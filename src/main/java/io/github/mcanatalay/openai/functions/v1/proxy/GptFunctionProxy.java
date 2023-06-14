package io.github.mcanatalay.openai.functions.v1.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.github.mcanatalay.openai.functions.v1.exceptions.GptFunctionExecutionException;
import io.github.mcanatalay.openai.functions.v1.exceptions.GptFunctionDeclarationInvalidException;
import io.github.mcanatalay.openai.functions.v1.exceptions.GptFunctionImplementationInvalidException;
import io.github.mcanatalay.openai.functions.v1.model.GptFunctionDeclaration;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Set;

public class GptFunctionProxy {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final String functionName;
    private final Method method;
    private final JsonSchema schema;
    private final Object bean;
    private final Class<?> type;

    public GptFunctionProxy(String functionName, GptFunctionDeclaration declaration, ApplicationContext context) {
        this.functionName = functionName;

        Class<?> clazz;
        try {
            clazz = Class.forName(declaration.getClassName());
        } catch (ClassNotFoundException e) {
            throw GptFunctionDeclarationInvalidException.classNotFound(functionName, declaration.getClassName());
        }

        Object classBean = null;
        try {
            classBean = context.getBean(clazz);
        } catch (Exception e) {
            // ignore
        }
        this.bean = classBean;

        Method functionMethod = null;
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(declaration.getMethodName())) {
                functionMethod = m;
                break;
            }
        }
        if (functionMethod == null) {
            throw GptFunctionDeclarationInvalidException.methodNotFound(functionName, declaration.getClassName(), declaration.getMethodName());
        } else if (functionMethod.getParameters().length == 0) {
            throw GptFunctionImplementationInvalidException.noParameters(declaration.getClassName(), declaration.getMethodName());
        } else if (functionMethod.getParameters().length > 1) {
            throw GptFunctionImplementationInvalidException.multipleParameters(declaration.getClassName(), declaration.getMethodName());
        } else if (classBean == null && !Modifier.isStatic(functionMethod.getModifiers())) {
            throw GptFunctionImplementationInvalidException.cannotReachBean(declaration.getClassName(), declaration.getMethodName());
        }

        this.type = functionMethod.getParameterTypes()[0];
        if (type.isPrimitive()) {
            throw GptFunctionImplementationInvalidException.primitiveParameter(declaration.getClassName(), declaration.getMethodName());
        }

        this.method = functionMethod;
        if (declaration.getParameters() != null) {
            try {
                this.schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(declaration.getParameters());
            } catch (Exception e) {
                throw GptFunctionDeclarationInvalidException.invalidSchema(functionName, "parameters");
            }
        } else {
            this.schema = null;
        }
    }

    public String invoke(String arguments) throws GptFunctionExecutionException {
        Object methodParameters = null;
        try {
            JsonNode parameters = MAPPER.treeToValue(MAPPER.readTree(arguments), JsonNode.class);
            Set<ValidationMessage> validationMessages = schema != null
                    ? schema.validate(parameters)
                    : Collections.emptySet();
            if (!validationMessages.isEmpty()) {
                throw GptFunctionExecutionException.invalidInput(functionName, validationMessages);
            }
            methodParameters = MAPPER.treeToValue(parameters, type);
        } catch (JsonProcessingException e) {
            throw GptFunctionExecutionException.invalidInput(functionName, e);
        }

        try {
            Object value = method.invoke(bean, methodParameters);
            return MAPPER.writeValueAsString(value);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw GptFunctionExecutionException.invalidCall(functionName, e);
        } catch (JsonProcessingException e) {
            throw GptFunctionExecutionException.invalidResult(functionName, e);
        }
    }
}
