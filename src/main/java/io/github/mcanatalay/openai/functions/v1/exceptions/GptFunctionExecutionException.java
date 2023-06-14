package io.github.mcanatalay.openai.functions.v1.exceptions;

import com.networknt.schema.ValidationMessage;

import java.util.Set;
import java.util.stream.Collectors;

public class GptFunctionExecutionException extends Exception {
    private GptFunctionExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public static GptFunctionExecutionException invalidInput(String functionName, Set<ValidationMessage> validationMessages) {
        return new GptFunctionExecutionException(String.format(
                "GPT Function: %s cannot be called with invalid input. Validation Message: %s",
                functionName,
                validationMessages.stream()
                        .map(ValidationMessage::toString)
                        .collect(Collectors.joining("\n"))),
                null
        );
    }

    public static GptFunctionExecutionException invalidInput(String functionName, Throwable cause) {
        return new GptFunctionExecutionException(String.format(
                "GPT Function: %s had error during calling method.",
                functionName),
                cause
        );
    }

    public static GptFunctionExecutionException invalidCall(String functionName, Throwable cause) {
        return new GptFunctionExecutionException(String.format(
                "GPT Function: %s had error during calling method.",
                functionName),
                cause
        );
    }

    public static GptFunctionExecutionException invalidResult(String functionName, Throwable cause) {
        return new GptFunctionExecutionException(String.format(
                "GPT Function: %s had error transforming return object to json.",
                functionName),
                cause
        );
    }
}
