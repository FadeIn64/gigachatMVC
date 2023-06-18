package com.gigachatmvc.parameterhandlers.congigurer;

import org.springframework.stereotype.Component;
import com.gigachatmvc.parameterhandlers.ParameterMethodHandler;
import com.gigachatmvc.parameterhandlers.definitions.ParameterMethodHandlersDefinitionChain;
import com.gigachatmvc.parameterhandlers.exceptions.TypeIsNotAnnotationException;
import com.gigachatmvc.parameterhandlers.definitions.AnnotatedMethodParameterMethodHandlersDefinition;
import com.gigachatmvc.parameterhandlers.definitions.ParameterMethodHandlersDefinition;

import java.lang.annotation.Annotation;
import java.util.*;
@Component
public class ControllerParameterHandlersChainImpl implements ControllerParameterHandlersChain, ParameterMethodHandlersDefinitionChain {

    private final Map<Class<? extends Annotation>, AnnotatedMethodConfigurerImpl<?>> annotatedMethodConfigurerMap;

    public ControllerParameterHandlersChainImpl() {
        annotatedMethodConfigurerMap = new HashMap<>();
    }

    @Override
    public <T extends Annotation> AnnotatedMethodConfigurer<T> annotatedMethods(Class<T> annotation) {
        AnnotatedMethodConfigurerImpl<?> res = annotatedMethodConfigurerMap.get(annotation);
        if (res == null){
            res = new AnnotatedMethodConfigurerImpl<T>(annotation);
            annotatedMethodConfigurerMap.put(annotation, res);
        }
        return (AnnotatedMethodConfigurer<T>) res;
    }

    @Override
    public ControllerParameterHandlersChain and() {
        return this;
    }

    @Override
    public List<? extends ParameterMethodHandlersDefinition> getParameterMethodHandlersDefinitions() {
        List<ParameterMethodHandlersDefinition> res = new ArrayList<>();

        var keys = annotatedMethodConfigurerMap.keySet();
        keys.forEach(x-> {
            res.addAll(annotatedMethodConfigurerMap.get(x).getAnnotatedMethodParameterMethodHandlersDefinition());
        });

        return res;
    }

    public class AnnotatedMethodConfigurerImpl<T extends Annotation> implements AnnotatedMethodConfigurer<T>{
        Class<T> annotation;
        private final Map<Class<?>, ParameterMethodHandlersConfigurerImpl<?>> parameterMethodHandlersConfigurerMap;

        public AnnotatedMethodConfigurerImpl(Class<T> annotation) {
            if (!annotation.isAnnotation())
                throw new TypeIsNotAnnotationException();
            this.annotation = annotation;
            parameterMethodHandlersConfigurerMap = new HashMap<>();
        }

        @Override
        public <E> ParameterMethodHandlersConfigurer<E> parameterType(Class<E> parameterType) {
            ParameterMethodHandlersConfigurer<?> parameterMethodHandlersConfigurer = parameterMethodHandlersConfigurerMap.get(parameterType);
            if (parameterMethodHandlersConfigurer == null){
                var res = new ParameterMethodHandlersConfigurerImpl<E>(parameterType);
                parameterMethodHandlersConfigurerMap.put(parameterType, res);
                return res;
            }
            return (ParameterMethodHandlersConfigurer<E>) parameterMethodHandlersConfigurer;
        }

        private Map<Class<?>, List<? extends ParameterMethodHandler<?>>> getHandlers(){
            Set<Class<?>> classes = parameterMethodHandlersConfigurerMap.keySet();

            Map<Class<?>, List< ? extends ParameterMethodHandler<?>>> res = new HashMap<>();

            //Достаем из каждого обработчика методы и добавляем тип параметра и его методы
            classes.forEach(x->res.put(x, parameterMethodHandlersConfigurerMap.get(x).methods));

            return res;
        }
        private List<? extends ParameterMethodHandlersDefinition> getAnnotatedMethodParameterMethodHandlersDefinition(){

            List<AnnotatedMethodParameterMethodHandlersDefinition> res = new ArrayList<>();

            Map<Class<?>, List<? extends ParameterMethodHandler<?>>> handlers = this.getHandlers();
            Set<Class<?>> keys = handlers.keySet();
            keys.forEach(x->res.add(new AnnotatedMethodParameterMethodHandlersDefinition(this.annotation, x, handlers.get(x))));

            return res;
        }

        @Override
        public ControllerParameterHandlersChain and() {
            return ControllerParameterHandlersChainImpl.this;
        }

        public class ParameterMethodHandlersConfigurerImpl<E> implements ParameterMethodHandlersConfigurer<E>{

            Class<E> parameterType;
            private final List<ParameterMethodHandler<E>> methods;

            public ParameterMethodHandlersConfigurerImpl(Class<E> parameterType) {
                this.parameterType = parameterType;
                methods = new ArrayList<>();
            }

            @Override
            public ParameterMethodHandlersConfigurer<E> addHandler(ParameterMethodHandler<E> handler) {
                methods.add(handler);
                return this;
            }

            @Override
            public MethodConfigurer and() {
                return AnnotatedMethodConfigurerImpl.this;
            }

        }


    }

}
