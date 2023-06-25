package demo.thymeleaf;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import demo.thymeleaf.model.Student;

@SpringBootApplication
public class ThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafApplication.class, args);
		List<String> fields = new ArrayList<>();
		List<String> fieldsType = new ArrayList<>();

		for (Field field : Student.class.getDeclaredFields()) {
			fields.add(field.getName());
			fieldsType.add(field.getType().getName());
		}
		fields.forEach(System.out::println);
		fieldsType.forEach(System.out::println);
	}
}

@Controller
class HelloController {
	@GetMapping("/")
	public String getIndex() {
		return "index";
	}

	@GetMapping("/add")
	public String getForm(Model model) {
		Student student = new Student();
		model.addAttribute("entity", student);
		model.addAttribute("formAction", "student/add");
		List<Field> fields = Arrays.asList(Student.class.getDeclaredFields());
		model.addAttribute("fields", fields);
		return "student/add";
	}
}
