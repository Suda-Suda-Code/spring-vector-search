package com.sudasudacode.assistant.ai.gemini;


import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.*;
import org.springframework.ai.embedding.observation.DefaultEmbeddingModelObservationConvention;
import org.springframework.ai.embedding.observation.EmbeddingModelObservationContext;
import org.springframework.ai.embedding.observation.EmbeddingModelObservationConvention;
import org.springframework.ai.embedding.observation.EmbeddingModelObservationDocumentation;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;

import java.util.List;

import static com.sudasudacode.assistant.ai.gemini.GeminiApiConstants.*;

public class GeminiEmbeddingModel extends AbstractEmbeddingModel {

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(GeminiEmbeddingModel.class);
    // What is this
    private static final EmbeddingModelObservationConvention DEFAULT_OBSERVATION_CONVENTION = new DefaultEmbeddingModelObservationConvention();
    // Retry Template
    private final RetryTemplate retryTemplate;
    // What is this
    private final ObservationRegistry observationRegistry;
    // API
    private final GeminiApi geminiApi;

    private EmbeddingModelObservationConvention observationConvention;


    public GeminiEmbeddingModel(GeminiApiConfig config) {
        this.retryTemplate = RetryUtils.DEFAULT_RETRY_TEMPLATE;
        this.observationRegistry = ObservationRegistry.NOOP;
        this.geminiApi = new GeminiApi(config);
    }


    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        EmbeddingOptions embeddingOptions = EmbeddingOptionsBuilder.builder()
                .withModel(TEXT_EMBEDDING_MODEL)
                .withDimensions(TEXT_EMBEDDING_MODEL_DIMENSIONS)
                .build();

        EmbeddingModelObservationContext observationContext = EmbeddingModelObservationContext.builder()
                .embeddingRequest(request)
                .provider(GEMINI_PROVIDER)
                .requestOptions(embeddingOptions).build();

        var input = request.getInstructions();
        var rqx = new GeminiApi.GeminiEmbeddingRequest(input);
        return EmbeddingModelObservationDocumentation.EMBEDDING_MODEL_OPERATION
                .observation(
                        this.observationConvention,
                        DEFAULT_OBSERVATION_CONVENTION,
                        () -> observationContext, this.observationRegistry
                ).observe(() -> {
                    var apiEmbeddingResponse = this.retryTemplate.execute((ctx) -> this.geminiApi.embeddings(rqx).getBody());
                    if (apiEmbeddingResponse == null) {
                        logger.warn("No embeddings returned for request: {}", request);
                        return new EmbeddingResponse(List.of());
                    } else {
                        Embedding embedding = new Embedding(apiEmbeddingResponse.getEmbedding().values, 0);
                        EmbeddingResponse embeddingResponse = new EmbeddingResponse(List.of(embedding));
                        observationContext.setResponse(embeddingResponse);
                        return embeddingResponse;
                    }
                });
    }

    @Override
    public float[] embed(Document document) {
        Assert.notNull(document, "Document must not be null");
        return this.embed(document.getFormattedContent(MetadataMode.EMBED));
    }

}

