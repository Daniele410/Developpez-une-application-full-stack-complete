package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicResponseDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @Override
    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).get();
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public List<Topic> getUserSubscribedTopics() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<Topic> topics = topicRepository.findAllById(Collections.singleton(user.getId()));
        return topics;
    }

//    @Override
//    public void subscribe(Topic topic) {
//        Topic topicFind = topicRepository.findById(topic.getId()).orElseThrow();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User user = userService.getUserByEmail(userDetails.getUsername());
//        if (topicFind.getUsers().contains(user)) {  // if user is already subscribed to the topic
//            log.info("User is already subscribed to the topic");
//        } else {
//            topic.getUsers().add(user);
//            topicRepository.save(topic);
//        }
//    }

    @Override
    public Topic createTopic(TopicResponseDTO topicResponseDTO) {
        Topic topic = new Topic().builder()
                .title(topicResponseDTO.getTitle())
                .description(topicResponseDTO.getDescription())
                .build();
        return topicRepository.save(topic);
    }

    @Override
    public void subscribe(long topicId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Topic topic = this.topicRepository.findById(topicId).orElseThrow();
        if (isUserSubscribed(topic)) {
            return;
        }
        topic.getUsers().add(user);
        user.getTopics().add(topic);
        topicRepository.save(topic);
    }

    private boolean isUserSubscribed(Topic topics) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        return topics.getUsers().stream().anyMatch(u -> Objects.equals(u.getId(), user.getId()));
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
            return;
        }
    }


}
