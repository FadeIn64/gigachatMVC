package com.gigachatmvc.parameterhandlers.definitions;

import java.util.List;

public interface ParameterMethodHandlersDefinitionChain {

    List<? extends ParameterMethodHandlersDefinition> getParameterMethodHandlersDefinitions();

}
