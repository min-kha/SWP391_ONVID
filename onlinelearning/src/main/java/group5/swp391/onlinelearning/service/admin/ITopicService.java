package group5.swp391.onlinelearning.service.admin;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Topic;

public interface ITopicService {
    public List<Topic> getTopics();

    public void addTopic(Topic topic) throws Exception;

    public void deleteTopic(int id);

    public void updateTopic(Topic topic) throws Exception;

    public Topic getTopicById(@NotNull int id);
}
