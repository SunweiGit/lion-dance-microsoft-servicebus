package cn.liondance.microsoft.servicebus.function;

import com.azure.messaging.servicebus.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * <a>https://docs.microsoft.com/zh-cn/azure/service-bus-messaging/service-bus-java-how-to-use-queues</a>
 *
 * @author sunwei
 */
@Slf4j
@Builder
public class ServiceBusReceiveMessage {

    // handles received messages
    public static ServiceBusProcessorClient receiveMessages(String connectionString, String queueName, Consumer<ServiceBusReceivedMessageContext> processMessage, Consumer<ServiceBusErrorContext> processError) {
        CountDownLatch countdownLatch = new CountDownLatch(1);
        // Create an instance of the processor through the ServiceBusClientBuilder
        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .processor()
                .queueName(queueName)
                .processMessage(processMessage)
                .processError(processError)
                .buildProcessorClient();
        return processorClient; /*
        System.out.println("Starting the processor");
        processorClient.start();
        processorClient.stop();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("Stopping and closing the processor");
        processorClient.close();*/
    }

    private static void processMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        log.debug("Processing message. Session: {}, Sequence #: {}. Contents: {}", message.getMessageId(),
                message.getSequenceNumber(), message.getBody());
    }

    private static void processError(ServiceBusErrorContext context, CountDownLatch countdownLatch) {
        log.debug("Error when receiving messages from namespace: {}. Entity: {}",
                context.getFullyQualifiedNamespace(), context.getEntityPath());

        if (!(context.getException() instanceof ServiceBusException)) {
            log.debug("Non-ServiceBusException occurred: {}", context.getException());
            return;
        }

        ServiceBusException exception = (ServiceBusException) context.getException();
        ServiceBusFailureReason reason = exception.getReason();

        if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED
                || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND
                || reason == ServiceBusFailureReason.UNAUTHORIZED) {
            log.debug("An unrecoverable error occurred. Stopping processing with reason {}: {}",
                    reason, exception.getMessage());

            countdownLatch.countDown();
        } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
            log.debug("Message lock lost for message: {}", context.getException());
        } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
            try {
                // Choosing an arbitrary amount of time to wait until trying again.
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("Unable to sleep for period of time");
            }
        } else {
            log.debug("Error source {}, reason {}, message: {}", context.getErrorSource(),
                    reason, context.getException());
        }
    }
}
