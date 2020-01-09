package mini.project.spring.aop;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

import lombok.Data;
import mini.project.spring.domain.annotation.PredictScheduledCronJobTriggerTime;
import mini.project.spring.domain.viewobject.ScheduledTaskInfo;

@Data
@Aspect
@Component
public class ScheduledJobAspect {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledJobAspect.class);
    private Map<String, ScheduledTaskInfo> scheduledTaskRuntimeInfo;

    public ScheduledJobAspect() {
        scheduledTaskRuntimeInfo = new HashMap<>();
    }

    @Before("@annotation(jobTriggerTime)")
    private void mapScheduledJobsWithRuntimeInfo(PredictScheduledCronJobTriggerTime jobTriggerTime) {
        String expression = jobTriggerTime.expression();
        String jobName = jobTriggerTime.name();
        String timeZone = jobTriggerTime.zone();

        ScheduledTaskInfo scheduledTaskInfo = getScheduledtaskInfo(expression, jobName, timeZone);
        scheduledTaskRuntimeInfo.put(jobName, scheduledTaskInfo);
    }

    private ScheduledTaskInfo getScheduledtaskInfo(String expression, String jobName, String timeZone) {
        TimeZone zone = TimeZone.getTimeZone(timeZone);
        Calendar now = Calendar.getInstance();
        ZonedDateTime previousTriggerTime = ZonedDateTime.ofInstant(now.getTime()
            .toInstant(), zone.toZoneId());
        Date previousTriggerDateTime = now.getTime();
        logger.info("Previous trigger time: " + previousTriggerTime);
        CronSequenceGenerator sequenceGenerator = new CronSequenceGenerator(expression);
        Date nextTriggeredDateTime = sequenceGenerator.next(previousTriggerDateTime);
        ZonedDateTime nextTriggerTime = ZonedDateTime.ofInstant(nextTriggeredDateTime.toInstant(), zone.toZoneId());
        logger.info("Next trigger time: " + nextTriggerTime);
        return new ScheduledTaskInfo(previousTriggerTime, nextTriggerTime);

    }
}
