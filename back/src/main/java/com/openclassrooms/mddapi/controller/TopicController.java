package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicResponseDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.ITopicService;
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

    @PostMapping("/topic/{id}")
    public ResponseEntity<?> subscribeTopic(@PathVariable Long id) {
        topicService.subscribe(id);
        log.info("User subscribed to topic with id: " + id);
        return ResponseEntity.ok().build();
    }

}
