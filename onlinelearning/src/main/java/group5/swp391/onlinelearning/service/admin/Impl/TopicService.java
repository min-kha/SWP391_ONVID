package group5.swp391.onlinelearning.service.admin.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.repository.TopicRepository;
import group5.swp391.onlinelearning.service.admin.ITopicService;

@Service
public class TopicService implements ITopicService {
    @Autowired
    TopicRepository topicRepository;

    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    @Override
    public boolean addTopics(Topic topic) {
        Topic _topic = topicRepository.findByName(topic.getName());
        if (_topic != null) {
            return false;
        }
        topicRepository.save(topic);
        return true;
    }

    @Override
    public void deleteTopic(int id) {
        topicRepository.deleteById(id);
    }

    @Override
    public void updateTopic(Topic topic) {
        Optional<Topic> _topic = topicRepository.findById(topic.getId());
        Topic exitTopic;
        if (_topic.isPresent()) {
            exitTopic = _topic.get();
            exitTopic.setName(topic.getName());
            exitTopic.setHashtag(topic.getHashtag());
        } else {
            // throw new NotFoundException("Topic not found");
        }
    }
}
