package com.kakao.codingtest.worker.thread;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import com.kakao.codingtest.jdbc.JdbcManager;
import com.kakao.codingtest.target.Convert2Parquet;
import com.kakao.codingtest.target.IConvertData;
import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;
import com.kakao.codingtest.util.Constants;
import com.kakao.codingtest.worker.vo.RequestJDBCQueryVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDBCWorkerThread extends Thread {
    private TaskInfoVO taskInfoVO;
    private RequestJDBCQueryVO queryVO;
    private JdbcManager jdbcManager;

    public JDBCWorkerThread(
            TaskInfoVO taskInfoVO,
            RequestJDBCQueryVO queryVO,
            JdbcManager jdbcManager) {
        this.taskInfoVO = taskInfoVO;
        this.queryVO = queryVO;
        this.jdbcManager = jdbcManager;
    }

    @Override
    public void run() {
        IConvertData convertData = null;
        String targetFormat = this.taskInfoVO.getTarget().getFormat().toLowerCase();

        if (targetFormat.equals(Constants.TargetFileFormat.PARQUET.getValue())) {
            convertData = new Convert2Parquet(this.taskInfoVO);
        } else if (targetFormat.equals(Constants.TargetFileFormat.CSV.getValue())) {
            // TODO 파일 포맷이 parquet가 아닐때...
        }

        try {
            FileSystem fs = FileSystem.get(new URI(this.taskInfoVO.getTarget().getUrl()), new Configuration());
            List<Map<String, Object>> dataList = jdbcManager.query(
                    this.taskInfoVO.getSource(), queryVO);
            convertData.convertAndPushHDFS(fs, dataList);
        } catch (ClassNotFoundException | SQLException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
    }
}
