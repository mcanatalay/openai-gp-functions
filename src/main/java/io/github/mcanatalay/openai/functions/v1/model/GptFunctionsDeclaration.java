package io.github.mcanatalay.openai.functions.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.mcanatalay.openai.functions.model.BaseGptFunctionsDeclaration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Data
@Jacksonized
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "version",
        visible = true,
        defaultImpl = GptFunctionsDeclaration.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GptFunctionsDeclaration.class, name = "1.0.0"),
})
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GptFunctionsDeclaration extends BaseGptFunctionsDeclaration {
    private Map<String, GptFunctionDeclaration> functions;
}
