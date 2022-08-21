package com.example.redis.controller;

import com.example.redis.dto.Shopping;
import com.example.redis.repository.ShoppingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Redis As a DB
@RestController
@RequestMapping("/shoppingItems")
@RequiredArgsConstructor
public class ShoppingController {

    private final ShoppingRepository shoppingRepository;

    @PostMapping(value = "/save")
    public Shopping save(@RequestBody Shopping shopping) {
        return shoppingRepository.save(shopping);
    }

    @GetMapping(value = "/getAll")
    public List<Shopping> getAllProducts() {
        return shoppingRepository.findAll();
    }

    @GetMapping(value = "/get/{id}")
    public Shopping findItems(@PathVariable int id) {
        return shoppingRepository.findProductById(id);
    }

    @DeleteMapping("delete/{id}")
    public String remove(@PathVariable int id) {
        return shoppingRepository.deleteProduct(id);
    }
}
