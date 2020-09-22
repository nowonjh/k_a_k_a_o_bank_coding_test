package com.kakao.codingtest.target;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.test.util.ReflectionTestUtils;

import com.kakao.codingtest.taskinfo.vo.SourceVO;
import com.kakao.codingtest.taskinfo.vo.TargetVO;
import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
class AsyncConvert2ParquetTest {
    private String hdfsUri;
    private Configuration conf;
    private File testDir;
    private MiniDFSCluster miniDFSCluster;

    private TaskInfoVO taskInfoVO;

    @BeforeAll
    void setUp() throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            File winutilsFile = new File(
                getClass().getClassLoader().getResource("hadoop/bin/winutils.exe").toURI());
            System.setProperty("hadoop.home.dir",
                winutilsFile.getParentFile().getParentFile().getAbsolutePath());
            System.setProperty("java.library.path", winutilsFile.getParentFile().getAbsolutePath());
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        }
        testDir = Files.createTempDirectory("test_hdfs").toFile();
        conf = new Configuration();
        conf.set(MiniDFSCluster.HDFS_MINIDFS_BASEDIR, testDir.getAbsolutePath());
        miniDFSCluster = new MiniDFSCluster.Builder(conf).nameNodePort(11000).build();
        hdfsUri = miniDFSCluster.getURI().toString();
    }

    @BeforeEach
    void initVO() {
        taskInfoVO = new TaskInfoVO();
        TargetVO targetVO = new TargetVO();
        targetVO.setPath(testDir.getAbsolutePath());
        SourceVO sourceVO = new SourceVO();
        sourceVO.setTimeField("time");
        sourceVO.setTimeFormat("yyyyMMddHHmmss");
        taskInfoVO.setTarget(targetVO);
        taskInfoVO.setSource(sourceVO);
    }

    @Test
    void asyncMakeParquet() throws IOException, URISyntaxException {
        FileSystem fs = FileSystem.get(new URI(hdfsUri), conf);
        BlockingQueue<Map<String, Object>> queue = new LinkedBlockingQueue<>(10_000);
        new Convert2Parquet(taskInfoVO).asyncConvertAndPushHDFS(fs, queue);
        String[] headers = {"a", "b", "c", "d"};
        for (int i = 0; i < 1000000; i++) {
            Map<String, Object> row = new HashMap<>();
            for (String header: headers) {
                row.put(header, RandomStringUtils.random(5, true, true));
            }
            int hour = new Random().nextInt(10);
            row.put("time", "202009190" + hour + "000000");
            try {
                queue.put(row);
                if (i % 1000 == 0) {
                    log.debug("produce count: " + i);
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Test
    void genPath() throws ParseException {
        long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-09-19 03:20:00").getTime();
        assertEquals(testDir.getAbsolutePath() + "/20200919/0320",
            ReflectionTestUtils.invokeMethod(
                new Convert2Parquet(taskInfoVO), "generatePathStr", time));
    }

    @AfterAll
    void tearDown() {
        miniDFSCluster.shutdown();
        FileUtil.fullyDelete(testDir);
    }
}
