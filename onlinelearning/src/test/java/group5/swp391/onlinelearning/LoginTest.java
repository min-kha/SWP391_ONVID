package group5.swp391.onlinelearning;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import group5.swp391.onlinelearning.controller.student.LoginController;
import group5.swp391.onlinelearning.service.IUserService;

public class LoginTest {

    @Mock
    private IUserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private HttpSession session;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostStudentLogin_WithValidCredentials_RedirectToHomeStudent() {
        UserDTOLoginRequest student = new UserDTOLoginRequest("user1@example.com",
                "Kha123@");
        when(bindingResult.hasErrors()).thenReturn(false);
        User user = new User();
        when(userService.loginStudent(student, model)).thenReturn(user);

        String result = loginController.postStudentLogin(student, bindingResult,
                model, session);

        assertEquals("redirect:home-student", result);
        verify(model, never()).addAttribute(eq("loginError"), anyString());
        verify(model, never()).addAttribute(eq("EnterFieldError"), anyString());
    }

    @Test
    public void testPostStudentLogin_WithInvalidCredentials_ReturnLoginViewWithErrors() {
        UserDTOLoginRequest student = new UserDTOLoginRequest("123", "123");
        when(bindingResult.hasErrors()).thenReturn(true);
        User user = new User();
        when(userService.loginStudent(student, model)).thenReturn(null);

        String result = loginController.postStudentLogin(student, bindingResult,
                model, session);

        assertEquals("login", result);
        verify(model, atLeastOnce()).addAttribute("EnterFieldError", "Login failed");
        verify(model, never()).addAttribute(eq("loginError"), anyString());
    }

    @Test
    public void testPostStudentLogin_WithInvalidLogin_ReturnLoginViewWithErrorMessage() {
        UserDTOLoginRequest student = new UserDTOLoginRequest("abc@example.com",
                "MatKhauSai123@");
        when(bindingResult.hasErrors()).thenReturn(false);
        User user = new User();

        when(userService.loginStudent(student, model)).thenReturn(null);

        String result = loginController.postStudentLogin(student, bindingResult,
                model, session);

        assertEquals("login", result);
        verify(model, times(1)).addAttribute("loginError", "Login failed");
        verify(model, never()).addAttribute(eq("EnterFieldError"), anyString());
    }
}
