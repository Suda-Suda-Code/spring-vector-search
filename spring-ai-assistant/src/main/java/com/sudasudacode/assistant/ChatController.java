package com.sudasudacode.assistant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sudasudacode.assistant.models.ChatRequest;
import com.sudasudacode.assistant.models.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatAssistantService service;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ChatResponse chat(@RequestBody ChatRequest request) throws JsonProcessingException {
        var message = service.getRecommendations(request.getMessage());
        return (new ChatResponse().reply(message));
    }

}
