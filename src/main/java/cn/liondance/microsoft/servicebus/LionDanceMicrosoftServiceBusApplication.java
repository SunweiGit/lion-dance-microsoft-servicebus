package cn.liondance.microsoft.servicebus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunwei
 */
@SpringBootApplication
@RestController
public class LionDanceMicrosoftServiceBusApplication {

    public static void main(String[] args) {
        SpringApplication.run(LionDanceMicrosoftServiceBusApplication.class, args);
    }

}
