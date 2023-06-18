package com.gigachatmvc.parameterhandlers.congigurer;

import java.lang.annotation.Annotation;

public interface ControllerParameterHandlersChain {

    <T extends Annotation> AnnotatedMethodConfigurer<T> annotatedMethods(Class<T> annotation);

    ControllerParameterHandlersChain and();

}
