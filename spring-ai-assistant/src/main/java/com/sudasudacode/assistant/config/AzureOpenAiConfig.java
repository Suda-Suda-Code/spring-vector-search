package com.sudasudacode.assistant.config;

import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.azure.openai.AzureOpenAiResponseFormat;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureOpenAiConfig {

    public AzureOpenAiChatOptions azureOpenAiChatOptions() {
        var options = new AzureOpenAiChatOptions();
        options.setDeploymentName("gpt-4o");
        options.setResponseFormat(AzureOpenAiResponseFormat.JSON);
        return options;

    }

}
