package cn.liondance.microsoft.servicebus.config;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * The type Shedlock config.
 *
 * @author sunwei
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class ServiceBusSenderClientConfig {

    private final ServiceBusMeetingConfig serviceBusMeetingConfig;

    private final ServiceBusTestConfig serviceBusTestConfig;

    @Primary
    @Qualifier("serviceBusTestSenderClient")
    @Bean(name = "serviceBusTestSenderClient")
    public ServiceBusSenderClient serviceBusTestSenderClient() {
        log.info("receiveMessages init");
        return new ServiceBusClientBuilder().connectionString(serviceBusTestConfig.getConnectionString()).sender().queueName(serviceBusTestConfig.getQueueName()).topicName(serviceBusTestConfig.getTopicName()).buildClient();
    }


    /**
     * Lock provider lock provider.
     *
     * @return the lock provider
     */
    @Qualifier("serviceBusMeetingSenderClient")
    @Bean(name = "serviceBusMeetingSenderClient")
    public ServiceBusSenderClient serviceBusMeetingSenderClient() {
        log.info("receiveMessages init");
        return new ServiceBusClientBuilder().connectionString(serviceBusMeetingConfig.getConnectionString()).sender().queueName(serviceBusMeetingConfig.getQueueName()).topicName(serviceBusMeetingConfig.getTopicName()).buildClient();
    }

}
