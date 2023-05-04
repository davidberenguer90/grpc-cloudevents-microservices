package dev.dberenguer.grpccloudevents.checkout.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class OrderDto {
    UUID id;
    List<OrderItemDto> orderItems;
}