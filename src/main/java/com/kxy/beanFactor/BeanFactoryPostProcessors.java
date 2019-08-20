package com.kxy.beanFactor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Auther: kxy
 * @Date: 2019/7/10 23:16
 * @Desraption
 **/
@Component
public class BeanFactoryPostProcessors implements
        BeanFactoryPostProcessor{

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
