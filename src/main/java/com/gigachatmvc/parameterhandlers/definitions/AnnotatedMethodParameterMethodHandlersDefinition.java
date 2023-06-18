package com.gigachatmvc.parameterhandlers.definitions;

import lombok.ToString;
import com.gigachatmvc.parameterhandlers.ParameterMethodHandler;

import java.lang.annotation.Annotation;
import java.util.List;

@ToString
public class AnnotatedMethodParameterMethodHandlersDefinition implements ParameterMethodHandlersDefinition{

    private Class<? extends Annotation> annotation;
    private Class<?> parameterType;
    private List<? extends ParameterMethodHandler<?>> parameterMethodHandlers;

    public AnnotatedMethodParameterMethodHandlersDefinition(Class<? extends Annotation> annotation, Class<?> parameterType, List<? extends ParameterMethodHandler<?>> parameterMethodHandlers) {
        this.annotation = annotation;
        this.parameterType = parameterType;
        this.parameterMethodHandlers = parameterMethodHandlers;
    }

    @Override
    public Class<? extends Annotation> getMethodAnnotation() {
        return annotation;
    }

    @Override
    public Class<? extends Annotation> getParameterAnnotation() {
        return null;
    }

    @Override
    public Class<?> getParameterType() {
        return parameterType;
    }

    @Override
    public List<? extends ParameterMethodHandler<?>> getHandlers() {
        return parameterMethodHandlers;
    }
}
