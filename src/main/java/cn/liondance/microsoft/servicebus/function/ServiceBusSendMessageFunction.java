package cn.liondance.microsoft.servicebus.function;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusMessageBatch;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * <a>https://docs.microsoft.com/zh-cn/azure/service-bus-messaging/service-bus-java-how-to-use-queues</a>
 *
 * @author sunwei
 */
@Slf4j
public class ServiceBusSendMessageFunction {

    private ServiceBusSendMessageFunction() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Send message.
     *
     * @param senderClient the sender client
     * @param body         the body
     */
    public static void sendMessage(ServiceBusSenderClient senderClient, String body) {
        // create a Service Bus Sender client for the queue
        // send one message to the queue
        sendMessage(senderClient, new ServiceBusMessage(body));
        log.debug("Sent a single message to the queue: " + senderClient.getFullyQualifiedNamespace());
    }


    /**
     * Send message.
     *
     * @param senderClient      the sender client
     * @param serviceBusMessage the service bus message
     */
    public static void sendMessage(ServiceBusSenderClient senderClient, ServiceBusMessage serviceBusMessage) {
        // create a Service Bus Sender client for the queue
        // send one message to the queue
        senderClient.sendMessage(serviceBusMessage);
        log.debug("Sent a single message to the queue: " + senderClient.getFullyQualifiedNamespace());
    }


    /**
     * Schedule message.
     *
     * @param senderClient   the sender client
     * @param body           the body
     * @param offsetDateTime the offset date time
     */
    public static void scheduleMessage(ServiceBusSenderClient senderClient, String body, String offsetDateTime) {
        // create a Service Bus Sender client for the queue
        // send one message to the queue
        LocalDateTime localDateTime = LocalDateTime.parse(offsetDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        senderClient.scheduleMessage(new ServiceBusMessage(body), OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(8)));
        log.debug("Sent a single message to the queue: " + senderClient.getFullyQualifiedNamespace());
    }

    public static void scheduleMessage(ServiceBusSenderClient senderClient, ServiceBusMessage serviceBusMessage, String offsetDateTime) {
        // create a Service Bus Sender client for the queue
        // send one message to the queue
        LocalDateTime localDateTime = LocalDateTime.parse(offsetDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        senderClient.scheduleMessage(serviceBusMessage, OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(8)));
        log.debug("Sent a single message to the queue: " + senderClient.getFullyQualifiedNamespace());
    }

    /**
     * Send message batch.
     *
     * @param senderClient the sender client
     * @param body         the body
     */
    public static void sendMessageBatch(ServiceBusSenderClient senderClient, String... body) {
        // create a Service Bus Sender client for the queue
        // Creates an ServiceBusMessageBatch where the ServiceBus.
        // create a list of messages
        List<ServiceBusMessage> listOfMessages = Arrays.stream(body).map(ServiceBusMessage::new).toList();
        // We try to add as many messages as a batch can fit based on the maximum size and send to Service Bus when
        // the batch can hold no more messages. Create a new batch for next set of messages and repeat until all
        // messages are sent.
        sendMessageBatch(senderClient, listOfMessages);
    }

    public static void sendMessageBatch(ServiceBusSenderClient senderClient, List<ServiceBusMessage> listOfMessages) {
        // create a Service Bus Sender client for the queue
        // Creates an ServiceBusMessageBatch where the ServiceBus.
        ServiceBusMessageBatch messageBatch = senderClient.createMessageBatch();
        // create a list of messages
        // We try to add as many messages as a batch can fit based on the maximum size and send to Service Bus when
        // the batch can hold no more messages. Create a new batch for next set of messages and repeat until all
        // messages are sent.
        for (ServiceBusMessage message : listOfMessages) {
            if (messageBatch.tryAddMessage(message)) {
                continue;
            }
            // The batch is full, so we create a new batch and send the batch.
            senderClient.sendMessages(messageBatch);
            // create a new batch
            messageBatch = senderClient.createMessageBatch();
            // Add that message that we couldn't before.
        }
        if (messageBatch.getCount() > 0) {
            senderClient.sendMessages(messageBatch);
        }
        //close the client
    }
}
