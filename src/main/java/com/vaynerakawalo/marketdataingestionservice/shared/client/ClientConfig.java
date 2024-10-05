package com.vaynerakawalo.marketdataingestionservice.shared.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

  @Bean
  AlbionRmtBackendClient albionRmtBackendClient(
      @Value("${external.albion-rmt-backend:}") String baseUrl) {
    var restClient = RestClient.builder().baseUrl(baseUrl).build();

    return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(AlbionRmtBackendClient.class);
  }
}
