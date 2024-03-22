package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicResponseDTO;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements ITopicService {

    private static final Logger log = LogManager.getLogger("TopicServiceImpl");

    private final TopicRepository topicRepository;

    private final UserServiceImpl userService;


    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, UserServiceImpl userService) {
        this.topicRepository = topicRepository;
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public Optional<Topic> getTopicById(Long id) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if (topicOptional.isPresent()) {
            return topicOptional;
        } else {
            throw new TopicNotFoundException("Topic not found");
        }
    }

    @Override
    public Optional<Topic> getTopicByTitle(String name) {
        return topicRepository.findTopicByTitle(name);
    }

    @Override
    public List<TopicResponseDTO> getAllTopics() {
        List<Long> myTopicsId = getUserSubscribedTopics().stream().map(Topic::getId).toList();
        List<TopicResponseDTO> allTopics = topicRepository.findAll().stream().map(TopicResponseDTO::new).toList();
        allTopics.forEach(topic -> topic.setIsSubscribed(myTopicsId.contains(topic.getId())));
        return allTopics;
    }

    @Override
    public List<Topic> getUserSubscribedTopics() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<Topic> topics = topicRepository.findAllTopicsByUsersId(user.getId());
        return topics;
    }

    @SneakyThrows
    @Override
    public Topic createTopic(TopicResponseDTO topicResponseDTO) {
        if (getTopicByTitle(topicResponseDTO.getTitle()).isPresent()) {
            throw new TopicNotFoundException("Topic already exists");
        } else {
        Topic topic = Topic.builder()
                .title(topicResponseDTO.getTitle())
                .description(topicResponseDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
        return topicRepository.save(topic);
    }
    }

    @Override
    public String saveSubScribe(long topicId, Boolean isSubscribed) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Topic topic = this.topicRepository.findById(topicId).orElseThrow();
        if (isSubscribed)
            if (isUserSubscribed(topic)) {
                log.info("User is already subscribed to the topic");
                return "Topic already subscribed to";
            }
        if (isSubscribed) {
            topic.getUsers().add(user);
            topicRepository.save(topic);
            return "Subscribed to topic successfully";
        } else {
            unsubscribe(topicId);
            return "Unsubscribed to topic successfully";
        }
    }

    @Override
    public boolean isUserSubscribed(Topic topics) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        return topics.getUsers().contains(user);
    }

    @Override
    public boolean isUserSubscribedToTopicById(long topicId) {
        Topic topic = this.topicRepository.findById(topicId).orElseThrow();
        return isUserSubscribed(topic);
    }

    @Override
    public void unsubscribe(long topicsId) {
        Topic topic = topicRepository.findById(topicsId).orElseThrow();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        if (topic.getUsers().contains(user)) {  // if user is already subscribed to the topic
            topic.getUsers().remove(user);
            topicRepository.save(topic);
        } else {
            log.info("User is not subscribed to the topic");
        }
    }


}
