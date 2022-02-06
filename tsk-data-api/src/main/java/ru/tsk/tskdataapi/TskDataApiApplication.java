package ru.tsk.tskdataapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;


//@EnableSwagger2
@SpringBootApplication
@Slf4j
public class TskDataApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TskDataApiApplication.class, args);
	}

}
