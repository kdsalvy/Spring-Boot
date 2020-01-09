package mini.project.spring.domain.scheduler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.test.context.junit4.SpringRunner;

import mini.project.spring.domain.scheduler.service.ScheduledCronJobService;
import mini.project.spring.domain.util.StringConstants;

@RunWith(SpringRunner.class)
public class ScheduledCronJobTest {

	@TestConfiguration
	static class ScheduledCronJobTestContextConfig {

		@Bean
		public ScheduledJob scheduledJob() {
			return new ScheduledCronJob();
		}
	}

	@MockBean
	private ScheduledCronJobService scheduledCronJobService;

	@Autowired
	ScheduledJob scheduledJob;

	@Test
	public void testFetchBusinessInfoCronTriggerTime() {
		TimeZone timezone = TimeZone.getTimeZone(StringConstants.IST_TIME_ZONE);
		CronTrigger trigger = new CronTrigger(
				StringConstants.CRON_JOB_EXPRESSION, timezone);
		Calendar calendar = Calendar.getInstance(timezone);
		calendar.set(Calendar.MINUTE, 2);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();
		calendar.add(Calendar.MINUTE, 2);
		TriggerContext context = getTriggerContext(date);

		assertEquals(calendar.getTime().toString(),
				trigger.nextExecutionTime(context).toString());
	}

	@Test
	public void testFetchBusinessInfoOnSuccess() {
		doNothing().when(scheduledCronJobService).expensiveMethod1();
		doNothing().when(scheduledCronJobService).expensiveMethod2();

		scheduledJob.fetchBusinessInfo();

		InOrder inorder = Mockito.inOrder(scheduledCronJobService,
				scheduledCronJobService);
		inorder.verify(scheduledCronJobService).expensiveMethod1();
		inorder.verify(scheduledCronJobService).expensiveMethod2();
	}

	@Test(expected = Exception.class)
	public void testFetchBusinessInfoOnExceptionInExpensiveMethod1() {
		doThrow(Exception.class).when(scheduledCronJobService)
				.expensiveMethod1();

		scheduledJob.fetchBusinessInfo();

		InOrder inorder = Mockito.inOrder(scheduledCronJobService,
				scheduledCronJobService);
		inorder.verify(scheduledCronJobService).expensiveMethod1();
	}

	@Test(expected = Exception.class)
	public void testFetchBusinessInfoOnExceptionInExpensiveMethod2() {
		doNothing().when(scheduledCronJobService).expensiveMethod1();
		doThrow(Exception.class).when(scheduledCronJobService)
				.expensiveMethod2();

		scheduledJob.fetchBusinessInfo();

		InOrder inorder = Mockito.inOrder(scheduledCronJobService,
				scheduledCronJobService);
		inorder.verify(scheduledCronJobService).expensiveMethod1();
		inorder.verify(scheduledCronJobService).expensiveMethod2();
	}

	private TriggerContext getTriggerContext(Date lastExecutionTime) {
		SimpleTriggerContext context = new SimpleTriggerContext();
		context.update(null, null, lastExecutionTime);
		return context;
	}
}
