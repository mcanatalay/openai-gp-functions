package io.github.mcanatalay.openai.functions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.mcanatalay.openai.functions.enums.GptFunctionsLanguage;
import io.github.mcanatalay.openai.functions.enums.GptFunctionsVersion;
import io.github.mcanatalay.openai.functions.v1.model.GptFunctionsDeclaration;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true,
        property = "version")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GptFunctionsDeclaration.class, name = "1.0.0"),
})
public class BaseGptFunctionsDeclaration {
    private GptFunctionsVersion version;
    private GptFunctionsLanguage language;
}
