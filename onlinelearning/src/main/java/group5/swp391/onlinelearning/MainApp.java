package group5.swp391.onlinelearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;

import group5.swp391.onlinelearning.repository.CartRepository;
import group5.swp391.onlinelearning.repository.CourseRepository;
import group5.swp391.onlinelearning.service.impl.CourseService;


@SpringBootApplication
public class MainApp {
	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}
}
