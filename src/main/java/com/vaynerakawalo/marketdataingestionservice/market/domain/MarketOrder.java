package com.vaynerakawalo.marketdataingestionservice.market.domain;

import org.springframework.util.Assert;

public record MarketOrder(
    int id,
    String itemTypeId,
    String itemGroupTypeId,
    int locationId,
    int qualityLevel,
    int tier,
    int enchantmentLevel,
    long unitPriceSilver,
    int amount,
    String buyerName,
    String auctionType,
    String expires) {

  public MarketOrder {
    Assert.state(id > 0, "Order ID must be grater than zero");
    Assert.state(qualityLevel >= 0, "Quality level must be grater than or equal zero");
    Assert.state(enchantmentLevel >= 0, "Enchantment level must be grater than or equal zero");
    Assert.state(unitPriceSilver > 0, "Unit price must be grater than zero");
    Assert.state(amount > 0, "amount must be grater than zero");
    Assert.hasLength(itemTypeId, "Item type cannot be empty");
    Assert.hasLength(itemGroupTypeId, "Item Group cannot be empty");
    Assert.hasLength(auctionType, "Auction type cannot be empty");
    Assert.hasLength(expires, "Expires cannot be empty");

    buyerName =
        locationId == 3003 ? "@BLACK_MARKET" : "@UNKNOWN"; // this locationID is black market
    tier = Character.isDigit(itemGroupTypeId.charAt(1)) ? itemGroupTypeId.charAt(1) - '0' : 1;

    Assert.state(
        tier > 0 && tier < 9,
        "Tier must be between 1 and 8 inclusive, invalid value: %s".formatted(tier));
  }
}
