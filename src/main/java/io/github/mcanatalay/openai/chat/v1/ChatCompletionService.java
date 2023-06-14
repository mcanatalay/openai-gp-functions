package io.github.mcanatalay.openai.chat.v1;

import io.github.mcanatalay.openai.chat.v1.model.ChatCompletionRequest;
import io.github.mcanatalay.openai.chat.v1.model.ChatCompletionResponse;

public interface ChatCompletionService {
    ChatCompletionResponse createChatCompletion(String authToken, ChatCompletionRequest request);

    ChatCompletionResponse createChatCompletion(ChatCompletionRequest request);
}
