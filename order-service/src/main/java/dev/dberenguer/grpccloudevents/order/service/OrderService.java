package dev.dberenguer.grpccloudevents.order.service;

import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;


@Slf4j
@Service
public class OrderService {

    @KafkaListener(topics = "#{@kafkaTopicBean}", groupId = "#{@kafkaGroupIdBean}",
            containerFactory = "kafkaCloudEventListenerContainerFactory")
    public void kafkaCloudEventListener(final CloudEvent message) {
        log.info("CloudEvent received {}", message);
        log.info("Message data {}", new String(message.getData().toBytes(), StandardCharsets.UTF_8));
    }

}
