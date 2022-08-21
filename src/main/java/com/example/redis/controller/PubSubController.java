package com.example.redis.controller;

import com.example.redis.component.RedisMessagePublisher;
import com.example.redis.component.RedisMessageSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Redis as a pub/sub
@RestController
@RequestMapping("/pubsub")
@RequiredArgsConstructor
public class PubSubController {
    private final RedisMessagePublisher redisMessagePublisher;
    private final RedisMessageSubscriber redisMessageSubscriber;

    @GetMapping("publish/{message}")
    void publish(@PathVariable String message) {
        redisMessagePublisher.publish(message);
    }

    @PostMapping("subscribe")
    List<String> subscribe() {
        return redisMessageSubscriber.getMessage();
    }
}
