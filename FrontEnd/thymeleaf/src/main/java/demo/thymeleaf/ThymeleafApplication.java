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
			fieldsType.add(field.getType().getSimpleName());
			System.out.println(field.getType().getSimpleName());
		}
		System.out.println("Simple Name: " + Student.class.getSimpleName());
		fields.forEach(System.out::println);
		fieldsType.forEach(System.out::println);
	}
}

@Controller
class HelloController {
	@GetMapping("/")
	public String getIndex() {
		return "redirect:/list";
	}

	@GetMapping("/create")
	public String getCreate(Model model) {
		Student student = new Student();
		model.addAttribute("entity", student);
		model.addAttribute("action", "student/create");
		List<Field> fields = Arrays.asList(Student.class.getDeclaredFields());
		model.addAttribute("fields", fields);
		return "view-sample/create";
	}

	@GetMapping("/edit")
	public String getEdit(Model model) {
		Student student = new Student();
		model.addAttribute("entity", student);
		model.addAttribute("action", "student/edit");
		List<Field> fields = Arrays.asList(Student.class.getDeclaredFields());
		model.addAttribute("fields", fields);
		return "view-sample/edit";
	}

	@GetMapping("/delete")
	public String getDelete(Model model) {
		Student student = new Student();
		model.addAttribute("entity", student);
		model.addAttribute("action", "student/delete");
		List<Field> fields = Arrays.asList(Student.class.getDeclaredFields());
		model.addAttribute("fields", fields);
		return "view-sample/delete";
	}

	@GetMapping("/list")
	public String getList(Model model) {
		Student student = new Student();
		model.addAttribute("entity", student);
		model.addAttribute("action", "student/list");
		List<Field> fields = Arrays.asList(Student.class.getDeclaredFields());
		model.addAttribute("fields", fields);
		return "view-sample/list";
	}

	@GetMapping("/detail")
	public String getDetail(Model model) {
		Student student = new Student(1, true, 889, "Kha", "0868636668", "kha@fpt.edu.vn", "kha@fp", "kha@fpt.edu.vn",
				"kha@fpt.edu.vn", "kha@fpt.edu.vn", "kha@fpt.edu.vn");

		model.addAttribute("entity", student);
		model.addAttribute("action", "student/detail");
		List<Field> fields = Arrays.asList(Student.class.getDeclaredFields());
		model.addAttribute("fields", fields);
		return "view-sample/detail";
	}
}
