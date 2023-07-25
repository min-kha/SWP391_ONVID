package group5.swp391.onlinelearning.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý mọi loại Exception
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex) {
        return "/error";
    }
}