package com.vaynerakawalo.marketdataingestionservice.shared.provider;

import com.vaynerakawalo.marketdataingestionservice.market.domain.MarketOrder;
import com.vaynerakawalo.marketdataingestionservice.shared.client.AlbionRmtBackendClient;
import java.util.List;
import java.util.Set;
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
      log.info("Successfully ingested orders into Albion RMT Backend");
      client.postOrders(orders);
    } catch (HttpClientErrorException.UnprocessableEntity ex) {
      var body = ex.getResponseBodyAs(FailedRecordIngestion.class);
      log.warn("The ingestion of some orders was unsuccessful {}", body.Failed());
    } catch (HttpStatusCodeException ex) {
      log.error(
          "Ingestion failed with status code {}, response {}",
          ex.getStatusCode(),
          ex.getResponseBodyAsString());
    } catch (Exception ex) {
      log.error("Ingestion failed with error {}", ex.getMessage());
    }
  }
}

record FailedRecordIngestion(Set<String> Failed) {}
