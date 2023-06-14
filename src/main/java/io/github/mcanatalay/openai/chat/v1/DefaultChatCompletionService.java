package io.github.mcanatalay.openai.chat.v1;

import io.github.mcanatalay.openai.chat.v1.model.ChatCompletionRequest;
import io.github.mcanatalay.openai.chat.v1.model.ChatCompletionResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class DefaultChatCompletionService implements ChatCompletionService {
    private static final String OPENAI_URL = "https://api.openai.com/v1";
    private final RestTemplate restTemplate;

    public DefaultChatCompletionService() {
        this.restTemplate = new RestTemplateBuilder()
                .errorHandler(new OpenAIErrorHandler())
                .build();
    }

    public ChatCompletionResponse createChatCompletion(String authToken, ChatCompletionRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<ChatCompletionRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate
                .exchange(OPENAI_URL + "/chat/completions", HttpMethod.POST, entity, ChatCompletionResponse.class)
                .getBody();
    }

    public ChatCompletionResponse createChatCompletion(ChatCompletionRequest request) {
        throw new UnsupportedOperationException("This method is not supported without an auth token");
    }
}
