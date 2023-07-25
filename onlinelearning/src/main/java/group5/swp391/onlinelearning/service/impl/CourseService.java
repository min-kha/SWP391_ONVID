package group5.swp391.onlinelearning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;

import group5.swp391.onlinelearning.entity.Feedback;

import group5.swp391.onlinelearning.entity.CourseReview;

import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetailStudent;
import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;
import group5.swp391.onlinelearning.model.mapper.CourseMapper;
import group5.swp391.onlinelearning.model.teacher.CourseDTOAdd;
import group5.swp391.onlinelearning.model.teacher.CourseDTOEdit;
import group5.swp391.onlinelearning.model.teacher.CourseDTOTeacher;
import group5.swp391.onlinelearning.repository.CourseRepository;
import group5.swp391.onlinelearning.repository.TopicRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private FeedbackService feedbackService;

    private HttpSession session;

    public CourseService(HttpSession session) {
        this.session = session;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public List<Course> getReviewCourses() {
        return courseRepository.findReviewCourses();
    }

    public Course getCourseByCourseId(int id) {
        return courseRepository.findById(id).get();
    }

    public Course getCourseById(int id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            return course;
        }
        return null;
    }

    // hung
    public Course createCourse(CourseDTOAdd courseDTOAdd) {
        Course course = courseMapper.courseDTOAddtoCourse(courseDTOAdd);
        User teacher = (User) session.getAttribute("user");
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    public List<CourseDTOTeacher> getCourseDTOTeacherList() {
        User teacher = (User) session.getAttribute("user");
        int id = teacher.getId();
        List<Course> courses = courseRepository.findAllByTeacherId(id);
        List<CourseDTOTeacher> cDtoTeachers = new ArrayList<CourseDTOTeacher>();
        for (Course course : courses) {
            cDtoTeachers.add(courseMapper.courseToCourseDTOTeacher(course));
        }
        return cDtoTeachers;
    }

    // check course exit in database
    public boolean isCourse(int courseId) {
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return true;
            }
        }
        return false;
    }

    // Check course have true teacher owener
    public boolean checkCourseOwner(int courseId) {
        User teacher = (User) session.getAttribute("user");
        int id = teacher.getId();
        List<Course> courses = courseRepository.findAllByTeacherId(id);
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return true;
            }
        }
        return false;
    }

    public Course updateCourse(CourseDTOEdit courseDTOEdit) {
        Optional<Course> courseOptional = courseRepository.findById(courseDTOEdit.getId());
        if (courseOptional.isPresent()) {
            Course existingCourse = courseOptional.get();
            existingCourse.setName(courseDTOEdit.getName());
            Topic topic = topicRepository.findById(courseDTOEdit.getTopic_id()).get();
            existingCourse.setTopic(topic);
            existingCourse.setPrice(courseDTOEdit.getPrice());
            existingCourse.setDescription(courseDTOEdit.getDescription());
            existingCourse.setImageLink(courseDTOEdit.getImageLink());
            return courseRepository.save(existingCourse);
        }
        // TODO: Throw exception in here
        return null;
    }

    public Course updateCourse(Course course) throws Exception {
        if (courseRepository.findById(course.getId()).isPresent()) {
            return courseRepository.save(course);
        }
        throw new InvalidInputException("id", "course.notfound", "Course not found");
    }

    public void deleteCourse(int id) {
        Course course = courseRepository.findById(id).get();
        if (course != null) {
            courseRepository.delete(course);
        }
    }

    public List<CourseDtoHomeDetail> getAllCourseDtoHomeDetails() {
        List<Course> courses = getAllCourses();
        List<CourseDtoHomeDetail> courseDtoHomeDetails = new ArrayList<>();
        List<Course> courseAvailable = new ArrayList<>();
        for (Course course : courses) {
            if (course.getStatus() == 3) {
                courseAvailable.add(course);
            }
        }

        for (Course course : courseAvailable) {
            List<Feedback> feedbackList = new ArrayList<Feedback>();
            feedbackList = feedbackService.getFeedbackByCourseId(course.getId());
            float avg = 0;
            if (feedbackList.size() == 0) {
                avg = 0;
            } else {
                for (Feedback feedback : feedbackList) {
                    avg += feedback.getRatingStar();
                }
                avg = avg / feedbackList.size();
            }
            courseDtoHomeDetails.add(courseMapper.courseToCourseDtoHomeDetail(course, avg));
        }
        return courseDtoHomeDetails;
    }

    public Course getCourseAllById(int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            return null;
        }
    }

    public CourseDtoDetailStudent getCourseDetailForStudentById(int id) {
        Course course = getCourseAllById(id);
        if (course != null) {
            CourseDtoDetailStudent courseRes = CourseMapper.courseToCourseDtoDetailStudent(course);
            return courseRes;
        } else {
            return null;
        }
    }

    public List<Course> getMyCourse(int studentId) {
        return courseRepository.getMyCourse(studentId);
    }

    public List<CourseDtoHomeDetail> getPopularCourse() {
        List<Course> coursesPopular = courseRepository.getPopularCourse();
        List<CourseDtoHomeDetail> courseDtoHomeDetailsPopular = new ArrayList<>();

        for (Course course : coursesPopular) {
            List<Feedback> feedbackList = new ArrayList<Feedback>();
            feedbackList = feedbackService.getFeedbackByCourseId(course.getId());
            float avg = 0;
            if (feedbackList.size() == 0) {
                avg = 0;
            } else {
                for (Feedback feedback : feedbackList) {
                    avg += feedback.getRatingStar();
                }
                avg = avg / feedbackList.size();
            }

            courseDtoHomeDetailsPopular.add(courseMapper.courseToCourseDtoHomeDetail(course, avg));
        }
        return courseDtoHomeDetailsPopular;
    }

    public List<CourseDtoHomeDetail> getSearchCourse(String keyword) {

        keyword = "%" + keyword + "%";
        List<Course> coursesSearch = courseRepository.searchCourseByKeyword(keyword);
        List<CourseDtoHomeDetail> courseDtoHomeDetailsSearch = new ArrayList<>();
        List<Course> courseAvailable = new ArrayList<>();
        for (Course course : coursesSearch) {
            if (course.getStatus() == 3) {
                courseAvailable.add(course);
            }
        }

        for (Course course : courseAvailable) {
            List<Feedback> feedbackList = new ArrayList<Feedback>();
            feedbackList = feedbackService.getFeedbackByCourseId(course.getId());
            float avg = 0;
            if (feedbackList.size() == 0) {
                avg = 0;
            } else {
                for (Feedback feedback : feedbackList) {
                    avg += feedback.getRatingStar();
                }
                avg = avg / feedbackList.size();
            }

            courseDtoHomeDetailsSearch.add(courseMapper.courseToCourseDtoHomeDetail(course, avg));
        }
        return courseDtoHomeDetailsSearch;
    }

    public List<CourseDtoHomeDetail> getCourseByPrice(Double from, Double to) {
        List<Course> coursesPrice = courseRepository.searchCourseByPrice(from, to);
        List<CourseDtoHomeDetail> courseDtoHomeDetailsPrice = new ArrayList<>();
        List<Course> courseAvailable = new ArrayList<>();
        for (Course course : coursesPrice) {
            if (course.getStatus() == 3) {
                courseAvailable.add(course);
            }
        }
        for (Course course : courseAvailable) {

            List<Feedback> feedbackList = new ArrayList<Feedback>();
            feedbackList = feedbackService.getFeedbackByCourseId(course.getId());
            float avg = 0;
            if (feedbackList.size() == 0) {
                avg = 0;
            } else {
                for (Feedback feedback : feedbackList) {
                    avg += feedback.getRatingStar();
                }
                avg = avg / feedbackList.size();
            }

            courseDtoHomeDetailsPrice.add(courseMapper.courseToCourseDtoHomeDetail(course, avg));
        }
        return courseDtoHomeDetailsPrice;
    }

    public List<CourseDtoHomeDetail> getCourseByHashtag(int topicId) {
        List<Course> coursesPrice = courseRepository.searchCourseHashtag(topicId);
        List<CourseDtoHomeDetail> courseDtoHomeDetailsHashtag = new ArrayList<>();
        List<Course> courseAvailable = new ArrayList<>();
        for (Course course : coursesPrice) {
            if (course.getStatus() == 3) {
                courseAvailable.add(course);
            }
        }
        for (Course course : courseAvailable) {

            List<Feedback> feedbackList = new ArrayList<Feedback>();
            feedbackList = feedbackService.getFeedbackByCourseId(course.getId());
            float avg = 0;
            if (feedbackList.size() == 0) {
                avg = 0;
            } else {
                for (Feedback feedback : feedbackList) {
                    avg += feedback.getRatingStar();
                }
                avg = avg / feedbackList.size();
            }

            courseDtoHomeDetailsHashtag.add(courseMapper.courseToCourseDtoHomeDetail(course, avg));
        }
        return courseDtoHomeDetailsHashtag;
    }

    public void submitCourse(Course course) {
        course.setStatus(0);
        courseRepository.save(course);
    }

    public void changeStatus(int id) throws Exception {
        Course course;
        var value = courseRepository.findById(id);
        if (value.isPresent()) {
            course = value.get();
        } else {
            throw new Exception("User not found");
        }
        if (course.getStatus() != -2) {
            course.setStatus(-2); // set status to Deactived
        } else {
            course.setStatus(3); // set status to Approved
        }
        courseRepository.save(course);
    }

}
