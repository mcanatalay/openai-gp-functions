package io.github.mcanatalay.openai.chat.v1.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ChatCompletionRole {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),
    FUNCTION("function");

    private final String value;

    ChatCompletionRole(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
