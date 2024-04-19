package com.sublinks.sublinksapi.federation.listeners;

import com.sublinks.sublinksapi.queue.services.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@ConditionalOnBean(RabbitTemplate.class)
@RequiredArgsConstructor
public class FederationListener {

  @Value("${sublinks.federation.exchange}")
  public String federationExchange;
  public Producer federationProducer;
}