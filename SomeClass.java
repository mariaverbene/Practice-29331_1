package kafkaspring;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class SomeClass extends MessageProducerSupport implements MessageHandler  {

    @Override
    public void handleMessage(Message message) throws MessagingException {

        ObjectMapper mapper;
        JsonNode jsonNode;

       try {
            mapper = new ObjectMapper();
            jsonNode = mapper.readTree((String) message.getPayload());

            ((ObjectNode) jsonNode).put("handledTimestamp", System.currentTimeMillis()); //
            message = MessageBuilder
                    .withPayload(mapper.writeValueAsString(jsonNode))
                    .setHeader(KafkaHeaders.TOPIC, "topic2")
                    .build();
       }
       catch (IOException e) {
            e.printStackTrace();
       }
       finally {
           sendMessage(message);
       }

    }
}