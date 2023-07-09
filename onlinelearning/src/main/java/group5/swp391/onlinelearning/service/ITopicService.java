package group5.swp391.onlinelearning.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import group5.swp391.onlinelearning.entity.Topic;

public interface ITopicService {
    public List<Topic> getTopics();

    public void addTopic(@NotNull Topic topic) throws Exception;

    public void deleteTopic(int id);

    public void updateTopic(@NotNull Topic topic) throws Exception;

    public Topic getTopicById(int id);
}
