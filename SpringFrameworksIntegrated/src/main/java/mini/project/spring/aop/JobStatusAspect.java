package mini.project.spring.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import mini.project.spring.domain.annotation.SendJobStatusMail;
import mini.project.spring.domain.scheduler.ScheduledJob;

@Aspect
@Component
public class JobStatusAspect {

    private static final Logger logger = LoggerFactory.getLogger(JobStatusAspect.class);

    @After("@annotation(sendStatusMail) && target(scheduledJob)")
    private void mailJobStatus(SendJobStatusMail sendStatusMail, ScheduledJob scheduledJob) {
        logger.info("Sending mail");
        logger.info("Subject: " + scheduledJob.getSubject());
        logger.info("Body: " + scheduledJob.getMessage());
    }
}
