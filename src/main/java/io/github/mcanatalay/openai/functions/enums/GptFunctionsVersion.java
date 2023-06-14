package io.github.mcanatalay.openai.functions.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.mcanatalay.openai.functions.v1.exceptions.GptFunctionsDeclarationInvalidException;

public enum GptFunctionsVersion {
    FIRST("1.0.0");

    private final String value;

    GptFunctionsVersion(String version) {
        this.value = version;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static GptFunctionsVersion fromValue(String value) {
        for (GptFunctionsVersion version : GptFunctionsVersion.values()) {
            if (version.getValue().equals(value)) {
                return version;
            }
        }
        throw GptFunctionsDeclarationInvalidException.unknownVersion(value);
    }
}
