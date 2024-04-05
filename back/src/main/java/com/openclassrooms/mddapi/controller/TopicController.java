package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicResponseDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.ITopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TopicController is a REST controller that handles HTTP requests related to topics.
 * It uses ITopicService to perform operations on topics.
 */
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TopicController {

    private final ITopicService topicService;

    /**
     * Retrieves all topics.
     *
     * @return a ResponseEntity containing a list of TopicResponseDTOs
     */
    @GetMapping("/topics")
    public ResponseEntity<List<TopicResponseDTO>> retrieveAllTopics() {
        List<TopicResponseDTO> topics = topicService.getAllTopics();
        log.info("retrieved all topics");
        return ResponseEntity.ok(topics);
    }

    /**
     * Saves a topic.
     *
     * @param topic a TopicResponseDTO containing the topic data
     * @return a ResponseEntity containing the saved Topic
     */
    @PostMapping("/topics")
    public ResponseEntity<Topic> saveTopic(@RequestBody @Valid TopicResponseDTO topic) {
        Topic savedTopic = topicService.createTopic(topic);
        log.info("topic saved");
        return ResponseEntity.ok(savedTopic);
    }

    /**
     * Retrieves user subscribed topics.
     *
     * @return a ResponseEntity containing a list of TopicResponseDTOs
     */
    @GetMapping("/topics/subscribe")
    public ResponseEntity<List<TopicResponseDTO>> retrieveUserSubscribedTopics() {
        List<TopicResponseDTO> topics = topicService.getUserSubscribedTopics();
        return ResponseEntity.ok(topics);
    }

    /**
     * Subscribes a user to a topic.
     *
     * @param topicResponseDTO a TopicResponseDTO containing the topic data
     * @return a ResponseEntity containing the TopicResponseDTO
     */
    @PostMapping("/topics/subscribe")
    public ResponseEntity<TopicResponseDTO> subscribeToTopic(@RequestBody @Valid TopicResponseDTO topicResponseDTO) {
        topicService.saveSubScribe(topicResponseDTO.getId(), topicResponseDTO.getIsSubscribed());
        log.info("subscribed to topic requested");
        return ResponseEntity.ok(new TopicResponseDTO());
    }

    /**
     * Checks if a user is subscribed to a topic.
     *
     * @param id the ID of the topic
     * @return a ResponseEntity containing a boolean value indicating if the user is subscribed
     */
    @GetMapping("/topics/{id}/is-subscribed")
    public ResponseEntity<Boolean> isUserSubscribedToTopic(@PathVariable long id) {
        boolean isSubscribed = topicService.isUserSubscribedToTopicById(id);
        return ResponseEntity.ok(isSubscribed);
    }

}
