package com.kakao.codingtest.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.codingtest.config.vo.TaskInfoVO;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yuganji
 *
 */
@Slf4j
@Service
public class ConfigurationLoader {
	protected ObjectMapper objMapper = new ObjectMapper();
	
	@Getter
	List<Map<String, TaskInfoVO>> taskList = null;
	
	@PostConstruct
	public void init() {
		this.reload();
	}
	@Scheduled(fixedDelay = 60 * 1000)
	public void reload() {
		try {
			this.taskList = objMapper.readValue(new File("./conf/configuration.json"), 
					new ArrayList<Map<String, TaskInfoVO>>().getClass().asSubclass(List.class));
		} catch (IOException e) {
			if (this.taskList == null) {
				log.error(e.getMessage(), e);
				System.exit(0);
			}
			log.warn("Didn't reload configuration.json.", e);
		}
	}
}
