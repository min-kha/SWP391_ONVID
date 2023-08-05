package demo.thymeleaf;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import demo.thymeleaf.model.Course;
import demo.thymeleaf.model.Student;
import demo.thymeleaf.utils.ThymeleafBaseCRUD;

@SpringBootApplication
public class ThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafApplication.class, args);
		List<String> fields = new ArrayList<>();
		List<String> fieldsType = new ArrayList<>();

		for (Field field : Student.class.getDeclaredFields()) {
			fields.add(field.getName());
			fieldsType.add(field.getType().getSimpleName());
			// System.out.println(field.getType().getSimpleName());
		}
		// System.out.println("Simple Name: " + Student.class.getSimpleName());
		// fields.forEach(System.out::println);
		// fieldsType.forEach(System.out::println);
	}
}


