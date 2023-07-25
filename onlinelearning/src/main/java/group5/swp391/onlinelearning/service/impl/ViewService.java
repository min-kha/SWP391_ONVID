package group5.swp391.onlinelearning.service.impl;

import java.util.Calendar;
import java.util.Date;
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
        Calendar calendar = Calendar.getInstance();
        Date currentDate = (Date) calendar.getTime();
        View viewRes = getViewNumberByCourseId(courseId);
        View view = new View();
        if (viewRes == null) {
            view = View.builder().course(course).date(currentDate)
                    .viewNumber(1).build();
        } else {
            view = View.builder().course(course).date(currentDate)
                    .viewNumber(getViewNumberByCourseId(courseId).getViewNumber() + 1).build();
        }
        viewRepositoty.save(view);
        return getViewNumberByCourseId(courseId).getViewNumber();
    }

    @Override
    public View getViewNumberByCourseId(int courseId) {
        View view = viewRepositoty.getViewNumberByCourseId(courseId);
        return view;
    }

    @Override
    public List<View> getAllViews() {
        return viewRepositoty.findAll();
    }

    @Override
    public Long getViewNumberByTeacherIdInDay(int teacherId) {
        if (viewRepositoty.getViewNumberByTeacherIdInToday(teacherId) == null)
            return Long.valueOf(0);
        return viewRepositoty.getViewNumberByTeacherIdInToday(teacherId);
    }

    @Override
    public Long getViewNumberByTeacherIdInYesterday(int teacherId) {
        if (viewRepositoty.getViewNumberByTeacherIdInYesterday(teacherId) == null)
            return Long.valueOf(0);
        return viewRepositoty.getViewNumberByTeacherIdInYesterday(teacherId);
    }

    // create new course --> create empty view
    @Override
    public void createEmptyView(Course course) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = (Date) calendar.getTime();
        View view = View.builder()
                .course(course)
                .date(currentDate)
                .viewNumber(0)
                .build();
    }

}
