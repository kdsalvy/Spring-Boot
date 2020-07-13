package learn.kd.spring.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class SpringZuulProxyServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringZuulProxyServerApplication.class, args);
	}

}
