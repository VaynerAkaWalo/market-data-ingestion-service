package com.vaynerakawalo.marketdataingestionservice.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

  @GetMapping(value = {"health", "healthcheck"})
  HealthResponse healthcheck() {
    return new HealthResponse("OK");
  }
}

record HealthResponse(String status) {}
