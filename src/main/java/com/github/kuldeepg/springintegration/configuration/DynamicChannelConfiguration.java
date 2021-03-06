package com.github.kuldeepg.springintegration.configuration;

import com.github.kuldeepg.springintegration.annotation.EventHandler;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;

@Configuration
public class DynamicChannelConfiguration implements BeanFactoryPostProcessor {

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
    Set<BeanDefinition> candidateComponents = provider.findCandidateComponents("com.github.kuldeepg");

    MethodAnnotationsScanner methodAnnotationsScanner = new MethodAnnotationsScanner();
    candidateComponents.stream()
        .flatMap(candidateComponent -> new Reflections(candidateComponent.getBeanClassName(), methodAnnotationsScanner)
            .getMethodsAnnotatedWith(EventHandler.class).stream())
        .collect(groupingBy(method -> method.getParameterTypes()[0].getName()))
        .forEach((paramName, listeners) -> registerChannel((DefaultListableBeanFactory) beanFactory, paramName, listeners));

    }

  private void registerChannel(DefaultListableBeanFactory beanFactory, String paramName, List<Method> listeners) {
    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    beanDefinition.setBeanClass(DynamicChannelFactoryBean.class);
    ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
    argumentValues.addGenericArgumentValue(listeners);
    beanDefinition.setConstructorArgumentValues(argumentValues);

    beanFactory.registerBeanDefinition(paramName + ".channel", beanDefinition);
  }
}