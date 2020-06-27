package learn.kd.catalog.config;

import java.util.Arrays;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.web.client.RestTemplate;

import learn.kd.catalog.interceptor.RestCallInfoIntercepter;

@Configuration
@EnableEurekaClient
@EnableCircuitBreaker
@EnableRetry(proxyTargetClass = true)
@EnableHystrixDashboard
@RibbonClients(value = { 
        @RibbonClient(name = "movie-info-service"), 
        @RibbonClient(name = "ratings-data-service") 
        }, defaultConfiguration = { 
                LoadBalancerConfig.class 
                })
public class ApplicationConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Arrays.asList(new RestCallInfoIntercepter()));
        return restTemplate;
    }

    @Bean
    public LoadBalancedRetryFactory retryFactory() {
        return new LoadBalancedRetryFactory() {
            @Override
            public BackOffPolicy createBackOffPolicy(String service) {
                return new ExponentialRandomBackOffPolicy();
            }
        };
    }
}
