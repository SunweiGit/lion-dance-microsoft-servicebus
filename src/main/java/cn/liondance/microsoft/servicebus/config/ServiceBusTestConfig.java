package cn.liondance.microsoft.servicebus.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author sunwei
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "azure.service-bus.test")
public class ServiceBusTestConfig {
    private String connectionString;
    private String queueName;
    private String topicName;
    private String subName;

}
