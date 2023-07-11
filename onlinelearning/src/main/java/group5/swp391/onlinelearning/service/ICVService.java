package group5.swp391.onlinelearning.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import group5.swp391.onlinelearning.entity.CV;

public interface ICVService {
    public List<CV> getCVs();

    public void addCV(@NotNull CV cv) throws Exception;

    public void deleteCV(int id);

    public void updateCV(@NotNull CV cv) throws Exception;

    public CV getCVById(int id);
}
