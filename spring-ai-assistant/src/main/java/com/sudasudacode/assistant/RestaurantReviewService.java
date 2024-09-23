package com.sudasudacode.assistant;

import com.sudasudacode.assistant.models.Review;
import com.sudasudacode.assistant.repository.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantReviewService {

    private final ReviewsRepository reviewsRepository;
    private final EmbeddingModel embeddingModel;

    public void addReview(Review review) {
        // do this async or in non-blocking code and update embeddings later
        var embedding = this.embeddingModel.embed(review.getReview());
        review.setEmbedding(embedding);
        reviewsRepository.save(review);
    }

}
