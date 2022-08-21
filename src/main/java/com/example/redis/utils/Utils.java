package com.example.redis.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convertFromObject(Object obj, Class<T> type) throws IOException {
        return objectMapper.readValue(objectMapper.writeValueAsBytes(obj), type);
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream initialStream = new ByteArrayInputStream(bytes)) {
            ObjectInput in = new ObjectInputStream(initialStream);
            return in.readObject();
        }
    }
}
