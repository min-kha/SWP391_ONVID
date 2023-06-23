package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import group5.swp391.onlinelearning.controller.LoginController;
import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.service2.IUserService;

public class LoginTest {

    @Mock
    private IUserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostStudentLogin_WithValidCredentials_RedirectToHomeStudent() {
        UserDTOLoginRequest student = new UserDTOLoginRequest("user1@example.com", "Kha123@");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.loginStudent(student)).thenReturn(true);

        String result = loginController.postStudentLogin(student, bindingResult, model);

        assertEquals("redirect:home-student", result);
        verify(model, never()).addAttribute(eq("loginError"), anyString());
        verify(model, never()).addAttribute(eq("EnterFieldError"), anyString());
    }

    @Test
    public void testPostStudentLogin_WithInvalidCredentials_ReturnLoginViewWithErrors() {
        UserDTOLoginRequest student = new UserDTOLoginRequest("123", "123");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(userService.loginStudent(student)).thenReturn(false);
        String result = loginController.postStudentLogin(student, bindingResult, model);

        assertEquals("login", result);
        verify(model, atLeastOnce()).addAttribute("EnterFieldError", "Login failed");
        verify(model, never()).addAttribute(eq("loginError"), anyString());
    }

    @Test
    public void testPostStudentLogin_WithInvalidLogin_ReturnLoginViewWithErrorMessage() {
        UserDTOLoginRequest student = new UserDTOLoginRequest("abc@example.com", "MatKhauSai123@");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.loginStudent(student)).thenReturn(false);

        String result = loginController.postStudentLogin(student, bindingResult, model);

        assertEquals("login", result);
        verify(model, times(1)).addAttribute("loginError", "Login failed");
        verify(model, never()).addAttribute(eq("EnterFieldError"), anyString());
    }
}
