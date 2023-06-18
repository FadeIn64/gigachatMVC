package com.gigachatmvc.parameterhandlers.definitions;

import com.gigachatmvc.parameterhandlers.ParameterMethodHandler;

import java.util.List;

public interface ParameterDefinition{

    void mergeMethods(ParameterMethodHandlersDefinition parameterMethodHandlersDefinition);

    List<? extends ParameterMethodHandler> getHandlers();

}
