package io.github.mcanatalay.openai.functions.v1;

import io.github.mcanatalay.openai.chat.v1.ChatCompletionService;
import io.github.mcanatalay.openai.chat.v1.enums.ChatCompletionRole;
import io.github.mcanatalay.openai.chat.v1.model.ChatCompletionFunction;
import io.github.mcanatalay.openai.chat.v1.model.ChatCompletionRequest;
import io.github.mcanatalay.openai.chat.v1.model.ChatCompletionResponse;
import io.github.mcanatalay.openai.chat.v1.model.ChatMessageData;
import io.github.mcanatalay.openai.functions.GptFunctionsService;
import io.github.mcanatalay.openai.functions.v1.exceptions.GptFunctionExecutionException;
import io.github.mcanatalay.openai.functions.v1.model.GptFunctionDeclaration;
import io.github.mcanatalay.openai.functions.v1.model.GptFunctionsDeclaration;
import io.github.mcanatalay.openai.functions.v1.proxy.GptFunctionProxy;
import org.springframework.context.ApplicationContext;

import java.util.*;

public class GptFunctionsServiceV1 implements GptFunctionsService {
    private final ChatCompletionService chatCompletionService;
    private final Map<GptFunctionDeclaration, ChatCompletionFunction> functions;
    private final Map<String, GptFunctionProxy> proxies;

    public GptFunctionsServiceV1(GptFunctionsDeclaration functionsDeclaration, ApplicationContext context) {
        this.chatCompletionService = context.getBean(ChatCompletionService.class);

        this.proxies = new HashMap<>();
        this.functions = new HashMap<>();
        functionsDeclaration.getFunctions().forEach((functionName, declaration) -> {
            proxies.put(functionName, new GptFunctionProxy(functionName, declaration, context));
            functions.put(declaration, ChatCompletionFunction.builder()
                    .name(functionName)
                    .description(declaration.getDescription())
                    .parameters(declaration.getParameters())
                    .build());
        });
    }

    public ChatCompletionResponse createChatCompletion(String authToken, ChatCompletionRequest request, Set<String> namespaces) {
        request.setFunctions(getScopedFunctions(namespaces));
        request.setFunctionCall("auto");
        ChatCompletionResponse response = chatCompletionService.createChatCompletion(authToken, request);

        ChatMessageData.FunctionCall functionCall = response.getChoices().get(0).getMessage().getFunctionCall();
        if (functionCall != null) {
            GptFunctionProxy proxy = proxies.get(functionCall.getName());

            String functionResponse = null;
            try {
                functionResponse = proxy.invoke(functionCall.getArguments());
            } catch (GptFunctionExecutionException e) {
                functionResponse = e.getMessage();
            }

            List<ChatMessageData> messages = new ArrayList<>(request.getMessages());
            messages.add(ChatMessageData.builder()
                    .name("Function")
                    .role(ChatCompletionRole.FUNCTION)
                    .content(functionResponse)
                    .build());
            ChatCompletionRequest functionRequest = ChatCompletionRequest.builder()
                    .model(request.getModel())
                    .messages(messages)
                    .build();

            return chatCompletionService.createChatCompletion(authToken, functionRequest);
        }

        return response;
    }

    public ChatCompletionResponse createChatCompletion(String authToken, ChatCompletionRequest request) {
        return createChatCompletion(authToken, request, Set.of("ALL"));
    }

    private List<ChatCompletionFunction> getScopedFunctions(Set<String> namespaces) {
       if (namespaces == null || namespaces.isEmpty()) {
            return Collections.emptyList();
       } else if (namespaces.contains("ALL")) {
           return new ArrayList<>(functions.values());
       }

       return functions.entrySet().stream()
               .filter(entry -> entry.getKey().getNamespaces().stream().anyMatch(namespaces::contains))
               .map(Map.Entry::getValue)
               .toList();
    }
}
