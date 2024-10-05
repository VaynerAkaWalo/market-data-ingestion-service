package com.vaynerakawalo.marketdataingestionservice.shared.provider;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.vaynerakawalo.marketdataingestionservice.market.domain.MarketOrder;
import com.vaynerakawalo.marketdataingestionservice.shared.client.AlbionRmtBackendClient;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@ExtendWith(MockitoExtension.class)
class AlbionRmtBackendProviderTest {

  @InjectMocks private AlbionRmtBackendProvider provider;

  @Mock private AlbionRmtBackendClient client;

  @Test
  void postOrders_callsClient(@Mock List<MarketOrder> orders) {
    provider.createOrders(orders);

    verify(client).postOrders(orders);
  }

  @Test
  void postOrders_clientThrowsException_doesNotThrowException() {
    doThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "", null, new byte[] {}, null))
        .when(client)
        .postOrders(anyList());

    provider.createOrders(List.of());
  }
}
