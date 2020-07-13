package learn.kd.catalog.config;

import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.stats.DefaultStatisticsRepository;
import org.springframework.retry.stats.StatisticsListener;
import org.springframework.stereotype.Component;

/**
 * Config to implement retry with Fixed backoff using Ribbon & Eureka.
 * Couldn't get it working yet.
 * 
 * Including it as a bean interferes with feign retry, so removing it
 * as a managed bean
 * 
 * @author saukedia1
 *
 */
//@Component
public class BackOffRetryConfig extends RibbonLoadBalancedRetryFactory {

    public BackOffRetryConfig(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public BackOffPolicy createBackOffPolicy(String service) {
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000);
        return fixedBackOffPolicy;
    }

    @Override
    public RetryListener[] createRetryListeners(String service) {
        RetryListener[] retryListeners = new RetryListener[1];
        retryListeners[0] = new StatisticsListener(new DefaultStatisticsRepository());
        return retryListeners;
    }
}