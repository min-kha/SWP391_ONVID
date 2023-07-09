package group5.swp391.onlinelearning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.repository.LessionRepository;
import group5.swp391.onlinelearning.service.ILessionService;

@Service
public class LessionService implements ILessionService {
    @Autowired
    LessionRepository lessionRepository;

    @Override
    public List<Lesson> getLessionsByCourseId(int courseId) {
        List<Lesson> list = lessionRepository.findAllByCourseId(courseId);
        return list;
    }

}
