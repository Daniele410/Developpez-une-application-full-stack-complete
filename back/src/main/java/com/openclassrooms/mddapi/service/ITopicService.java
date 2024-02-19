package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicResponseDTO;
import com.openclassrooms.mddapi.model.Topic;

import java.util.List;

public interface ITopicService {
    Topic getTopicById(Long id);

    List<Topic> getAllTopics();

    List<Topic> getUserSubscribedTopics();

    Topic createTopic(TopicResponseDTO topicResponseDTO);

    void subscribe(long topicId);

    void unsubscribe(long topicsId);
}
