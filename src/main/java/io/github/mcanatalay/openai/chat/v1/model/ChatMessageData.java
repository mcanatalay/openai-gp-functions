package io.github.mcanatalay.openai.chat.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.mcanatalay.openai.chat.v1.enums.ChatCompletionRole;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChatMessageData {
    @NonNull
    @JsonProperty("role")
    private ChatCompletionRole role;

    @JsonProperty("content")
    private String content;

    @JsonProperty("name")
    private String name;

    @JsonProperty("function_call")
    private FunctionCall functionCall;

    @Data
    @Jacksonized
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class FunctionCall {
        private String name;
        private String arguments;
    }
}
