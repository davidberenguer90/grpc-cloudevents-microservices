package dev.dberenguer.grpccloudevents.checkout.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dberenguer.grpccloudevents.checkout.dto.OrderDto;
import dev.dberenguer.grpccloudevents.checkout.dto.OrderItemDto;
import dev.dberenguer.grpccloudevents.checkout.dto.ProductDto;
import dev.dberenguer.grpccloudevents.checkout.grpc.client.ProductClient;
import dev.dberenguer.grpccloudevents.grpc.ProductDtoGrpc;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class CheckoutService {

    private static final Random RANDOM = new Random();
    private final ProductClient productClient;
    private final ConversionService conversionService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, CloudEvent> kafkaTemplate;

    @Value("${dberenguer.kafka.consumer.topic}")
    private String kafkaTopic;

    @Scheduled(fixedRate = 10000)
    public void checkout() throws JsonProcessingException {
        final List<ProductDtoGrpc> productsDto = this.productClient.findAllProducts();

        final OrderDto orderDto = this.createOrder(productsDto);
        final CloudEvent event = this.createCloudEvent(orderDto, "order.placed");

        this.sendMessage(event);
        log.info("Published order: {}", orderDto);
    }

    private OrderDto createOrder(final List<ProductDtoGrpc> productsDtoGrpc) {
        final List<ProductDto> productsDto = productsDtoGrpc.stream()
                .map(p -> this.conversionService.convert(p, ProductDto.class))
                .toList();

        final ProductDto productDto1 = productsDto.get(RANDOM.ints(0,3).findFirst().getAsInt());
        final Integer quantity1 = RANDOM.ints(1,10).findFirst().getAsInt();
        final ProductDto productDto2 = productsDto.get(RANDOM.ints(0,3).findFirst().getAsInt());
        final Integer quantity2 = RANDOM.ints(1,10).findFirst().getAsInt();

        return OrderDto.builder()
                .id(UUID.randomUUID())
                .orderItems(
                        List.of(OrderItemDto.builder().product(productDto1).quantity(quantity1).build(),
                                OrderItemDto.builder().product(productDto2).quantity(quantity2).build())
                )
                .build();
    }

    private CloudEvent createCloudEvent(final Object orderDto, final String type) throws JsonProcessingException {
        return CloudEventBuilder.v1()
                .withSource(URI.create("checkout-service"))
                .withType(type)
                .withId(UUID.randomUUID().toString())
                .withData(MediaType.APPLICATION_JSON_VALUE, this.objectMapper.writeValueAsBytes(orderDto))
                .build();
    }

    private void sendMessage(final CloudEvent event) {
        final ListenableFuture<SendResult<String, CloudEvent>> futureSent = this.kafkaTemplate.send(this.kafkaTopic, event);

        futureSent.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(final SendResult<String, CloudEvent> result) {
                log.info("Event {} sent to topic {}", result.getProducerRecord().value().getId(), result.getRecordMetadata().topic());
            }

            @Override
            public void onFailure(final Throwable ex) {
                log.error("Error sending... {}", ex.getMessage());
            }
        });
    }

}
