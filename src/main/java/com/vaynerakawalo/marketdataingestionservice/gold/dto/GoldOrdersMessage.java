package com.vaynerakawalo.marketdataingestionservice.gold.dto;

import com.vaynerakawalo.marketdataingestionservice.gold.domain.GoldOrder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.util.Assert;

public record GoldOrdersMessage(int[] Prices, long[] Timestamps) {

  public GoldOrdersMessage {
    Assert.state(Prices.length == Timestamps.length, "Prices and Timestamps length must match");
  }

  private static long convertToEpoch(long tick) {
    return tick / 10_000 - 62136892800000L;
  }

  public List<GoldOrder> getGoldOrders() {
    return IntStream.range(0, Prices.length)
        .boxed()
        .map(index -> new GoldOrder(Prices[index], convertToEpoch(Timestamps[index])))
        .collect(Collectors.toList());
  }
}
