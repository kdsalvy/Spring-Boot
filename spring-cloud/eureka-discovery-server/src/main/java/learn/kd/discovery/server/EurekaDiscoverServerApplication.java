package learn.kd.discovery.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class EurekaDiscoverServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaDiscoverServerApplication.class, args);
		log.info("Started Service Discovery Server");
	}

}
