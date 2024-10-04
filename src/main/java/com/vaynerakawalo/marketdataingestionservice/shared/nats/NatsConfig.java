package com.vaynerakawalo.marketdataingestionservice.shared.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import io.nats.client.Nats;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("nats")
@Configuration
public class NatsConfig {

  @Bean
  Connection connection(@Value("${nats.server}") String serverUrl)
      throws IOException, InterruptedException {
    return Nats.connect(serverUrl);
  }

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}