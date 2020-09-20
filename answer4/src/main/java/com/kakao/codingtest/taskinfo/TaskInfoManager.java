package com.kakao.codingtest.taskinfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.codingtest.exception.TaskInfoValidateException;
import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;
import com.kakao.codingtest.util.Constants;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yuganji
 * 1분에 한번씩 테스크 설정파일을 읽어 캐싱한다.
 */
@Slf4j
@Service
public class TaskInfoManager {
    private ObjectMapper objMapper = new ObjectMapper();
    private String taskInfoPath = "./conf/task_info.json";
    @Getter
    private List<TaskInfoVO> taskList;

    @PostConstruct
    public void init() {
        this.taskList = Collections.synchronizedList(new ArrayList<>());
        this.reload();
    }
    @Scheduled(fixedDelay = Constants.MILLIS_1MIN)
    public void reload() {
        List<TaskInfoVO> tempTaskList = null;
        try {
            tempTaskList = objMapper.readValue(new File(this.taskInfoPath),
                    new TypeReference<List<TaskInfoVO>>() { });
        } catch (IOException e) {
            if (this.taskList == null) {
                log.error(e.getMessage(), e);
                System.exit(0);
            }
            log.warn("Can't reload task_info.json. please check it.", e);
            return;
        }

        this.taskList.clear();
        for (TaskInfoVO taskInfoVO: tempTaskList) {
            try {
                if (this.validCheck(taskInfoVO)) {
                    this.taskList.add(taskInfoVO);
                }
            } catch (TaskInfoValidateException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public boolean validCheck(TaskInfoVO taskInfoVO) throws TaskInfoValidateException {
       boolean result = true;
        result = result && this.isValidName(taskInfoVO);
        result = result && this.isValidDelayMin(taskInfoVO);
        result = result && this.isValidPeriodHour(taskInfoVO);
        result = result && this.isValidConcurrency(taskInfoVO);
        result = result && this.isValidConnector(taskInfoVO);
        result = result && this.isValidTargetType(taskInfoVO);
        result = result && this.isValidTargetFormat(taskInfoVO);
        return result;
    }

    private boolean isValidName(TaskInfoVO taskInfoVO) throws TaskInfoValidateException {
        if (taskInfoVO.getName() == null) {
            throw new TaskInfoValidateException(
                    "\"name\" is not defined." + taskInfoVO.toString());
        }
        return true;
    }

    private boolean isValidDelayMin(TaskInfoVO taskInfoVO) throws TaskInfoValidateException {
        if (taskInfoVO.getDelayMin() * Constants.MILLIS_1MIN > Constants.MILLIS_1DAY) {
            throw new TaskInfoValidateException(
                    "\"delay_min\" is too large. "
                    + "It shuold be less than 1day. [" + taskInfoVO.getName() + "]");
        }
        return true;
    }

    private boolean isValidPeriodHour(TaskInfoVO taskInfoVO) throws TaskInfoValidateException {
        if (taskInfoVO.getPeriodHour() * Constants.MILLIS_1HOUR
                > Constants.MILLIS_1DAY * 7) {
            throw new TaskInfoValidateException(
                    "\"period_hour\" is too large. "
                    + "It shuold be less than 1day. [" + taskInfoVO.getName() + "]");
        } else if (taskInfoVO.getPeriodHour() <= 0) {
            throw new TaskInfoValidateException(
                    "\"period_hour\" shuold be lager than 0. [" + taskInfoVO.getName() + "]");
        }
        return true;
    }
    private boolean isValidConcurrency(TaskInfoVO taskInfoVO) throws TaskInfoValidateException {
        if (taskInfoVO.getConcurrency() >= Runtime.getRuntime().availableProcessors() / 2) {
            throw new TaskInfoValidateException(
                    "\"concurrency\" is too large. "
                    + "It should be less than half the number of processors. "
                    + "[" + taskInfoVO.getName() + "]");
        }
        return true;
    }

    private boolean isValidConnector(TaskInfoVO taskInfoVO) throws TaskInfoValidateException {
        List<String> connectorList =
                Stream.of(Constants.ConnectorType.values())
                .map(x -> x.getValue()).collect(Collectors.toList());
        if (!connectorList.contains(taskInfoVO.getConnector().toLowerCase())) {
            throw new TaskInfoValidateException(
                    "\"connector\" must be one of these connectors. "
                    + connectorList.toString());
        }
        return true;
    }
    private boolean isValidTargetType(TaskInfoVO taskInfoVO) throws TaskInfoValidateException {
        List<String> targetTypeList =
                Stream.of(Constants.TargetType.values())
                .map(x -> x.getValue()).collect(Collectors.toList());
        if (!targetTypeList.contains(taskInfoVO.getTarget().getType().toLowerCase())) {
            throw new TaskInfoValidateException(
                    "\"target\"->\"type\" must be one of these connectors. "
                    + targetTypeList.toString());
        }
        return true;
    }

    private boolean isValidTargetFormat(TaskInfoVO taskInfoVO) throws TaskInfoValidateException {
        List<String> targetFileFormatList =
                Stream.of(Constants.TargetFileFormat.values())
                .map(x -> x.getValue()).collect(Collectors.toList());
        if (!targetFileFormatList.contains(taskInfoVO.getTarget().getFormat().toLowerCase())) {
            throw new TaskInfoValidateException(
                    "\"target\"->\"format\" must be one of these connectors. "
                    + targetFileFormatList.toString());
        }
        return true;
    }
}
