package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.repository.TopicRepository;
import group5.swp391.onlinelearning.service.impl.TopicService;
import group5.swp391.onlinelearning.utils.TestDataProvider;

public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTopics() {
        List<Topic> mockTopics = new ArrayList<>();
        mockTopics.add(TestDataProvider.createSampleTopic());
        mockTopics.add(TestDataProvider.createSampleTopic());
        when(topicRepository.findAll()).thenReturn(mockTopics);

        List<Topic> result = topicService.getTopics();

        assertEquals(mockTopics, result);
    }

    @Test
    public void testAddTopic() throws Exception {
        Topic topic = TestDataProvider.createSampleTopic();

        when(topicRepository.findById(topic.getId())).thenReturn(Optional.empty());
        when(topicRepository.findByHashtag(topic.getHashtag())).thenReturn(null);

        assertDoesNotThrow(() -> topicService.addTopic(topic));
        verify(topicRepository, times(1)).save(topic);
    }

    @Test
    public void testAddTopicDuplicateId() throws Exception {
        Topic topic = TestDataProvider.createSampleTopic();

        when(topicRepository.findById(topic.getId())).thenReturn(Optional.of(topic));

        assertThrows(InvalidInputException.class, () -> topicService.addTopic(topic));
        verify(topicRepository, never()).save(topic);
    }

    @Test
    public void testAddTopicDuplicateHashtag() throws Exception {
        Topic topic = TestDataProvider.createSampleTopic();

        when(topicRepository.findById(topic.getId())).thenReturn(Optional.empty());
        when(topicRepository.findByHashtag(topic.getHashtag())).thenReturn(TestDataProvider.createSampleTopic());

        assertThrows(InvalidInputException.class, () -> topicService.addTopic(topic));
        verify(topicRepository, never()).save(topic);
    }

    @Test
    public void testDeleteTopic() {
        int topicId = 1;

        assertDoesNotThrow(() -> topicService.deleteTopic(topicId));
        verify(topicRepository, times(1)).deleteById(topicId);
    }

    @Test
    public void testUpdateTopic() throws Exception {
        Topic topic = TestDataProvider.createSampleTopic();

        when(topicRepository.findById(topic.getId())).thenReturn(Optional.of(topic));
        when(topicRepository.findByHashtag(topic.getHashtag())).thenReturn(null);

        assertDoesNotThrow(() -> topicService.updateTopic(topic));
        verify(topicRepository, times(1)).save(topic);
    }

    @Test
    public void testUpdateTopicNotFound() {
        Topic topic = TestDataProvider.createSampleTopic();

        when(topicRepository.findById(topic.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () -> topicService.updateTopic(topic));
        verify(topicRepository, never()).save(topic);
    }

    @Test
    public void testUpdateTopicDuplicateHashtag() {
        Topic existingTopic = TestDataProvider.createSampleTopic();
        Topic topic = TestDataProvider.createSampleTopic();
        Topic updatingTopic = TestDataProvider.createSampleTopic();
        updatingTopic.setId(topic.getId());
        // assuming that updatingTopic has the same hashtag with exitingTopic
        updatingTopic.setHashtag(existingTopic.getHashtag());

        when(topicRepository.findById(updatingTopic.getId())).thenReturn(Optional.of(topic));
        // hashtag is duplicate
        when(topicRepository.findByHashtag(updatingTopic.getHashtag())).thenReturn(existingTopic);

        assertThrows(InvalidInputException.class, () -> topicService.updateTopic(updatingTopic));
        verify(topicRepository, never()).save(updatingTopic);
    }

    @Test
    public void testGetTopicById() {
        int topicId = 1;
        Topic mockTopic = TestDataProvider.createSampleTopic();

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(mockTopic));

        Topic result = topicService.getTopicById(topicId);

        assertEquals(mockTopic, result);
    }
}
