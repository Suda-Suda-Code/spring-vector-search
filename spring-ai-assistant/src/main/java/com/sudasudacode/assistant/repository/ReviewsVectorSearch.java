package com.sudasudacode.assistant.repository;

import com.sudasudacode.assistant.models.Review;
import org.springframework.data.mongodb.repository.Aggregation;

import java.util.List;

public interface ReviewsVectorSearch {

    @Aggregation(pipeline = {
            "{" +
                    "'$vectorSearch':" +
                    "{ " +
                    "'index':'vector_index', " +
                    "'path': 'embedding' ," +
                    "'queryVector': ?1 ," +
                    "'numCandidates':10, " +
                    "'limit': 10 " +
                    "}" +
                    "}"

    })
    List<Review> search(float[] queryEmbedding);

}
