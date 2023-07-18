package group5.swp391.onlinelearning.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import group5.swp391.onlinelearning.entity.CV;
import group5.swp391.onlinelearning.entity.User;

public interface ICVService {
    public List<CV> getCVs();

    public void addCV(@NotNull CV cv) throws Exception;

    public void deleteCV(int id);

    public void updateCV(@NotNull CV cv) throws Exception;

    public CV getCVById(int id);

    public CV getCVByTeacherId(int teacherId);

    public void createNewCV(User teacherId, String pdf_link);
}
