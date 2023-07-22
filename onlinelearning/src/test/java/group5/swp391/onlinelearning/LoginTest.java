package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import group5.swp391.onlinelearning.controller.Student.LoginController;
import group5.swp391.onlinelearning.entity.Cart;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.service.impl.CartService;

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

    @Mock
    private CartService cartService;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // public void testPostStudentLogin_WithValidCredentials_RedirectToHomePage() {
    // // Arrange
    // UserDTOLoginRequest validStudent = new UserDTOLoginRequest();
    // Model model = mock(Model.class);
    // HttpSession session = mock(HttpSession.class);
    // User mockUser = new User();
    // Cart mockCart = new Cart();

    // when(userService.loginStudent(any(), any())).thenReturn(mockUser);
    // // when(cartService.getCartByStudentId(anyLong())).thenReturn(mockCart);

    // // Act
    // String result = loginController.postStudentLogin(validStudent,
    // mock(BindingResult.class), model, session);

    // // Assert
    // assertEquals("redirect:/student/home/1", result);
    // verify(session).setAttribute("studentSession", mockUser);
    // verify(session).setAttribute("cartStudentSession", mockCart.getCourses());
    // }

    @Test
    public void testPostStudentLogin_WithInvalidCredentials_ReturnLoginViewWithError() {
        // Arrange
        UserDTOLoginRequest invalidStudent = new UserDTOLoginRequest();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);

        when(userService.loginStudent(any(), any())).thenReturn(null);

        // Act
        String result = loginController.postStudentLogin(invalidStudent, mock(BindingResult.class), model, session);

        // Assert
        assertEquals("/student/login/loginAccount", result);
        verify(model).addAttribute("loginError", "Login failed");
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

        assertEquals("/student/login/loginAccount", result);
        verify(model, times(1)).addAttribute("loginError", "Login failed");
        verify(model, never()).addAttribute(eq("EnterFieldError"), anyString());
    }
}
