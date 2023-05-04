package dev.dberenguer.grpccloudevents.checkout.configuration;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckoutGrpcConfiguration {

    @Value("${dberenguer.grpc.product.host}")
    private String grpcProductHost;
    @Value("${dberenguer.grpc.product.port}")
    private Integer grpcProductPort;

    @Bean
    public Channel configGrpcChannel(){
        return ManagedChannelBuilder.forAddress(this.grpcProductHost, this.grpcProductPort)
                .usePlaintext()
                .build();
    }


}
