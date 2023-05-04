package dev.dberenguer.grpccloudevents.checkout.converter;

import dev.dberenguer.grpccloudevents.checkout.dto.ProductDto;
import dev.dberenguer.grpccloudevents.grpc.ProductDtoGrpc;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoGrpcToProductDtoConverter implements Converter<ProductDtoGrpc, ProductDto> {

    @Override
    public ProductDto convert(final ProductDtoGrpc source) {
        return ProductDto.builder()
                .code(source.getCode())
                .name(source.getName())
                .price(source.getPrice())
                .build();
    }
}
