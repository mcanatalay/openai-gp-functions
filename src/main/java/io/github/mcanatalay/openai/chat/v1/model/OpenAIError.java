package io.github.mcanatalay.openai.chat.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class OpenAIError {
    @JsonProperty("error")
    private OpenAiErrorDetails error;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Jacksonized
    public static class OpenAiErrorDetails {
        @JsonProperty("message")
        private String message;

        @JsonProperty("type")
        private String type;

        @JsonProperty("param")
        private String param;

        @JsonProperty("code")
        private String code;
    }
}