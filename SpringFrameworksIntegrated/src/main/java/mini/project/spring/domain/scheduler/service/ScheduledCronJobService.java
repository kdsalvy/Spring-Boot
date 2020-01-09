package mini.project.spring.domain.scheduler.service;

import org.springframework.stereotype.Component;

@Component
public interface ScheduledCronJobService {

	public void expensiveMethod1();
	
	public void expensiveMethod2();
}
