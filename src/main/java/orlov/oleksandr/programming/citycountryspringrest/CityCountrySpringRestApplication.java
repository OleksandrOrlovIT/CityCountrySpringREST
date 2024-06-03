package orlov.oleksandr.programming.citycountryspringrest;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import orlov.oleksandr.programming.citycountryspringrest.service.messages.MessageSender;

@AllArgsConstructor
@SpringBootApplication
public class CityCountrySpringRestApplication implements CommandLineRunner {

    private MessageSender messageSender;

    public static void main(String[] args) {
        SpringApplication.run(CityCountrySpringRestApplication.class, args);
    }

    //TODO delete this run method and implement a message sender when entity are created
    @Override
    public void run(String... args) throws Exception {
        messageSender.send("Hello from Application A");
    }

}
