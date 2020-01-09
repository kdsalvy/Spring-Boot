package mini.project.spring.domain.scheduler;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import mini.project.spring.domain.annotation.MonitorPerformance;
import mini.project.spring.domain.annotation.PredictScheduledCronJobTriggerTime;
import mini.project.spring.domain.annotation.SendJobStatusMail;
import mini.project.spring.domain.scheduler.service.ScheduledCronJobService;
import mini.project.spring.domain.util.StringConstants;

@Service
public class ScheduledCronJob implements ScheduledJob {

	private static final Logger logger = LoggerFactory
			.getLogger(ScheduledCronJob.class);
	private String messageBody = StringConstants.EMPTY_STRING;
	private String messageSubject = StringConstants.EMPTY_STRING;

	@Autowired
	private ScheduledCronJobService scheduledCronJobService;

	@MonitorPerformance
	@SendJobStatusMail
	@PredictScheduledCronJobTriggerTime(name = "FetchBusinessInfo", expression = StringConstants.CRON_JOB_EXPRESSION, zone = StringConstants.IST_TIME_ZONE)
	@Scheduled(cron = StringConstants.CRON_JOB_EXPRESSION, zone = StringConstants.IST_TIME_ZONE)
	@Override
	public void fetchBusinessInfo() {
		logger.info("ScheduledCronJob.periodicallyFetchBusinessInfo()");
		messageSubject = "Fetch Business Info Report";
		messageBody = "Scheduled Job executing at: " + Calendar.getInstance();
		logger.info(messageBody);
		logger.info(messageSubject);
		scheduledCronJobService.expensiveMethod1();
		scheduledCronJobService.expensiveMethod2();
	}

	@Override
	public String getMessage() {
		return messageBody;
	}

	@Override
	public String getSubject() {
		return messageSubject;
	}
}
