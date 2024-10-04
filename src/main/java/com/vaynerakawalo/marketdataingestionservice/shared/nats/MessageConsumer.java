package com.vaynerakawalo.marketdataingestionservice.shared.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MessageConsumer<T> implements MessageHandler {

  private final ObjectMapper objectMapper;
  private final Class<T> clazz;

  protected MessageConsumer(
      ObjectMapper objectMapper, Connection connection, String topic, Class<T> clazz) {
    this.objectMapper = objectMapper;
    this.clazz = clazz;
    connection.createDispatcher(this).subscribe(topic);
  }

  @Override
  public void onMessage(Message message) {
    T entity;
    try {
      entity = objectMapper.readValue(message.getData(), clazz);
    } catch (Exception ex) {
      log.error(
          "Could not serialize message of type {}, message: {}",
          clazz.getSimpleName(),
          messageToString(message),
          ex);
      return;
    }

    try {
      handleMessage(entity);
    } catch (Exception ex) {
      log.error(
          "Error while processing message of type {}, message {}",
          clazz.getSimpleName(),
          messageToString(message),
          ex);
    }
  }

  private String messageToString(Message message) {
    return new String(message.getData(), StandardCharsets.UTF_8);
  }

  public abstract void handleMessage(T entity);
}
