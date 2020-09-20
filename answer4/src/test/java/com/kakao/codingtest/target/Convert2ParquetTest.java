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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

@TestInstance(Lifecycle.PER_CLASS)
class Convert2ParquetTest {
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
    void makeParquet() throws IOException, URISyntaxException {
        List<Map<String, Object>> dataList = new ArrayList<>();
        String[] headers = {"a", "b", "c", "d"};
        for (int i = 0; i < 1000; i++) {
            Map<String, Object> row = new HashMap<>();
            for (String header: headers) {
                row.put(header, RandomStringUtils.random(5, true, true));
            }
            int hour = new Random().nextInt(10);
            row.put("time", "202009190" + hour + "000000");
            dataList.add(row);
        }
        FileSystem fs = FileSystem.get(new URI(hdfsUri), conf);
        assertEquals(true,
                new Convert2Parquet(taskInfoVO).convertAndPushHDFS(fs, dataList).size() > 0);
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
