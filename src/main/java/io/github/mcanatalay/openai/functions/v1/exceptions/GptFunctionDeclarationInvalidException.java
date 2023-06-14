package io.github.mcanatalay.openai.functions.v1.exceptions;

public class GptFunctionDeclarationInvalidException extends RuntimeException {
    private GptFunctionDeclarationInvalidException(String message) {
        super(message);
    }

    public static GptFunctionDeclarationInvalidException classNotFound(String functionName, String className) {
        return new GptFunctionDeclarationInvalidException(String.format(
                "GPT Function: %s has invalid value in Class: %s. Class is not defined. Please check your gpt-functions declarations.",
                functionName,
                className)
        );
    }

    public static GptFunctionDeclarationInvalidException methodNotFound(String functionName, String className, String methodName) {
        return new GptFunctionDeclarationInvalidException(String.format(
                "GPT Function: %s has invalid value in Class: %s, Method: %s. Method is not defined. Please check your gpt-functions declarations.",
                functionName,
                className,
                methodName)
        );
    }

    public static GptFunctionDeclarationInvalidException invalidSchema(String functionName, String fieldName) {
        return new GptFunctionDeclarationInvalidException(String.format(
                "GPT Function: %s has invalid value in %s. Schema is invalid. Please check your gpt-functions declarations.",
                functionName,
                fieldName)
        );
    }
}
