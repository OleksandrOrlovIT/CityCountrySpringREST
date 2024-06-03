package orlov.oleksandr.programming.citycountryspringrest.service.messages.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import orlov.oleksandr.programming.citycountryspringrest.service.messages.MessageSender;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class RabbitMQMessageSender implements MessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final Dotenv dotenv;

    public void sendMessageWithEmail(String message, String json){
        try {
            Map<String, String> messageContent = new HashMap<>();
            messageContent.put("subject", message);
            messageContent.put("content", json);
            messageContent.put("email", dotenv.get("EMAIL_ADDRESS"));

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(messageContent);
            rabbitTemplate.convertAndSend("myQueue", jsonMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}