package cn.liondance.microsoft.servicebus.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.TextMessage;


/**
 * The type User controller.
 *
 * @author sunwei
 */
@Api(tags = "JMSSendMessage")
@Slf4j
@RestController
@RequestMapping(value = "JMSsendMessage")
@AllArgsConstructor
public class JmsSendMessageController {

    private final JmsTemplate jmsTemplate;

    /**
     * Send message response entity.
     *
     * @param body the body
     * @return the response entity
     */

    @ApiOperation(value = "sendMessage", notes = "尝试给不同的队列发送消息")
    @PostMapping(value = "sendMessage")
    public ResponseEntity<String> sendMessage(String queueName, @RequestBody String body) {
        jmsTemplate.send("meeting", session -> session.createTextMessage(body));
        return ResponseEntity.ok("ok");
    }

    /**
     * jms 发送延时消息到MicrosoftServiceBus 这里并没有生效
     *
     * @param queueName
     * @param body
     * @return
     */
    @ApiOperation(value = "scheduleMessage", notes = "尝试给不同的队列发送消息")
    @PostMapping(value = "scheduleMessage")
    public ResponseEntity<String> scheduleMessage(String queueName, @RequestBody String body) {
       /* jmsTemplate.send(queueName, session -> {
            TextMessage textMessage = session.createTextMessage(body);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 2 * 60 * 1000);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 1);
            return textMessage;
        });*/
        return ResponseEntity.ok("ok");
    }


}
