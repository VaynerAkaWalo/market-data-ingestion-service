package com.vaynerakawalo.marketdataingestionservice.shared.provider;

import com.vaynerakawalo.marketdataingestionservice.market.domain.MarketOrder;
import com.vaynerakawalo.marketdataingestionservice.shared.client.AlbionRmtBackendClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlbionRmtBackendProvider {

  private final AlbionRmtBackendClient client;

  public void createOrders(List<MarketOrder> orders) {
    try {
      client.postOrders(orders);
      log.info("Successfully posted orders");
    } catch (HttpClientErrorException.UnprocessableEntity ex) {
      log.warn("Api responded with 429 status");
    } catch (HttpStatusCodeException ex) {
      log.error("Api responded with {} status, message {}", ex.getStatusCode(), ex.getMessage());
    } catch (Exception ex) {
      log.error("Unknown error occurred while sending order to api message: {}", ex.getMessage());
    }
  }
}
