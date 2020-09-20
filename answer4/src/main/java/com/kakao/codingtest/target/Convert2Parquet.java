package com.kakao.codingtest.target;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.SchemaBuilder.FieldAssembler;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.column.ParquetProperties;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;
import com.kakao.codingtest.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Convert2Parquet implements IConvertData {
    private final SimpleDateFormat outDayFormat = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat outTimeFormat = new SimpleDateFormat("HHmm");
    private TaskInfoVO taskInfoVO;
    public Convert2Parquet(TaskInfoVO taskInfoVO) {
        this.taskInfoVO = taskInfoVO;
    }

    /**
     *    데이터 셋을 변환 후 HDFS에 write
     *    Task Info에 정의된 Time field에 따라
     *  10분단위로 파일을 쪼개어 저장한다
     *  ex) ${target.path}/20200916/2030_xxxxx.parquet
     *      ${target.path}/20200916/2040_xxxxx.parquet
     *      ${target.path}/20200916/2050_xxxxx.parquet
     *      ${target.path}/20200916/2060_xxxxx.parquet
     */
    @Override
    public List<Path> convertAndPushHDFS(FileSystem fileSystem, List<Map<String, Object>> dataList) throws IOException {
        if (StringUtils.isBlank(this.taskInfoVO.getTarget().getPath())) {
            throw new IllegalArgumentException("parquetPath cannot be null or empty");
        }
        if (dataList.size() == 0) {
            log.warn("Data is empty");
            return null;
        }

        SimpleDateFormat inFormat = new SimpleDateFormat(taskInfoVO.getSource().getTimeFormat());
        String timeField = taskInfoVO.getSource().getTimeField();

        Map<String, ParquetWriter<GenericData.Record>> writerMap = new HashMap<>();
        Schema schema = null;

        for (Map<String, Object> row: dataList) {
            if (schema == null) {
                FieldAssembler<Schema> fa = SchemaBuilder
                        .record("record_name")
                        .namespace("com.kakao.codingtest")
                        .fields();
                for (Entry<String, Object> entry: row.entrySet()) {
                    fa.name(entry.getKey()).type().stringType().stringDefault("-");
                }
                schema = fa.endRecord();
            }
            String tmpPath;
            try {
                tmpPath = this.generatePathStr(
                        inFormat.parse(row.get(timeField).toString()).getTime());
            } catch (ParseException e1) {
                log.warn(e1.getMessage());
                tmpPath = "/tmp/unknown_time";
            }

            if (writerMap.get(tmpPath) == null) {
                ParquetWriter<GenericData.Record> writer = this.createParquetWriter(tmpPath, schema);
                writerMap.put(tmpPath, writer);
            }
            ParquetWriter<GenericData.Record> writer = writerMap.get(tmpPath);
            GenericRecordBuilder record = new GenericRecordBuilder(schema);
            for (Schema.Field f : schema.getFields()) {
                switch (f.schema().getType()) {
                    case BOOLEAN:
                        try {
                            record.set(f, Boolean.parseBoolean(row.get(f.name()).toString()));
                        } catch (Exception e) {
                            record.set(f, false);
                        }
                        break;
                    case INT:
                        try {
                            record.set(f, Integer.parseInt(row.get(f.name()).toString()));
                        } catch (NumberFormatException | NullPointerException e) {
                            record.set(f, 0);
                        }
                        break;
                    case FLOAT:
                        try {
                            record.set(f, Float.parseFloat(row.get(f.name()).toString()));
                        } catch (NumberFormatException | NullPointerException e) {
                            record.set(f, 0.0f);
                        }
                        break;
                    case DOUBLE:
                        try {
                            record.set(f, Double.parseDouble(row.get(f.name()).toString()));
                        } catch (NumberFormatException | NullPointerException e) {
                            record.set(f, 0.0d);
                        }
                        break;
                    case LONG:
                        try {
                            record.set(f, Long.parseLong(row.get(f.name()).toString()));
                        } catch (NumberFormatException | NullPointerException e) {
                            record.set(f, 0L);
                        }
                        break;
                    default:
                        if (row.get(f.name()) != null) {
                            record.set(f, row.get(f.name()));
                        } else {
                            record.set(f, "");
                        }
                        break;
                }
            }
            writer.write(record.build());
        }
        List<Path> destPaths = new ArrayList<>();
        for (Entry<String, ParquetWriter<Record>> entry: writerMap.entrySet()) {
            if (entry.getValue() != null) {
                entry.getValue().close();
            }
            String destFile = entry.getKey() + "_" + RandomStringUtils.random(5, true, false) + ".parquet";
            fileSystem.rename(new Path(entry.getKey()), new Path(destFile));
            destPaths.add(new Path(destFile));
        }
        return destPaths;
    }

    private String generatePathStr(long timestamp) {
        timestamp = timestamp / (Constants.MILLIS_1MIN * 10) * (Constants.MILLIS_1MIN * 10);
        return String.join("/",
                taskInfoVO.getTarget().getPath(),
                outDayFormat.format(timestamp),
                outTimeFormat.format(timestamp));
    }

    private ParquetWriter<GenericData.Record> createParquetWriter(String dir, Schema schema)
            throws IllegalArgumentException, IOException {
        Configuration conf = new Configuration();
        conf.set("parquet.strings.signed-min-max.enabled", "true");
        conf.set("parquet.string.min-max-statistics", "true");

        return AvroParquetWriter
                .<GenericData.Record>builder(new Path(dir))
                .withConf(conf)
                .withDataModel(GenericData.get())
                .withSchema(schema)
                .withWriterVersion(ParquetProperties.WriterVersion.PARQUET_1_0)
                .withRowGroupSize(ParquetWriter.DEFAULT_BLOCK_SIZE)
                .withPageSize(ParquetProperties.DEFAULT_PAGE_SIZE)
                .withDictionaryPageSize(ParquetProperties.DEFAULT_DICTIONARY_PAGE_SIZE)
                .withCompressionCodec(CompressionCodecName.SNAPPY)
                .withWriteMode(ParquetFileWriter.Mode.OVERWRITE)
                .withDictionaryEncoding(false)
                .withValidation(false)
                .build();
    }

    @Override
    public List<Path> convertWriteLocal(List<Map<String, Object>> dataList) {
        // TODO Auto-generated method stub
        return null;
    }
}
