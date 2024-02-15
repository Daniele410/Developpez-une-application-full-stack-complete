package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Topic;

import java.util.List;

public interface ITopicService {
    Topic getTopicById(Long id);

    List<Topic> getAllTopics();

    List<Topic> getUserSubscribedTopics();

    void subscribe(long topicsId);

    void unsubscribe(long topicsId);
}
