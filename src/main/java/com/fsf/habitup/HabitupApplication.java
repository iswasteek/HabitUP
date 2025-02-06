package com.fsf.habitup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fsf.habitup")
public class HabitupApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitupApplication.class, args);
	}

}
