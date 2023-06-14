package io.github.mcanatalay.openai.chat.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

@Data
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChatCompletionRequest {
    @NonNull
    @JsonProperty("model")
    private String model;

    @NonNull
    @JsonProperty("messages")
    private List<ChatMessageData> messages;

    @JsonProperty("functions")
    private List<ChatCompletionFunction> functions;

    @JsonProperty("function_call")
    private String functionCall;

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("top_p")
    private Double topP;

    @JsonProperty("n")
    private Integer n;

    @JsonProperty("stop")
    private List<String> stop;

    @JsonProperty("max_tokens")
    private Integer maxTokens;

    @JsonProperty("presence_penalty")
    private Double presencePenalty;

    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;

    @JsonProperty("logit_bias")
    private Map<String, Integer> logitBias;

    @JsonProperty("user")
    private String user;
}
