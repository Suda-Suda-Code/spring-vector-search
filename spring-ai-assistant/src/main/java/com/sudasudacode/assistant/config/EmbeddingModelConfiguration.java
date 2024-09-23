package com.sudasudacode.assistant.config;

import com.sudasudacode.assistant.ai.gemini.GeminiApiConfig;
import com.sudasudacode.assistant.ai.gemini.GeminiEmbeddingModel;
import org.springframework.ai.embedding.EmbeddingModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingModelConfiguration {

    @Value("${spring.ai.gemini.api-key}")
    private String apiKey;

    @Value("${spring.ai.gemini.model}")
    private String model;


    @Bean
    public EmbeddingModel embeddingModel() {
        /**
         * ---------------------------------------------------------------------------------------------------
         * Out of box embedding model from Spring AI
         *
         * return new OpenAiEmbeddingModel(new OpenAiApi("YOUR_API_KEY"));
         *
         * List of supported embedding models
         * https://docs.spring.io/spring-ai/reference/api/embeddings.html#available-implementations
         *
         * ---------------------------------------------------------------------------------------------------
         * or Write your integration with any custom model like one below
         */
        return new GeminiEmbeddingModel(new GeminiApiConfig(apiKey, model));
    }

}
