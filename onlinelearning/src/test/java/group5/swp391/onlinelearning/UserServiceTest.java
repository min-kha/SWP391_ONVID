package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.repository.UserRepository;
import group5.swp391.onlinelearning.service.impl.UserService;
import group5.swp391.onlinelearning.utils.TestDataProvider;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(TestDataProvider.createSampleUser());
        mockUsers.add(TestDataProvider.createSampleUser());
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(mockUsers, result);
    }

    @Test
    public void testGetUserById() {
        int userId = 1;
        User mockUser = TestDataProvider.createSampleUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserById(userId);

        assertEquals(mockUser, result);
    }

    @Test
    public void testAddStaff() throws Exception {
        User staff = TestDataProvider.createSampleUser();

        when(userRepository.findById(staff.getId())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(staff.getEmail())).thenReturn(null);

        assertDoesNotThrow(() -> userService.addStaff(staff));
        verify(userRepository, times(1)).save(staff);
    }

    @Test
    public void testAddStaffDuplicateId() throws Exception {
        User staff = TestDataProvider.createSampleUser();

        when(userRepository.findById(staff.getId())).thenReturn(Optional.of(staff));

        assertThrows(InvalidInputException.class, () -> userService.addStaff(staff));
        verify(userRepository, never()).save(staff);
    }

    @Test
    public void testAddStaffDuplicateEmail() throws Exception {
        User staff = TestDataProvider.createSampleUser();

        when(userRepository.findById(staff.getId())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(staff.getEmail())).thenReturn(TestDataProvider.createSampleUser());

        assertThrows(InvalidInputException.class, () -> userService.addStaff(staff));
        verify(userRepository, never()).save(staff);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = TestDataProvider.createSampleUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        assertDoesNotThrow(() -> userService.updateUser(user));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUserNotFound() {
        User user = TestDataProvider.createSampleUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () -> userService.updateUser(user));
        verify(userRepository, never()).save(user);
    }

    // TODO: COMMENT BY HUNG
    // @Test
    // public void testUpdateUserDuplicateEmail() {

    // User existingUser = getRandomUser();
    // User user = getRandomUser();
    // User updatingUser = getRandomUser();

    // updatingUser.setId(user.getId());
    // // assuming that user has the same email with existingUser
    // updatingUser.setEmail(existingUser.getEmail());

    // when(userRepository.findById(updatingUser.getId())).thenReturn(Optional.of(user));
    // when(userRepository.findByEmail(updatingUser.getEmail())).thenReturn(existingUser);

    // assertThrows(InvalidInputException.class, () ->
    // userService.updateUser(user));
    // verify(userRepository, never()).save(user);
    // }

    @Test
    public void testDeleteUser() {
        int userId = 1;

        assertDoesNotThrow(() -> userService.deleteUser(userId));
        verify(userRepository, times(1)).deleteById(userId);
    }

}
