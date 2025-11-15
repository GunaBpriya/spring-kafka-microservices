package main.java.com.example.userservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Producer Configuration for User Service
 * This configuration sets up the Kafka producer with optimal settings for reliability and performance
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Producer Factory Configuration
     * Creates a factory for producing Kafka messages with String keys and String values
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        
        // Kafka broker address
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        
        // Serializer for the key (String)
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        
        // Serializer for the value (String)
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        
        // Acknowledgment level: 'all' ensures the leader and all replicas acknowledge
        // This provides the strongest durability guarantee
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        
        // Number of retries for failed sends
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
        
        // Batch size for grouping messages (in bytes)
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        
        // Delay before sending a batch (allows more messages to accumulate)
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        
        // Total memory buffer for the producer
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        
        // Enable idempotence to prevent duplicate messages
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        
        // Maximum in-flight requests per connection
        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);
        
        // Compression type for better network utilization
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        
        // Request timeout
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Kafka Template Bean
     * Provides a high-level abstraction for sending messages to Kafka topics
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
