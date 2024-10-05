package com.vaynerakawalo.marketdataingestionservice.market.infra;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaynerakawalo.marketdataingestionservice.market.domain.MarketOrder;
import com.vaynerakawalo.marketdataingestionservice.market.dto.MarketOrdersMessage;
import com.vaynerakawalo.marketdataingestionservice.shared.provider.AlbionRmtBackendProvider;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MarketOrderConsumerTest {

  private MarketOrderConsumer consumer;

  @Mock private AlbionRmtBackendProvider provider;
  @Mock private Connection connection;
  @Mock private Dispatcher dispatcher;

  @BeforeEach
  void setUp() {
    when(connection.createDispatcher(any())).thenReturn(dispatcher);
    consumer = new MarketOrderConsumer(new ObjectMapper(), connection, "TEST_TOPIC", provider);
  }

  @Test
  void handleMessage_happyPath_callsProvider(@Mock MarketOrder marketOrder) {
    var message = new MarketOrdersMessage(List.of(marketOrder));

    consumer.handleMessage(message);

    verify(provider).createOrders(message.orders());
  }
}
