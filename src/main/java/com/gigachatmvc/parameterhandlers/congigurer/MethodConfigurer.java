package com.gigachatmvc.parameterhandlers.congigurer;

public interface MethodConfigurer {

    <E> ParameterMethodHandlersConfigurer<E> parameterType(Class<E> parameterType);

    ControllerParameterHandlersChain and();

}
