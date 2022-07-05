package cn.liondance.microsoft.servicebus.config;

import cn.liondance.microsoft.servicebus.function.GetServiceBusProcessorClientFunction;
import com.azure.messaging.servicebus.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * The type Shedlock config.
 *
 * @author sunwei
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class ServiceBusReceiveClientConfig {

    private final ServiceBusMeetingConfig serviceBusMeetingConfig;

    private final ServiceBusTestConfig serviceBusTestConfig;


    private static void processTestMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        log.error("processTestMessage Processing message. Session: {}, Sequence #: {}. Contents: {}", message.getMessageId(), message.getSequenceNumber(), message.getBody());
    }

    private static void processTestError(ServiceBusErrorContext context) {
        log.error("processTestError Error when receiving messages from namespace: {}. Entity: {}", context.getFullyQualifiedNamespace(), context.getEntityPath());

        if (!(context.getException() instanceof ServiceBusException)) {
            log.error("Non-ServiceBusException occurred: {}", context.getException());
            return;
        }

        ServiceBusException exception = (ServiceBusException) context.getException();
        ServiceBusFailureReason reason = exception.getReason();

        if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND || reason == ServiceBusFailureReason.UNAUTHORIZED) {
            log.error("An unrecoverable error occurred. Stopping processing with reason {}: {}", reason, exception.getMessage());
        } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
            log.error("Message lock lost for message: {}", context.getException());
        } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
            try {
                // Choosing an arbitrary amount of time to wait until trying again.
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.error("Unable to sleep for period of time");
                Thread.currentThread().interrupt();
            }
        } else {
            log.error("Error source {}, reason {}, message: {}", context.getErrorSource(), reason, context.getException());
        }
    }

    private static void processMeetingMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        log.error("processMeetingMessage Processing message. Session: {}, Sequence #: {}. Contents: {}", message.getMessageId(), message.getSequenceNumber(), message.getBody());
    }

    private static void processMeetingError(ServiceBusErrorContext context) {
        log.error("processMeetingError Error when receiving messages from namespace: {}. Entity: {}", context.getFullyQualifiedNamespace(), context.getEntityPath());

        if (!(context.getException() instanceof ServiceBusException)) {
            log.error("Non-ServiceBusException occurred: {}", context.getException());
            return;
        }

        ServiceBusException exception = (ServiceBusException) context.getException();
        ServiceBusFailureReason reason = exception.getReason();

        if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND || reason == ServiceBusFailureReason.UNAUTHORIZED) {
            log.error("An unrecoverable error occurred. Stopping processing with reason {}: {}", reason, exception.getMessage());
        } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
            log.error("Message lock lost for message: {}", context.getException());
        } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
            try {
                // Choosing an arbitrary amount of time to wait until trying again.
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.error("Unable to sleep for period of time");
                Thread.currentThread().interrupt();
            }
        } else {
            log.error("Error source {}, reason {}, message: {}", context.getErrorSource(), reason, context.getException());
        }
    }

    /**
     * Service bus meeting receive messages.
     */
    @Bean
    public void serviceBusMeetingReceiveMessages() {
        GetServiceBusProcessorClientFunction.getServiceBusProcessorClient(serviceBusMeetingConfig.getConnectionString(), serviceBusMeetingConfig.getQueueName(), ServiceBusReceiveClientConfig::processMeetingMessage, ServiceBusReceiveClientConfig::processMeetingError).start();
    }

    /**
     * Service bus meeting receive messages.
     */
    @Bean
    public void serviceBusTestReceiveMessages() {
        GetServiceBusProcessorClientFunction.getServiceBusProcessorClient(serviceBusTestConfig.getConnectionString(), serviceBusTestConfig.getQueueName(), ServiceBusReceiveClientConfig::processTestMessage, ServiceBusReceiveClientConfig::processTestError).start();
    }

}
