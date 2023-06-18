package com.gigachatmvc.parameterhandlers.proxer;

import com.gigachatmvc.parameterhandlers.ParameterMethodHandler;
import com.gigachatmvc.parameterhandlers.definitions.ParameterDefinition;
import com.gigachatmvc.parameterhandlers.definitions.ParameterDefinitionImpl;
import com.gigachatmvc.parameterhandlers.definitions.ParameterMethodHandlersDefinitionChain;
import com.gigachatmvc.parameterhandlers.exceptions.HandlerReturnFalseStatus;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodProxer {

    private static final Map<Method, MethodProxer> methodProxerMap;

    static {
        methodProxerMap = new HashMap<>();
    }

    ParameterMethodHandlersDefinitionChain parameterMethodHandlersDefinitionChain;
    private Map<ParameterDefinition, Object> parameterDefinitionMap = new HashMap<>();
    public static MethodProxer newInstance(Method method, Object[] args, ParameterMethodHandlersDefinitionChain parameterMethodHandlersDefinitionChain) {

        var res = methodProxerMap.get(method);
        if (res == null){
            res = new MethodProxer(parameterMethodHandlersDefinitionChain, method, method.getParameters(), args);
        }
        return res;
    }

    private MethodProxer(ParameterMethodHandlersDefinitionChain parameterMethodHandlersDefinitionChain,Method method, Parameter[] parameters, Object[] args){
        this.parameterMethodHandlersDefinitionChain = parameterMethodHandlersDefinitionChain;
        var _parameters = List.of(parameters);
        var _args = List.of(args);
        for (int i = 0; i < _parameters.size(); i++)
            parameterDefinitionMap.put(new ParameterDefinitionImpl(method, _parameters.get(i)), _args.get(i));
    }

    public void invoke(){
        var keys = parameterDefinitionMap.keySet();
        for (ParameterDefinition parameterDefinition : keys){

            if (parameterDefinition.getHandlers() == null)
                parameterMethodHandlersDefinitionChain.getParameterMethodHandlersDefinitions()
                        .forEach(parameterDefinition::mergeMethods);

            for (ParameterMethodHandler<Object> parameterMethodHandler : parameterDefinition.getHandlers()){
                if (!parameterMethodHandler.doHandler(parameterDefinitionMap.get(parameterDefinition)))
                    throw new HandlerReturnFalseStatus();
            }
        }
    }

}
