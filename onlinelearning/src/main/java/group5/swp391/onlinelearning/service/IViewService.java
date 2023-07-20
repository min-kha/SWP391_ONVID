package group5.swp391.onlinelearning.service;

import java.util.List;

import group5.swp391.onlinelearning.entity.View;

public interface IViewService {
    public long addView(int courseId);

    public View getViewNumberByCourseId(int courseId);

    public List<View> getAllViews();

    public Long getViewNumberByTeacherIdInDay(int teacherId);

    public Long getViewNumberByTeacherIdInYesterday(int teacherId);
}
