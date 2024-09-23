package com.sudasudacode.assistant.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudasudacode.assistant.prompt.ReplyMessage;
import com.sudasudacode.assistant.prompt.Suggestion;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sudasudacode.assistant.utils.Constants.*;

public final class AssistantSystemPrompt {

    private static final String SYSTEM_MESSAGE_$3 = "You are an AI assistant that recommends restaurants and dishes " +
            "based on user preferences. Analyze customer reviews to suggest relevant, highly rated restaurants and " +
            "summarize key insights from reviews. Provide personalized, structured responses based on real user " +
            "experiences.";
    private static final String SYSTEM_MESSAGE_$1 = "Request and response should be structured raw JSON without any markdown as mentioned in example";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final SystemMessage $1 = new SystemMessage(SYSTEM_MESSAGE_$1);
    private static final SystemMessage $2 = new SystemMessage(SYSTEM_MESSAGE_$2);
    private static final Suggestion suggestion = new Suggestion().name(STR_RESTAURANT_NAME)
            .id(STR_RESTAURANT_ID)
            .location(STR_RESTAURANT_LOCATION)
            .rating(1.0)
            .keyHighlights(List.of(STR_RESTAURANT_KEY_HIGHLIGHTS))
            .dishesToTry(List.of(STR_RESTAURANT_DISHES_TO_TRY))
            .reviews(List.of(STR_RESTAURANT_KEY_REVIEWS))
            .tags(LIST_RESTAURANT_TAGS_EXAMPLE)
            .aiSummary(STR_RESTAURANT_AI_SUMMARY_REVIEW);
    private static final ReplyMessage replyMessage = new ReplyMessage().message(STR_EXAMPLE_OF_REPLY_MESSAGE)
            .suggestionList(List.of(suggestion));
    private static final SystemMessage $3;
    public static final List<Message> systemMessages;

    static {
        try {
            $3 = new SystemMessage(objectMapper.writeValueAsString(replyMessage));
            systemMessages = new ArrayList<>(Arrays.asList($1, $2, $3));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
