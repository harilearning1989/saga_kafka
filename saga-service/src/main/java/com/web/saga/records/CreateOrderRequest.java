package com.web.saga.records;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(@NotNull Long productId, @Min(1) Integer qty, @Min(1) Long amount) {
}
