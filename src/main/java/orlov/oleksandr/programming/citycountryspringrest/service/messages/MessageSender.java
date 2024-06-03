package orlov.oleksandr.programming.citycountryspringrest.service.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final Dotenv dotenv;

    public void send(String message) {
        rabbitTemplate.convertAndSend("myQueue", message);
    }

    public void sendMessageWithEmail(String message, String json){
        try {
            Map<String, String> messageContent = new HashMap<>();
            messageContent.put("message", message);
            messageContent.put("json", json);
            messageContent.put("email", dotenv.get("EMAIL_ADDRESS"));

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(messageContent);
            rabbitTemplate.convertAndSend("myQueue", jsonMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}