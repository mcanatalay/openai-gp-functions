package io.github.mcanatalay.openai.autoconfigure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.github.mcanatalay.openai.chat.v1.ChatCompletionService;
import io.github.mcanatalay.openai.chat.v1.DefaultChatCompletionService;
import io.github.mcanatalay.openai.functions.GptFunctionsService;
import io.github.mcanatalay.openai.functions.model.BaseGptFunctionsDeclaration;
import io.github.mcanatalay.openai.functions.v1.GptFunctionsServiceV1;
import io.github.mcanatalay.openai.functions.v1.model.GptFunctionsDeclaration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.util.Set;

@AutoConfiguration
@Slf4j
@ConditionalOnResource(resources = { "classpath:gpt-functions.schema.json", "classpath:gpt-functions.schema.json" })
public class GptFunctionsAutoconfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ChatCompletionService defaultChatCompletionService() {
        return new DefaultChatCompletionService();
    }

    @Bean
    @ConditionalOnMissingBean
    public GptFunctionsService defaultGptFunctionsService(ApplicationContext context) {
        BaseGptFunctionsDeclaration declaration = null;
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonSchema schema = JsonSchemaFactory
                    .getInstance(SpecVersion.VersionFlag.V7)
                    .getSchema(ResourceUtils.getURL("classpath:gpt-functions.schema.json").toURI());

            JsonNode declarationNode = mapper.readTree(ResourceUtils.getFile("classpath:gpt-functions.json"));
            Set<ValidationMessage> validationMessages = schema.validate(declarationNode);
            if (validationMessages.isEmpty()) {
                declaration = new ObjectMapper().treeToValue(
                        declarationNode,
                        BaseGptFunctionsDeclaration.class
                );
            } else {
                log.debug("GPT Function Declarations are invalid. GPT Functions will not be available. Validation Messages: {}", validationMessages);
            }
        } catch (Exception e) {
            log.debug("No GPT Function Declarations found. GPT Functions will not be available.");
        }

        if (declaration instanceof GptFunctionsDeclaration versionedDeclaration) {
            return new GptFunctionsServiceV1(versionedDeclaration, context);
        }

        return null;
    }
}
