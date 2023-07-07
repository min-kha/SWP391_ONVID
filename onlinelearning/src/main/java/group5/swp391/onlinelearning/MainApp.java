package group5.swp391.onlinelearning;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import group5.swp391.onlinelearning.repository.CartRepository;

@SpringBootApplication
public class MainApp {
	public static void main(String[] args) {
		var context = SpringApplication.run(MainApp.class, args);
		// context.getBean(CartRepository.class).deleteCourseFromCart(1, 1);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
