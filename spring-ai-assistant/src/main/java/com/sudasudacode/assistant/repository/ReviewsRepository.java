package com.sudasudacode.assistant.repository;

import com.sudasudacode.assistant.models.Review;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewsRepository extends MongoRepository<Review, String> {


    @Aggregation(pipeline = {
           "{'$vectorSearch': { 'index': 'vector_index', 'path': 'embedding', 'queryVector': ?0, 'numCandidates': 10, 'limit': 10 } }",
            "{ $project: { embedding: 0 } }",
    })
    List<Review> similaritySearch(float[] queryEmbedding);

}
