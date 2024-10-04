package com.vaynerakawalo.marketdataingestionservice.gold.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaynerakawalo.marketdataingestionservice.gold.dto.GoldOrdersMessage;
import com.vaynerakawalo.marketdataingestionservice.shared.nats.MessageConsumer;
import io.nats.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("nats")
public class GoldOrderConsumer extends MessageConsumer<GoldOrdersMessage> {

  public GoldOrderConsumer(
      @Value("${topic.goldorders}") String topic,
      Connection connection,
      ObjectMapper objectMapper) {
    super(objectMapper, connection, topic, GoldOrdersMessage.class);
  }

  @Override
  public void handleMessage(GoldOrdersMessage entity) {
    log.info("Received message {}", entity.toString());
  }
}
