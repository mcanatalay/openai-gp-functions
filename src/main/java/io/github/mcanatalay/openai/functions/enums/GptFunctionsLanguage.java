package io.github.mcanatalay.openai.functions.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.mcanatalay.openai.functions.v1.exceptions.GptFunctionsDeclarationInvalidException;

public enum GptFunctionsLanguage {
    JAVA("JAVA");

    private final String value;

    GptFunctionsLanguage(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static GptFunctionsLanguage fromValue(String value) {
        for (GptFunctionsLanguage language : GptFunctionsLanguage.values()) {
            if (language.getValue().equals(value)) {
                return language;
            }
        }
        throw GptFunctionsDeclarationInvalidException.unknownLanguage(value);
    }
}
