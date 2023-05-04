package dev.dberenguer.grpccloudevents.checkout.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class OrderItemDto {
    ProductDto product;
    Integer quantity;
}