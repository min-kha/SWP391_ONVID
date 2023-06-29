package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import group5.swp391.onlinelearning.controller.AccountController;
import group5.swp391.onlinelearning.model.dto.StaffDTOCreate;
import group5.swp391.onlinelearning.service.IUserService;

public class CreateStaffTest {

    @Mock
    private IUserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTrue() {
        StaffDTOCreate staff = StaffDTOCreate.builder().email("handoi@gmail.com").password("Hung532@")
                .rePassword("Hung532@").name("HungTest").build();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = accountController.postCreateUser(staff, bindingResult, model);

        assertEquals("redirect:account", result);
    }

    @Test
    public void testFailWithDupplicateEmail() {
        StaffDTOCreate staff = StaffDTOCreate.builder().email("hackinghungtx@gmail.com").password("Hung532@")
                .rePassword("Hung532@").name("HungTest").build();
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = accountController.postCreateUser(staff, bindingResult, model);
        assertEquals("CreateStaff", result);
    }

    @Test
    public void testFailWithPassNotFormat() {
        StaffDTOCreate staff = StaffDTOCreate.builder().email("hackinghungtx1111@gmail.com").password("123")
                .rePassword("123@").name("HungTest").build();
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = accountController.postCreateUser(staff, bindingResult, model);
        assertEquals("CreateStaff", result);
    }

    @Test
    public void testFailWithRepassNotSameWithPass() {
        StaffDTOCreate staff = StaffDTOCreate.builder().email("hackinghungtx1111@gmail.com").password("Hung532@!")
                .rePassword("Hung532@").name("HungTest").build();
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = accountController.postCreateUser(staff, bindingResult, model);
        assertEquals("CreateStaff", result);
    }

    @Test
    public void testFailWithBlankEmail() {
        StaffDTOCreate staff = StaffDTOCreate.builder().email("").password("Hung532@!")
                .rePassword("Hung532@").name("HungTest").build();
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = accountController.postCreateUser(staff, bindingResult, model);
        assertEquals("CreateStaff", result);
    }

    @Test
    public void testFailWithBlankName() {
        StaffDTOCreate staff = StaffDTOCreate.builder().email("hackinghungtx1111@gmail.com").password("Hung532@!")
                .rePassword("Hung532@").name("").build();
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = accountController.postCreateUser(staff, bindingResult, model);
        assertEquals("CreateStaff", result);
    }
}