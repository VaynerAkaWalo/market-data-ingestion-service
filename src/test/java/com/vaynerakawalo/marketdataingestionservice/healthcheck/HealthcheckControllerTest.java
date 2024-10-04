package com.vaynerakawalo.marketdataingestionservice.healthcheck;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HealthcheckController.class)
class HealthcheckControllerTest {

  @Autowired private MockMvc mockMvc;

  @ParameterizedTest
  @ValueSource(strings = {"/health", "/healthcheck"})
  void health_shouldReturnOK(String uri) throws Exception {

    var expect =
        """
        {
          "status": "OK"
        }
        """;

    mockMvc.perform(get(uri)).andExpect(content().json(expect)).andExpect(status().isOk());
  }
}
