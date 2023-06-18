package com.gigachatmvc.parameterhandlers.definitions;

import com.gigachatmvc.parameterhandlers.ParameterMethodHandler;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ParameterMethodHandlersDefinition {

    Class<? extends Annotation> getMethodAnnotation();
    Class<? extends Annotation> getParameterAnnotation();
    Class<?> getParameterType();

    List<? extends ParameterMethodHandler<?>> getHandlers();

}
