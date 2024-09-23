package com.sudasudacode.assistant.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document(collection = "restaurant_reviews")
public class Review {

    @Id
    String id;

    String restaurant_id;

    String review;

    float[] embedding;

    @Override
    public String toString() {
        return "Review:\n" + review ;

    }
}
