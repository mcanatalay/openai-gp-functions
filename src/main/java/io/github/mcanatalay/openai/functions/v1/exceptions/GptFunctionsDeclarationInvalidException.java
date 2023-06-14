package io.github.mcanatalay.openai.functions.v1.exceptions;

import io.github.mcanatalay.openai.functions.enums.GptFunctionsLanguage;
import io.github.mcanatalay.openai.functions.enums.GptFunctionsVersion;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GptFunctionsDeclarationInvalidException extends RuntimeException {
    private GptFunctionsDeclarationInvalidException(String message) {
        super(message);
    }

    public static GptFunctionsDeclarationInvalidException unknownVersion(String version) {
        return new GptFunctionsDeclarationInvalidException(String.format(
                "Version: %s. Supported Versions: %s. Please check your gpt-functions declarations.",
                version,
                Stream.of(GptFunctionsVersion.values()).map(GptFunctionsVersion::getValue).collect(Collectors.joining(",")))
        );
    }

    public static GptFunctionsDeclarationInvalidException unknownLanguage(String language) {
        return new GptFunctionsDeclarationInvalidException(String.format(
                "Language: %s. Supported Languages: %s. Please check your gpt-functions declarations.",
                language,
                Stream.of(GptFunctionsLanguage.values()).map(GptFunctionsLanguage::getValue).collect(Collectors.joining(",")))
        );
    }
}
