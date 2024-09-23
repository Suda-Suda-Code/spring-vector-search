package com.sudasudacode.assistant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudasudacode.assistant.utils.AssistantSystemPrompt;
import com.sudasudacode.assistant.models.Restaurant;
import com.sudasudacode.assistant.models.Review;
import com.sudasudacode.assistant.prompt.QueryMessage;
import com.sudasudacode.assistant.prompt.ReplyMessage;
import com.sudasudacode.assistant.repository.RestaurantRepository;
import com.sudasudacode.assistant.repository.ReviewsRepository;
import com.sudasudacode.assistant.utils.SortRestaurantByRating;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatAssistantService {

    private final ReviewsRepository reviewsRepository;
    private final EmbeddingModel embeddingModel;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestaurantRepository restaurantRepository;
    private final ChatModel chatModel;

    public ReplyMessage getRecommendations(String message) throws JsonProcessingException {
        // embed
        float[] queryEmbedding = this.embeddingModel.embed(message);
        // query ( RETRIEVE )
        var results = reviewsRepository.similaritySearch(queryEmbedding);
        var restaurantIds = results.stream().map(Review::getRestaurant_id).collect(Collectors.toList());
        List<Restaurant> restaurants = restaurantRepository.findByRestaurantId(restaurantIds);
        return query(message, results, restaurants);
    }


    public ReplyMessage query(String query, List<Review> reviews, List<Restaurant> restaurants) throws JsonProcessingException {
        // contextual query with restaurant and review information
        QueryMessage message = new QueryMessage()
                .query(query)
                .restaurants(restaurants)
                .reviews(reviews);
        UserMessage userMessage = new UserMessage(objectMapper.writeValueAsString(message));
        // Query your operational collection to see if any conversation exists or else start a conversation with
        // system prompt
        List<Message> messages = AssistantSystemPrompt.systemMessages;
        messages.add(userMessage);
        // AUGMENT and GENERATE
        String response = chatModel.call(messages.toArray(new Message[0]));
        // Deserialize
        ReplyMessage replyMessage = objectMapper.readValue(response, ReplyMessage.class);
        Comparator sortRestaurantByRating = new SortRestaurantByRating();
        replyMessage.suggestionList().sort(sortRestaurantByRating);
        return replyMessage;
    }

}
