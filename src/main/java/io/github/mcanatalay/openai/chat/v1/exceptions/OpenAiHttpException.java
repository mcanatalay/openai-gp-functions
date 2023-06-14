package io.github.mcanatalay.openai.chat.v1.exceptions;

import io.github.mcanatalay.openai.chat.v1.model.OpenAIError;
import org.springframework.web.client.RestClientException;

public class OpenAiHttpException extends RestClientException {
    public final int statusCode;
    public final String code;
    public final String param;
    public final String type;

    public OpenAiHttpException(OpenAIError error, Exception parent, int statusCode) {
        super(error.getError().getMessage(), parent);
        this.statusCode = statusCode;
        this.code = error.getError().getCode();
        this.param = error.getError().getParam();
        this.type = error.getError().getType();
    }

    public static OpenAIError toError(OpenAiHttpException exception) {
        return OpenAIError.builder()
                .error(OpenAIError.OpenAiErrorDetails.builder()
                        .code(exception.code)
                        .message(exception.getMessage())
                        .param(exception.param)
                        .type(exception.type)
                        .build())
                .build();
    }
}