package ua.lpr.webhelper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@PropertySources({
		@PropertySource("application.properties"),
		@PropertySource("application-${spring.profiles.active}.properties")
})
@EnableAsync
public class WebHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebHelperApplication.class, args);
	}
}
