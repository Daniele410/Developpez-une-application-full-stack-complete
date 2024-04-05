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

/**
 * TopicServiceImpl is a service class that implements ITopicService.
 * It provides methods to interact with the TopicRepository and perform operations on Topic entities.
 */
@Service
public class TopicServiceImpl implements ITopicService {

    private static final Logger log = LogManager.getLogger("TopicServiceImpl");

    private final TopicRepository topicRepository;

    private final UserServiceImpl userService;

    /**
     * Constructs a new TopicServiceImpl with the specified TopicRepository and UserServiceImpl.
     *
     * @param topicRepository the TopicRepository to be used
     * @param userService     the UserServiceImpl to be used
     */
    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, UserServiceImpl userService) {
        this.topicRepository = topicRepository;
        this.userService = userService;
    }

    /**
     * Retrieves a Topic entity by its id.
     *
     * @param id the id of the Topic entity to retrieve
     * @return the Topic entity with the specified id
     * @throws TopicNotFoundException if the Topic entity could not be found
     */
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


    /**
     * Retrieves a Topic entity by its title.
     *
     * @param name the title of the Topic entity to retrieve
     * @return the Topic entity with the specified title wrapped in an Optional
     */
    @Override
    public Optional<Topic> getTopicByTitle(String name) {
        return topicRepository.findTopicByTitle(name);
    }

    /**
     * Retrieves all Topic entities and maps them to TopicResponseDTOs.
     * Also sets the 'isSubscribed' field for each TopicResponseDTO based on the user's subscriptions.
     *
     * @return a list of all TopicResponseDTOs
     */
    @Override
    public List<TopicResponseDTO> getAllTopics() {
        List<Long> myTopicsId = getUserSubscribedTopicsPart().stream().map(Topic::getId).toList();
        List<TopicResponseDTO> allTopics = topicRepository.findAll().stream().map(TopicResponseDTO::new).toList();
        allTopics.forEach(topic -> topic.setIsSubscribed(myTopicsId.contains(topic.getId())));
        return allTopics;
    }

    /**
     * Retrieves all Topic entities that the current user is subscribed to.
     *
     * @return a list of all Topic entities that the current user is subscribed to
     */
    @Override
    public List<Topic> getUserSubscribedTopicsPart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<Topic> topics = topicRepository.findAllTopicsByUsersId(user.getId());
        return topics;
    }

    /**
     * Retrieves all Topic entities that the current user is subscribed to and maps them to TopicResponseDTOs.
     * Also sets the 'isSubscribed' field for each TopicResponseDTO to true.
     *
     * @return a list of all TopicResponseDTOs that the current user is subscribed to
     */
    @Override
    public List<TopicResponseDTO> getUserSubscribedTopics() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<Long> myTopicsId = getUserSubscribedTopicsPart().stream().map(Topic::getId).toList();
        List<TopicResponseDTO> topics = topicRepository.findAllTopicsByUsersId(user.getId()).stream().map(TopicResponseDTO::new).toList();
        topics.forEach(topic -> topic.setIsSubscribed(myTopicsId.contains(topic.getId())));
        return topics;
    }

    /**
     * Creates a new Topic entity from a TopicResponseDTO and saves it in the repository.
     *
     * @param topicResponseDTO the TopicResponseDTO containing the data to create the Topic entity with
     * @return the created Topic entity
     * @throws TopicNotFoundException if a Topic entity with the same title already exists
     */
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

    /**
     * Subscribes or unsubscribes the current user to a Topic entity based on the 'isSubscribed' parameter.
     *
     * @param topicId      the id of the Topic entity to subscribe/unsubscribe to
     * @param isSubscribed whether the user should be subscribed or unsubscribed
     * @return a message indicating the success of the operation
     */
    @Override
    public String saveSubScribe(long topicId, Boolean isSubscribed) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Topic topic = this.topicRepository.findById(topicId).orElseThrow();

        if (isSubscribed) {
            topic.getUsers().add(user);
            topicRepository.save(topic);
            log.info("Subscribed to topic successfully");
            return "Subscribed to topic successfully";
        } else {
            unsubscribe(topicId);
            log.info("Unsubscribed to topic successfully");
            return "Unsubscribed to topic successfully";
        }
    }

    /**
     * Checks if the current user is subscribed to a Topic entity.
     *
     * @param topics the Topic entity to check
     * @return true if the user is subscribed to the Topic entity, false otherwise
     */
    @Override
    public boolean isUserSubscribed(Topic topics) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        return topics.getUsers().contains(user);
    }

    /**
     * Checks if the current user is subscribed to a Topic entity by its id.
     *
     * @param topicId the id of the Topic entity to check
     * @return true if the user is subscribed to the Topic entity, false otherwise
     */
    @Override
    public boolean isUserSubscribedToTopicById(long topicId) {
        Topic topic = this.topicRepository.findById(topicId).orElseThrow();
        return isUserSubscribed(topic);
    }

    /**
     * Unsubscribes the current user from a Topic entity.
     *
     * @param topicsId the id of the Topic entity to unsubscribe from
     */
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
