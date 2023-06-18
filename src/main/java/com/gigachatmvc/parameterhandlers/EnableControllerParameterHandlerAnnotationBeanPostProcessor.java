package com.gigachatmvc.parameterhandlers;

import com.gigachatmvc.parameterhandlers.definitions.ParameterMethodHandlersDefinitionChain;
import com.gigachatmvc.parameterhandlers.exceptions.ClassIsNotControllerException;
import com.gigachatmvc.parameterhandlers.proxer.MethodProxer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class EnableControllerParameterHandlerAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Object> originals;


    {
        originals = new HashMap<>();
    }

    private ParameterMethodHandlersDefinitionChain parameterMethodHandlersDefinitionChain;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        List<Class<?>> interfaces = List.of(bean.getClass().getInterfaces());
        if (interfaces.contains(ParameterMethodHandlersDefinitionChain.class)){
            parameterMethodHandlersDefinitionChain = (ParameterMethodHandlersDefinitionChain) bean;
        }

        if (bean.getClass().isAnnotationPresent(EnableControllerParameterHandler.class)){
            if (!bean.getClass().isAnnotationPresent(Controller.class))
                throw new ClassIsNotControllerException("Class " + bean.getClass() + " is not Controller");
            originals.put(beanName, bean);
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!originals.containsKey(beanName))
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);

        Object oldBean = originals.get(beanName);
        Class<?> oldClass = oldBean.getClass();

        return Enhancer.create(oldBean.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy1) throws Throwable {

                var res = proxy1.invoke(bean, args);

                MethodProxer methodProxer = MethodProxer.newInstance(method, args, parameterMethodHandlersDefinitionChain);
                methodProxer.invoke();

                return res;
            }
        });
    }
}
