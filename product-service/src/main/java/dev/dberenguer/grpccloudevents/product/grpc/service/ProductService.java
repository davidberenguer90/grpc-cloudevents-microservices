package dev.dberenguer.grpccloudevents.product.grpc.service;

import com.google.protobuf.Empty;
import dev.dberenguer.grpccloudevents.grpc.ProductDtoGrpc;
import dev.dberenguer.grpccloudevents.grpc.ProductServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

import java.util.List;

@Slf4j
@GRpcService
public class ProductService extends ProductServiceGrpc.ProductServiceImplBase {

    private static final List<ProductDtoGrpc> PRODUCTS = List.of(
            ProductDtoGrpc.newBuilder().setCode("1").setName("computer").setPrice(999.99).build(),
            ProductDtoGrpc.newBuilder().setCode("2").setName("mobile").setPrice(499.90).build(),
            ProductDtoGrpc.newBuilder().setCode("3").setName("keyboard").setPrice(49.95).build()
    );

    @Override
    public void findAllProducts(final Empty request, final StreamObserver<ProductDtoGrpc> responseObserver) {
        log.info("Sending products...");
        PRODUCTS.forEach(responseObserver::onNext);

        responseObserver.onCompleted();
        log.info("Products response observer completed");
    }

}
