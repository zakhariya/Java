package com.slinemotors.slineproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@PropertySources({
		@PropertySource("application.properties"),
		@PropertySource("application-${spring.profiles.active}.properties")
})
@Slf4j
public class SLineProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SLineProjectApplication.class, args);
    }

    /*public static void main(String[] args) {
	    SpringApplication application =
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SLineProjectApplication.class);
		ConfigurableApplicationContext context = builder.run(args);

		UserService userService = context.getBean(UserService.class);
		List<User> users = userService.findAll();

		log.warn("No users was found. Creating default user admin with password admin.");

		userService.createDefaultUser();

		if (users.size() == 0) {
			System.out.println("000000000");
		}
	}*/
}
