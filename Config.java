package kafkaspring;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.kafka.Kafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

@Configuration
@EnableIntegration
public class Config {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Autowired
    private SomeClass someClass;

    @Autowired
    private ConfigProperties configProperties;

    @Bean
    public IntegrationFlow topicFlow1() {
        return IntegrationFlows
                .from (Kafka.messageDrivenChannelAdapter(new ConcurrentMessageListenerContainer<String,String>((ConsumerFactory<String,String>) consumerFactory(), new ContainerProperties("topic1"))))
                .handle(someClass)
                .get();
    }

    @Bean
    public IntegrationFlow topicFlow2() {
        return IntegrationFlows
                .from (someClass)
                .handle(Kafka.outboundChannelAdapter(producerFactory()).topic("topic2"))
                .get();
    }

    @Bean
    public ConsumerFactory<?, ?> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new StringDeserializer()
              //new JsonDeserializer<>(Item.class)
        );
    }

    @Bean
    public Map consumerConfigs() {
        Map<String,Object> properties = new HashMap<>();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.deserializer",configProperties.getKey_deserializer());
        properties.put("value.deserializer", configProperties.getValue_deserializer());
      //properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put("group.id", configProperties.getGroupId());
        return properties;
    }

    @Bean
    public KafkaTemplate kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
  //public ProducerFactory<String, Item> producerFactory() {
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map producerConfigs() {
        Map<String, Object> properties = new HashMap();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", configProperties.getKey_serializer());
        properties.put("value.serializer", configProperties.getValue_serializer());
     // properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return properties;
    }
}
