package com.gigachatmvc.parameterhandlers;

@FunctionalInterface
public interface ParameterMethodHandler<T> {
    boolean doHandler(T parameter);
}
