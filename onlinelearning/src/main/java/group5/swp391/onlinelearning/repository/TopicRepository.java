package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import group5.swp391.onlinelearning.entity.Topic;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    Topic findByName(String name);

    Topic findByHashtag(String hashtag);
}
