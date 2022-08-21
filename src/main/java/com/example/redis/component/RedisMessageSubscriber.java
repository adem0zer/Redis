package com.example.redis.component;

import com.example.redis.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RedisMessageSubscriber implements MessageListener {
    public static List<String> messageList = new ArrayList<>();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = "";
        try {
            msg = Utils.convertFromObject(Utils.deserialize(message.getBody()), String.class);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        log.info("Message received: " + msg);
        messageList.add(msg);
    }

    public List<String> getMessage() {
        return messageList;
    }
}
