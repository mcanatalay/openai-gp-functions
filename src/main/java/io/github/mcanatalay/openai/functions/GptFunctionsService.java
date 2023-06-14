package io.github.mcanatalay.openai.functions;

import io.github.mcanatalay.openai.chat.v1.model.ChatCompletionRequest;
import io.github.mcanatalay.openai.chat.v1.model.ChatCompletionResponse;

import java.util.Set;

public interface GptFunctionsService {
    ChatCompletionResponse createChatCompletion(String authToken, ChatCompletionRequest request, Set<String> namespaces);
    ChatCompletionResponse createChatCompletion(String authToken, ChatCompletionRequest request);
}
