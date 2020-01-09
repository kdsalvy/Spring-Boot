package mini.project.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import mini.project.spring.domain.annotation.MonitorPerformance;

@Aspect
@Component
public class PerformanceMonitorAspect {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitorAspect.class);
	
	@Around("@annotation(monitorPerformance)")
	public Object monitorMethodPerformance(ProceedingJoinPoint jointPoint, MonitorPerformance monitorPerformance) throws Throwable {
		logger.info("Start Monitoring Method Performance");
		CodeSignature signature = (CodeSignature) jointPoint.getSignature();
		String methodName = signature.toShortString();
		
		StopWatch stopwatch = new StopWatch(PerformanceMonitorAspect.class.getName());
		
		stopwatch.start(methodName);
		Object response = jointPoint.proceed();
		stopwatch.stop();
		
		//re-using the stopwatch
		stopwatch.start("2nd Block");
		logger.info(signature.toLongString());
		String[] paramNames = signature.getParameterNames();
		Object[] paramValues = jointPoint.getArgs();
		Thread.sleep(1000);
		for(int i = 0; i < paramNames.length; i++) {
			logger.info("paramName: {}", paramNames[0]);
			logger.info("paramValue: {}", paramValues[0]);
		}
		stopwatch.stop();
		logger.info(stopwatch.prettyPrint());
		logger.info(stopwatch.shortSummary());
		logger.info(String.valueOf(stopwatch.getTotalTimeMillis()));
		logger.info("Stop Monitoring Method Performance");
		
		return response;
		
	}
}
