package org.qian.springboot.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaAuditing
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication
public class SpringBootOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootOrderApplication.class, args);
	}
}
