package group5.swp391.onlinelearning.service;

import java.util.List;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Learn;
import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.entity.User;

public interface ILearnService {

    public void setLearnDefault(List<Course> courses, User student);

    public List<Learn> getListLearnByLessonIdAndStudentId(List<Lesson> lessons, User student);
}
