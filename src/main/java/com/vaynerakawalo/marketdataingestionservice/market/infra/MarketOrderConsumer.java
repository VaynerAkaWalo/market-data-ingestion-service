package com.vaynerakawalo.marketdataingestionservice.market.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaynerakawalo.marketdataingestionservice.market.dto.MarketOrdersMessage;
import com.vaynerakawalo.marketdataingestionservice.shared.nats.MessageConsumer;
import com.vaynerakawalo.marketdataingestionservice.shared.provider.AlbionRmtBackendProvider;
import io.nats.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("nats")
public class MarketOrderConsumer extends MessageConsumer<MarketOrdersMessage> {

  private final AlbionRmtBackendProvider albionRmtBackendProvider;

  protected MarketOrderConsumer(
      ObjectMapper objectMapper,
      Connection connection,
      @Value("${topic.marketorders}") String topic,
      AlbionRmtBackendProvider albionRmtBackendProvider) {
    super(objectMapper, connection, topic, MarketOrdersMessage.class);
    this.albionRmtBackendProvider = albionRmtBackendProvider;
  }

  @Override
  public void handleMessage(MarketOrdersMessage entity) {
    log.info("Ingested orders: {}", entity);

    albionRmtBackendProvider.createOrders(entity.orders());
  }
}
