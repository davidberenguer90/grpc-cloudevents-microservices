package dev.dberenguer.grpccloudevents.checkout.grpc.client;

import com.google.protobuf.Empty;
import dev.dberenguer.grpccloudevents.grpc.ProductDtoGrpc;
import dev.dberenguer.grpccloudevents.grpc.ProductServiceGrpc;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;


@Slf4j
@Component
public class ProductClient {

    private final ProductServiceGrpc.ProductServiceBlockingStub blockingStub;

    public ProductClient(final Channel channel) {
        this.blockingStub = ProductServiceGrpc.newBlockingStub(channel);
    }

    public List<ProductDtoGrpc> findAllProducts() {
        final Empty request = Empty.newBuilder().build();

        log.info("Finding all products...");
        final Iterator<ProductDtoGrpc> products = this.blockingStub.findAllProducts(request);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(products, Spliterator.ORDERED), false)
                .peek(product -> log.info("Product received: {}", product))
                .toList();
    }

}
