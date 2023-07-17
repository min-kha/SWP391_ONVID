package group5.swp391.onlinelearning;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import group5.swp391.onlinelearning.repository.CourseRepository;
import group5.swp391.onlinelearning.service.impl.CourseService;
import group5.swp391.onlinelearning.service.impl.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @Test
    public void testGetCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/teacher/course/detail/{id}", 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.view().name("course/detail"));
    }

    @Test
    public void testGetCourseList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/teacher/course/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
                .andExpect(MockMvcResultMatchers.view().name("course/list"));
    }

    @Test
    public void testGetCreateCourse_ReturnsCreateCoursePage() throws Exception {
        // Act
        mockMvc.perform(get("/teacher/course/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/add"))
                .andExpect(model().attributeExists("course"))
                .andReturn();
    }

}
