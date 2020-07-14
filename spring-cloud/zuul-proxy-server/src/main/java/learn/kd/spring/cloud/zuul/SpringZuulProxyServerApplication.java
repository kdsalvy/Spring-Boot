package learn.kd.spring.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableZuulProxy
@SpringBootApplication
public class SpringZuulProxyServerApplication {

    public static void main(String[] args) {
        log.info("Starting Proxy Server");
        SpringApplication.run(SpringZuulProxyServerApplication.class, args);
        log.info("Started Proxy Server");
    }

}
