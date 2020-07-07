package learn.kd.catalog.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@EnableFeignClients(basePackages = {"learn.kd.catalog.service"})
@EnableEurekaClient
@EnableRetry
@EnableCircuitBreaker
@EnableHystrixDashboard
@RibbonClients(value = { @RibbonClient(name = "movie-info-service"), @RibbonClient(name = "ratings-data-service") }, defaultConfiguration = LoadBalancerConfig.class)
@Configuration
public class ApplicationConfig {

//    Not Required as we are using Feign now
//    @Primary
//    @Bean
//    @LoadBalanced
//    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
//        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//        httpRequestFactory.setConnectionRequestTimeout(5000);
//
//        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
//        return restTemplate;
//    }

}
