package orlov.oleksandr.programming.citycountryspringrest.service.messages;

public interface MessageSender {
    void sendMessageWithEmail(String message, String json);
}
