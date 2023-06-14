package io.github.mcanatalay.openai.functions.v1.exceptions;

public class GptFunctionImplementationInvalidException extends RuntimeException {
    private GptFunctionImplementationInvalidException(String message) {
        super(message);
    }

    public static GptFunctionImplementationInvalidException cannotReachBean(String className, String methodName) {
        return new GptFunctionImplementationInvalidException(String.format(
                "Cannot reach bean, method is not static. Class: %s, Method: %s",
                className,
                methodName)
        );
    }

    public static GptFunctionImplementationInvalidException noParameters(String className, String methodName) {
        return new GptFunctionImplementationInvalidException(String.format(
                "Methods with no variables are not supported. Class: %s, Method: %s",
                className,
                methodName)
        );
    }

    public static GptFunctionImplementationInvalidException multipleParameters(String className, String methodName) {
        return new GptFunctionImplementationInvalidException(String.format(
                "Methods with multiple variables are not supported. Class: %s, Method: %s",
                className,
                methodName)
        );
    }

    public static GptFunctionImplementationInvalidException primitiveParameter(String className, String methodName) {
        return new GptFunctionImplementationInvalidException(String.format(
                "Methods with primitive variables are not supported. Class: %s, Method: %s",
                className,
                methodName)
        );
    }
}
