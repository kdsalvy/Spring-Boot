package mini.project.spring.domain.scheduler.service;

import org.springframework.stereotype.Component;

import mini.project.spring.domain.annotation.MonitorPerformance;

@Component
public class ScheduledCronJobServiceImpl implements ScheduledCronJobService {

	@MonitorPerformance
	@Override
	public void expensiveMethod2() {
		System.out.println("ScheduledCronJob.expensiveMethod2()");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@MonitorPerformance
	@Override
	public void expensiveMethod1() {
		System.out.println("ScheduledCronJob.expensiveMethod1()");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
