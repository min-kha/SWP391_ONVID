package group5.swp391.onlinelearning.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.View;
import group5.swp391.onlinelearning.service.IViewService;
import group5.swp391.onlinelearning.repository.ViewRepositoty;

@Service
public class ViewService implements IViewService {
    @Autowired
    ViewRepositoty viewRepositoty;

    @Autowired
    CourseService courseService;

    @Override
    public long addView(int courseId) {
        Course course = courseService.getCourseByCourseId(courseId);
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);

        View view = View.builder().course(course).date(date).viewNumber(1).build();
        View viewRes = viewRepositoty.save(view);
        return getViewNumberByCourseId(courseId);
    }

    @Override
    public long getViewNumberByCourseId(int courseId) {
        List<View> views = getAllViews();
        long totalView = 0;
        for (View view : views) {
            if (courseId == view.getCourse().getId()) {
                totalView = totalView + view.getViewNumber();
            }
        }
        return totalView;
    }

    @Override
    public List<View> getAllViews() {
        return viewRepositoty.findAll();
    }

}
