package dev.dberenguer.grpccloudevents.order.configuration;

import io.cloudevents.CloudEvent;
import io.cloudevents.kafka.CloudEventDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OrderKafkaConfiguration {

    @Value("${dberenguer.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;
    @Value("${dberenguer.kafka.consumer.group-id}")
    private String kafkaGroupId;
    @Value("${dberenguer.kafka.consumer.topic}")
    private String kafkaTopic;

    @Bean
    public String kafkaGroupIdBean() {
        return this.kafkaGroupId;
    }

    @Bean
    public String kafkaTopicBean() {
        return this.kafkaTopic;
    }

    @Bean
    public ConsumerFactory<String, CloudEvent> consumerCloudEventFactory() {
        final Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaBootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, this.kafkaGroupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CloudEventDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CloudEvent> kafkaCloudEventListenerContainerFactory(final ConsumerFactory<String, CloudEvent> consumerCloudEventFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, CloudEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerCloudEventFactory);
        return factory;
    }

}
