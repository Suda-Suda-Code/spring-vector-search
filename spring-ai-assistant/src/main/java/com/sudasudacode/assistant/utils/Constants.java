package com.sudasudacode.assistant.utils;

import lombok.experimental.UtilityClass;

import java.util.List;

public class Constants {

    public static final String SYSTEM_MESSAGE_$1 = "You are an AI assistant that recommends restaurants and dishes based on user preferences. Analyze customer reviews to suggest relevant, highly rated restaurants and summarize key insights from reviews. Provide personalized, structured responses based on real user experiences.";
    public static final String SYSTEM_MESSAGE_$2 = "Request and response should be structured as mentioned in example.";
    public static final String STR_RESTAURANT_NAME = "Restaurant Name";
    public static final String STR_RESTAURANT_ID = "Unique Id of the restaurant : restaurant_id";
    public static final String STR_RESTAURANT_LOCATION = "restaurant location";
    public static final String STR_RESTAURANT_KEY_HIGHLIGHTS = "Key highlights from the list of reviews given in input message";
    public static final String STR_RESTAURANT_KEY_REVIEWS = "Key reviews about the restaurant";
    public static final List<String> LIST_RESTAURANT_TAGS_EXAMPLE = List.of("Affordable", "Value for Money", "Great Ambience");
    public static final String STR_RESTAURANT_AI_SUMMARY_REVIEW = "What customers say about the restaurant from the list of review given as input";
    public static final String STR_RESTAURANT_DISHES_TO_TRY = "Restaurant special dishes to try";
    public static final String STR_EXAMPLE_OF_REPLY_MESSAGE = "Example of response message: Greeting message to user and suggesting which one to pick";

}
