package com.fertilizertool.database;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JobCompletionListener extends JobExecutionListenerSupport{

	private static final Logger log = LoggerFactory.getLogger(JobCompletionListener.class);
	
	@Override
	public void afterJob(JobExecution jobexecution) {
		if(jobexecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Inserted Successfully");
		}else if (jobexecution.getStatus() == BatchStatus.FAILED) {
			log.info("Inserted Failes");
		}
	}
	
}
