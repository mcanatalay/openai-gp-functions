package io.github.mcanatalay.openai.chat.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class ChatCompletionResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created")
    private Long created;

    @JsonProperty("model")
    private String model;

    @JsonProperty("choices")
    private List<ChoiceData> choices;

    @JsonProperty("usage")
    private UsageData usage;

    @Data
    @Builder
    @Jacksonized
    public static class ChoiceData {
        @JsonProperty("index")
        private Integer index;

        @JsonProperty("message")
        private ChatMessageData message;

        @JsonProperty("finish_reason")
        private String finishReason;
    }
}
