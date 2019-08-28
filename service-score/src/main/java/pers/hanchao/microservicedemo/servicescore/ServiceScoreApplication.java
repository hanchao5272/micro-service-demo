package pers.hanchao.microservicedemo.servicescore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author hanchao
 */
@EnableEurekaClient
@SpringBootApplication
public class ServiceScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceScoreApplication.class, args);
    }

}
