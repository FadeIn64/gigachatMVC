package com.gigachatmvc.parameterhandlers.definitions;

import lombok.Getter;
import lombok.ToString;
import com.gigachatmvc.parameterhandlers.ParameterMethodHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
@ToString
public class ParameterDefinitionImpl implements ParameterDefinition{

    List<Class<? extends Annotation>> methodAnnotations;
    List<Class<? extends Annotation>> parameterAnnotations;
    Class<?> parameterType;
    List<ParameterMethodHandler<?>> handlers;

    private static List<Class<? extends Annotation>> getAnnotations(List<Annotation> annotations){
        List<Class<? extends Annotation>> res = new ArrayList<>();
        for (Annotation a : annotations)
            res.add(a.annotationType());
        return res;
    }

    public ParameterDefinitionImpl(Method method, Parameter parameter){

        methodAnnotations = getAnnotations(List.of(method.getAnnotations()));
        parameterAnnotations = getAnnotations(List.of(parameter.getAnnotations()));

        parameterType = parameter.getType();
    }

    @Override
    public void mergeMethods(ParameterMethodHandlersDefinition parameterMethodHandlersDefinition) {

        if (this.handlers == null)
            this.handlers = new ArrayList<>();

        if (parameterType == parameterMethodHandlersDefinition.getParameterType()
                && (methodAnnotations.contains(parameterMethodHandlersDefinition.getMethodAnnotation())
                || parameterAnnotations.contains(parameterMethodHandlersDefinition.getParameterAnnotation()))){
            this.handlers.addAll(parameterMethodHandlersDefinition.getHandlers());
        }

    }

    @Override
    public List<? extends ParameterMethodHandler<?>> getHandlers() {
        return handlers;
    }
}
