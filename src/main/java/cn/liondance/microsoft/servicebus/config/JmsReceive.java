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
        JSONObject parseObject = JSON.parseObject(serviceBusMessage.getBody().toString());
        log.error("parseObject {}", parseObject);
        /**
         *<h1>注意</h1> 在处理消息的时候如果出现异常，该receive可以继续接收消息。但是如果项目重启错误消息会再次消费
         */
        //throw new RuntimeException("");
    }
}
