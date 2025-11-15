package main.java.com.example.notificationservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Consumer Configuration for Notification Service
 * This configuration sets up the Kafka consumer with optimal settings for reliability and performance
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    /**
     * Consumer Factory Configuration
     * Creates a factory for consuming Kafka messages with String keys and String values
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        
        // Kafka broker address
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        
        // Consumer group ID - consumers with the same group ID share the load
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        
        // Deserializer for the key (String)
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        
        // Deserializer for the value (String)
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        
        // Auto offset reset strategy: 'earliest' reads from the beginning if no offset exists
        // Options: 'earliest', 'latest', 'none'
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        // Disable auto-commit for manual offset management (more control)
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        
        // Maximum number of records returned in a single poll
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        
        // Maximum time between polls before consumer is considered dead
        configProps.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        
        // Session timeout - time consumer can be out of contact before rebalancing
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        
        // Heartbeat interval - frequency of heartbeats to coordinator
        configProps.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        
        // Fetch minimum bytes - minimum data to return from a fetch request
        configProps.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);
        
        // Fetch maximum wait - maximum time to wait for fetch min bytes
        configProps.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);
        
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    /**
     * Kafka Listener Container Factory
     * Configures the container for @KafkaListener annotated methods
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        
        factory.setConsumerFactory(consumerFactory());
        
        // Number of concurrent consumer threads
        factory.setConcurrency(3);
        
        // Acknowledge mode: MANUAL_IMMEDIATE allows manual offset commit
        // Options: RECORD, BATCH, TIME, COUNT, COUNT_TIME, MANUAL, MANUAL_IMMEDIATE
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        
        // Enable batch processing for better throughput (optional)
        factory.setBatchListener(false);
        
        // Auto-startup the container
        factory.setAutoStartup(true);
        
        return factory;
    }
}
