package group5.swp391.onlinelearning.service;

import java.util.List;

import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Lession;

@Service
public interface ILessionService {
    public List<Lession> getLessionsByCourseId(int courseId);
}
