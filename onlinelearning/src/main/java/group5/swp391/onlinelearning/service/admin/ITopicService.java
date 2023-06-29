package group5.swp391.onlinelearning.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Topic;

@Service
public interface ITopicService {
    public List<Topic> getTopics();

    public boolean addTopics(Topic topic);

    public void deleteTopic(int id);

    public void updateTopic(Topic topic);
}
