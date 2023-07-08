package group5.swp391.onlinelearning;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApp {
	public static void main(String[] args) {
		var context = SpringApplication.run(MainApp.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
