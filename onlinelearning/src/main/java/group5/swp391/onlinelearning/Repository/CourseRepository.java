package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import group5.swp391.onlinelearning.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    
}
