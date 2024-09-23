package com.sudasudacode.assistant.ai.gemini;


import lombok.Getter;
import lombok.Setter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

import static com.sudasudacode.assistant.ai.gemini.GeminiApiConstants.BASE_URI;

public class GeminiApi {

    private final RestClient restClient;
    private final String apiKey;
    private final String embeddingModel;
    private String path;


    public GeminiApi(GeminiApiConfig config) {
        Assert.notNull(config.apiKey(), "Gemini API can not be null.");
        Assert.notNull(config.model(), "Gemini API model can not be null.");
        this.apiKey = config.apiKey();
        this.embeddingModel = config.model();
        this.path = String.format("/v1beta/models/%s:embedContent", embeddingModel);
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URI)
                .defaultHeaders((h) -> {
                    h.setContentType(MediaType.APPLICATION_JSON);
                })
                .build();
    }


    public <T> ResponseEntity<GeminiEmbeddingResponse> embeddings(GeminiEmbeddingRequest embeddingRequest) {
        Assert.notNull(embeddingRequest, "The request body can not be null.");
        Assert.notNull(embeddingRequest.getContent(), "The input can not be null.");

        return this.restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(this.path)
                        .queryParam("key", apiKey).build()
                )
                .body(embeddingRequest)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
    }


    public static class GeminiEmbeddingRequest {

        public Content getContent() {
            return content;
        }

        private static Content content;

        public GeminiEmbeddingRequest(List<String> input) {
            this.content = new Content(input);
        }

        @Setter
        @Getter
        private static class Content {
            private final List<Part> parts;

            protected Content(List<String> input) {
                this.parts = input.stream().map(i -> new Part(i)).collect(Collectors.toUnmodifiableList());
            }
        }

        @Getter
        @Setter
        private static class Part {

            private final String text;

            protected Part(String input) {
                this.text = input;
            }

        }
    }


    @Getter
    @Setter
    public static class GeminiEmbeddingResponse {

        private GeminiEmbeddingResponse.Embedding embedding;

        @Setter
        @Getter
        public static class Embedding {
            float[] values;
        }
    }


}

