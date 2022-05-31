package cn.liondance.microsoft.servicebus.controller;

import cn.liondance.microsoft.servicebus.function.ServiceBusSendMessage;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * The type User controller.
 *
 * @author sunwei
 */
@Api(tags = "SendMessage")
@RestController
@RequestMapping(value = "sendMessage")
public class SendMessageController {

    private final ServiceBusSenderClient senderClient;


    private final ServiceBusSenderClient serviceBusTestSenderClient;

    public SendMessageController(@Qualifier("serviceBusMeetingSenderClient") ServiceBusSenderClient senderClient, @Qualifier("serviceBusTestSenderClient") ServiceBusSenderClient serviceBusTestSenderClient) {
        this.senderClient = senderClient;
        this.serviceBusTestSenderClient = serviceBusTestSenderClient;
    }

    /**
     * Send message response entity.
     *
     * @param body the body
     * @return the response entity
     */
    @ApiOperation(value = "sendMessage")
    @PostMapping(value = "sendMessage")
    public ResponseEntity<String> sendMessage(String queueName, @RequestBody String body) {
        switch (queueName) {
            case "meeting":
                senderClient.sendMessage(new ServiceBusMessage(body));
                break;
            case "test":
                serviceBusTestSenderClient.sendMessage(new ServiceBusMessage(body));
                break;
        }
        return ResponseEntity.ok("ok");
    }

    /**
     * Send message batch response entity.
     *
     * @param body the body
     * @return the response entity
     */
    @ApiOperation(value = "sendMessageBatch")
    @PostMapping(value = "sendMessageBatch")
    public ResponseEntity<String> sendMessageBatch(@RequestBody List<String> body) {
        ServiceBusSendMessage.sendMessageBatch(senderClient, body);
        return ResponseEntity.ok("ok");
    }


    /**
     * Schedule message response entity.
     *
     * @param offsetDateTime the offset date time
     * @param body           the body
     * @return the response entity
     */
    @ApiOperation(value = "scheduleMessage")
    @PostMapping(value = "scheduleMessage")
    public ResponseEntity<String> scheduleMessage(String offsetDateTime, @RequestBody String body) {
        ServiceBusSendMessage.scheduleMessage(senderClient, body, offsetDateTime);
        return ResponseEntity.ok("ok");
    }

    /**
     * Close response entity.
     *
     * @return the response entity
     */
    @ApiOperation(value = "close")
    @PostMapping(value = "close")
    public ResponseEntity<String> close() {
        senderClient.close();
        return ResponseEntity.ok("ok");
    }

}
