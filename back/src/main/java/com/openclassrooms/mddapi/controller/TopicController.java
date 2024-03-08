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
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TopicController {

    private final ITopicService topicService;

    @GetMapping("/topics")
    public ResponseEntity<List<TopicResponseDTO>> retrieveAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        List<TopicResponseDTO> responseDTOs = topics.stream()
                .map(TopicResponseDTO::new)
                .collect(Collectors.toList());
        log.info("retrieved all topics");
        return ResponseEntity.ok(responseDTOs);
    }

    @PostMapping("/topics")
    public ResponseEntity<Topic> saveTopic(@RequestBody @Valid TopicResponseDTO topic) {
        Topic savedTopic = topicService.createTopic(topic);
        log.info("topic saved");
        return ResponseEntity.ok(savedTopic);
    }

    @GetMapping("/topics/subscribed")
    public ResponseEntity<List<TopicResponseDTO>> retrieveUserSubscribedTopics() {
        List<Topic> topics = topicService.getUserSubscribedTopics();
        List<TopicResponseDTO> responseDTOs = topics.stream()
                .map(TopicResponseDTO::new)
                .collect(Collectors.toList());
        log.info("retrieved user subscribed topics");
        return ResponseEntity.ok(responseDTOs);
    }


}