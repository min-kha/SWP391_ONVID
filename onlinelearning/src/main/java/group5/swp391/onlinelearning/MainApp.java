package group5.swp391.onlinelearning;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import group5.swp391.onlinelearning.service.impl.UserService;

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
