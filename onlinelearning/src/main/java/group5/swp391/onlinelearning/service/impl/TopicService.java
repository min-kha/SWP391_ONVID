package group5.swp391.onlinelearning.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.repository.TopicRepository;
import group5.swp391.onlinelearning.service.ITopicService;

@Service
public class TopicService implements ITopicService {
    @Autowired
    TopicRepository topicRepository;

    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    @Override
    public void addTopic(@NotNull Topic topic) throws Exception {
        if (topicRepository.findById(topic.getId()).isPresent()) {
            throw new InvalidInputException("", "topic.duplicate", "Topic is already exists");
        }
        if (isDuplicateHashtag(topic)) {
            throw new InvalidInputException("hashtag", "hashtag.duplicate", "Duplicate topic hashtag");
        }
        topicRepository.save(topic);
    }

    @Override
    public void deleteTopic(int id) {
        topicRepository.deleteById(id);
    }

    @Override
    public void updateTopic(@NotNull Topic topic) throws Exception {
        Optional<Topic> topicTmp = topicRepository.findById(topic.getId());
        if (topicTmp.isPresent()) {
            if (isDuplicateHashtag(topic)) {
                throw new InvalidInputException("hashtag", "hashtag.duplicate", "Duplicate topic hashtag");
            }
            topicRepository.save(topic);
        } else {
            throw new InvalidInputException("id", "topic.notfound", "Topic not found");
        }
    }

    @Override
    public Topic getTopicById(int id) {
        return topicRepository.findById(id).orElse(null);
    }

    private boolean isDuplicateHashtag(Topic topic) {
        return topicRepository.findByHashtag(topic.getHashtag()) != null;
    }
}
