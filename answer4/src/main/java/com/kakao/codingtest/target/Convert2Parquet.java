package com.kakao.codingtest.parquet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.SchemaBuilder.FieldAssembler;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.column.ParquetProperties;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConvertParquetUtil {
	private static final String TMP_EXT = ".tmp";

	public static String mapToParquet(
			FileSystem fileSystem, String parquetPath, List<Map<String, Object>> dataList)
					throws IOException {
		Path outputParquetFile;
		String tmpPath;
		if (StringUtils.isBlank(parquetPath)) {
			throw new IllegalArgumentException("parquetPath cannot be null or empty");
		} else {
			tmpPath = parquetPath + TMP_EXT;
			outputParquetFile = new Path(tmpPath);
		}
		if (dataList.size() == 0) {
			log.warn("Data is empty");
			return null;
		}
		FieldAssembler<Schema> fa = SchemaBuilder
				.record("record_name")
				.namespace("com.kakao.codingtest")
				.fields();
		for (Entry<String, Object> entry: dataList.get(0).entrySet()) {
			fa.name(entry.getKey()).type().stringType().stringDefault("-");
		}
		Schema schema = fa.endRecord();
		Configuration conf;
		if (fileSystem == null) {
			conf = new Configuration();
		} else {
			conf = fileSystem.getConf();
		}
		conf.set("parquet.strings.signed-min-max.enabled", "true");
		conf.set("parquet.string.min-max-statistics", "true");

		ParquetWriter<GenericData.Record> writer;
		writer = AvroParquetWriter
				.<GenericData.Record>builder(outputParquetFile)
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

		for (Map<String, Object> row: dataList) {
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
		if (writer != null) {
			writer.close();
		}

		if (fileSystem != null) {
			fileSystem.rename(outputParquetFile, new Path(parquetPath));
		} else {
			FileUtils.moveFile(new File(tmpPath), new File(parquetPath));
		}
		return parquetPath;
	}
}
