package com.vaynerakawalo.marketdataingestionservice.market.dto;

import com.vaynerakawalo.marketdataingestionservice.market.domain.MarketOrder;
import java.util.List;

public record MarketOrdersMessage(List<MarketOrder> orders) {}
