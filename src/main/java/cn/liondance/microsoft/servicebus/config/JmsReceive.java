package cn.liondance.microsoft.servicebus.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.azure.messaging.servicebus.ServiceBusMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * The type Jms receive.
 */
@Slf4j
@Component
public class JmsReceive {

    /**
     * Receive queue.
     * 接收者来处理消息
     *
     * @param serviceBusMessage the service bus message
     */
    @JmsListener(destination = "meeting", containerFactory = "jmsListenerContainerFactory")
    public void receiveQueue(ServiceBusMessage serviceBusMessage) {


        // log.error("Message:{}", serviceBusMessage.getBody());
        // log.error("serviceBusMessage.getBody().toString():{}", serviceBusMessage.getBody().toString());
        JSONObject parseObject = JSON.parseObject(serviceBusMessage.getBody().toString());
        log.error("parseObject {}", parseObject);
        //throw new RuntimeException("");
    }
}
