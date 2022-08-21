package com.example.redis.repository;

import com.example.redis.dto.Shopping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ShoppingRepository {
    public static final String HASH_KEY = "Shopping";

    private final RedisTemplate<String, Object> redisTemplate;

    public Shopping save(Shopping shopping) {
        try {
            redisTemplate.opsForHash().put(HASH_KEY, shopping.getId(), shopping);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return shopping;
    }

    public List<Shopping> findAll() {
        List<Object> values = redisTemplate.opsForHash().values(HASH_KEY);
        List<Shopping> shoppings = new ArrayList<>();
        values.forEach(a->{
            shoppings.add((Shopping)a);
        });
        return shoppings;
    }

    public Shopping findProductById(int id) {
        return (Shopping) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    public String deleteProduct(int id) {
        redisTemplate.opsForHash().delete(HASH_KEY, id);
        return "shopping item deleted successfully !!";
    }
}
