package group5.swp391.onlinelearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import group5.swp391.onlinelearning.service.impl.CourseService;

@SpringBootApplication
public class MainApp {

	public static void main(String[] args) {
		var context = SpringApplication.run(MainApp.class, args);
		CourseService courseService = context.getBean(CourseService.class);
		System.out.println(courseService.getAllCourses());
	}

}
