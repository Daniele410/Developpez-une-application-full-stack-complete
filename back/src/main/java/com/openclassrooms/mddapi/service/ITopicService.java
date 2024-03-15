package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicResponseDTO;
import com.openclassrooms.mddapi.model.Topic;

import java.util.List;
import java.util.Optional;

public interface ITopicService {
    Optional<Topic> getTopicById(Long id);

    Optional<Topic> getTopicByTitle(String name);

    List<Topic> getAllTopics();

    List<Topic> getUserSubscribedTopics();

    Topic createTopic(TopicResponseDTO topicResponseDTO);

    String saveSubScribe(long topicId, Boolean isSubscribed);

    boolean isUserSubscribed(Topic topics);

    void unsubscribe(long topicsId);
}
