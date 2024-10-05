package com.vaynerakawalo.marketdataingestionservice.shared.client;

import com.vaynerakawalo.marketdataingestionservice.market.domain.MarketOrder;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface AlbionRmtBackendClient {

  @PostExchange(value = "/v1/marketdata", contentType = MediaType.APPLICATION_JSON_VALUE)
  void postOrders(@RequestBody List<MarketOrder> orders);
}
