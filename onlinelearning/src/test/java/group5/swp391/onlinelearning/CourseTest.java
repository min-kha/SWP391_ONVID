package group5.swp391.onlinelearning;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import group5.swp391.onlinelearning.controller.CourseController;
import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetail;
import group5.swp391.onlinelearning.repository2.CourseRepository;
import group5.swp391.onlinelearning.service2.Impl.CourseServiceImpl;
import group5.swp391.onlinelearning.service2.Impl.UserService;

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
    private CourseServiceImpl courseService;

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
