package io.github.mcanatalay.openai.functions.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Data
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GptFunctionDeclaration {
    @JsonProperty("class")
    private String className;
    @JsonProperty("method")
    private String methodName;
    private String description;
    private Set<String> namespaces;
    private JsonNode parameters;
}
