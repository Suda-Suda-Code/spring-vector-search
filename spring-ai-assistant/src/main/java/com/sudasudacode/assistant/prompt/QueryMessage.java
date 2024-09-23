package com.sudasudacode.assistant.prompt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sudasudacode.assistant.models.Restaurant;
import com.sudasudacode.assistant.models.Review;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class QueryMessage {

    private String query;

    private List<Review> reviews;

    private List<Restaurant> restaurants;

}
