package com.gigachatmvc.parameterhandlers.congigurer;

import com.gigachatmvc.parameterhandlers.ParameterMethodHandler;

public interface ParameterMethodHandlersConfigurer<T> {
    ParameterMethodHandlersConfigurer<T> addHandler(ParameterMethodHandler<T> handler);
    MethodConfigurer and();
}
