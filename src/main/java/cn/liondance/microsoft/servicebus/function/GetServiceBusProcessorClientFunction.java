package cn.liondance.microsoft.servicebus.function;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * <a>https://docs.microsoft.com/zh-cn/azure/service-bus-messaging/service-bus-java-how-to-use-queues</a>
 *
 * @author sunwei
 */
@Slf4j
public class GetServiceBusProcessorClientFunction {

    private GetServiceBusProcessorClientFunction() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Send message.
     *
     * @param connectionString the connection string
     * @param queueName        the queue name
     * @param topicName        the topic name
     * @param subName          the sub name
     * @param processMessage   the process message
     * @param processError     the process error
     * @return service bus processor client
     */
    public static ServiceBusProcessorClient getServiceBusProcessorClient(String connectionString,
                                                                         String queueName,
                                                                         String topicName,
                                                                         String subName, Consumer<ServiceBusReceivedMessageContext> processMessage, Consumer<ServiceBusErrorContext> processError) {
        return new ServiceBusClientBuilder().connectionString(connectionString).processor().queueName(queueName).topicName(topicName).subscriptionName(subName).processMessage(processMessage).processError(processError).buildProcessorClient();
    }

    /**
     * Gets service bus processor client.
     *
     * @param connectionString the connection string
     * @param queueName        the queue name
     * @param processMessage   the process message
     * @param processError     the process error
     * @return the service bus processor client
     */
    public static ServiceBusProcessorClient getServiceBusProcessorClient(String connectionString,
                                                                         String queueName, Consumer<ServiceBusReceivedMessageContext> processMessage, Consumer<ServiceBusErrorContext> processError) {
        return new ServiceBusClientBuilder().connectionString(connectionString).processor().queueName(queueName).processMessage(processMessage).processError(processError).buildProcessorClient();
    }

    /**
     * Gets service bus processor client.
     *
     * @param connectionString the connection string
     * @param queueName        the queue name
     * @param processMessage   the process message
     * @return the service bus processor client
     */
    public static ServiceBusProcessorClient getServiceBusProcessorClient(String connectionString,
                                                                         String queueName, Consumer<ServiceBusReceivedMessageContext> processMessage) {
        return new ServiceBusClientBuilder().connectionString(connectionString).processor().queueName(queueName).processMessage(processMessage).buildProcessorClient();
    }


    /**
     * Gets service bus processor client.
     *
     * @param connectionString the connection string
     * @param queueName        the queue name
     * @param topicName        the topic name
     * @param subName          the sub name
     * @param processMessage   the process message
     * @return the service bus processor client
     */
    public static ServiceBusProcessorClient getServiceBusProcessorClient(String connectionString,
                                                                         String queueName,
                                                                         String topicName,
                                                                         String subName, Consumer<ServiceBusReceivedMessageContext> processMessage) {
        return new ServiceBusClientBuilder().connectionString(connectionString).processor().queueName(queueName).topicName(topicName).subscriptionName(subName).processMessage(processMessage).buildProcessorClient();
    }


}
