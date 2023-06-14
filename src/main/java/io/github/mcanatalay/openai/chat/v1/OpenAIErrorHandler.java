package io.github.mcanatalay.openai.chat.v1;

import io.github.mcanatalay.openai.chat.v1.exceptions.OpenAiHttpException;
import io.github.mcanatalay.openai.chat.v1.model.OpenAIError;
import lombok.NonNull;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;

import java.io.IOException;

public class OpenAIErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(@NonNull ClientHttpResponse response) throws IOException {
        try {
            super.handleError(response);
        } catch (RestClientResponseException e) {
            OpenAIError error = null;
            try {
                error = e.getResponseBodyAs(OpenAIError.class);
            } catch (Exception ex) {
                throw e;
            }
            if (error == null) {
                throw e;
            }
            throw new OpenAiHttpException(error, e, e.getStatusCode().value());
        }
    }
}
