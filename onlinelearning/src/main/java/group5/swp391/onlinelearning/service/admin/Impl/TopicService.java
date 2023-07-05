package group5.swp391.onlinelearning.service.admin.Impl;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

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
    public void addTopics(Topic topic) throws Exception {
        Topic topicTmp = topicRepository.findByHashtag(topic.getHashtag());
        if (topicTmp != null) {
            throw new Exception("Duplicate topic hashtag");
        }
        topicRepository.save(topicTmp);
    }

    @Override
    public void deleteTopic(int id) {
        topicRepository.deleteById(id);
    }

    @Override
    public void updateTopic(Topic topic) throws Exception {
        Optional<Topic> _topic = topicRepository.findById(topic.getId());
        Topic exitTopic;
        if (_topic.isPresent()) {
            exitTopic = _topic.get();
            exitTopic.setName(topic.getName());
            exitTopic.setHashtag(topic.getHashtag());
        } else {
            throw new Exception("Topic not found");
        }
    }

    @Override
    public Topic getTopicById(int id) {
        return topicRepository.findById(id).get();
    }

}
