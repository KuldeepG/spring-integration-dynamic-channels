package com.github.kuldeepg.springintegration.configuration;

import com.github.kuldeepg.springintegration.annotation.EventHandler;
import com.github.kuldeepg.springintegration.channel.TransactionalChannel;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.annotation.ServiceActivatorAnnotationPostProcessor;
import org.springframework.messaging.MessageHandler;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Method;
import java.util.List;

import static java.util.Arrays.asList;

public class DynamicChannelFactoryBean extends AbstractFactoryBean<TransactionalChannel> {
  private final List<Method> methods;

  public DynamicChannelFactoryBean(List<Method> methods) {
    this.methods = methods;
  }

  @Override
  public Class<?> getObjectType() {
    return TransactionalChannel.class;
  }

  @Override
  protected TransactionalChannel createInstance() {
    ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) getBeanFactory();

    TransactionTemplate transactionTemplate = beanFactory.getBean(TransactionTemplate.class);
    TransactionalChannel channel = new TransactionalChannel(transactionTemplate);

    for(Method method : methods) {
      Object listenerContainer = beanFactory.getBean(method.getDeclaringClass());

      EventHandler annotation = method.getAnnotation(EventHandler.class);
      MessageHandler handler = (MessageHandler) new ServiceActivatorAnnotationPostProcessor(beanFactory)
          .postProcess(listenerContainer,
              listenerContainer.getClass().getName(),
              method,
              asList(annotation));

      channel.subscribe(handler);
    }

    return channel;
  }
}
