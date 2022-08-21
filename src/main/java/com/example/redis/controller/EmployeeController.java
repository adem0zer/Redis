package com.example.redis.controller;

import com.example.redis.dto.Employee;
import com.example.redis.dto.Shopping;
import com.example.redis.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

//Redis as a Cache
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@EnableCaching
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final CacheManager cacheManager;

    @PostMapping(value = "/save")
    @CachePut(value = "Employee", key = "#employee.empId")
    public Employee save(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping(value = "/getAll")
    public List<Employee> getAllProducts() {
        return employeeRepository.getAllEmployee();
    }

    @GetMapping(value = "/get/{id}")
    @Cacheable(key = "#id", value = "Employee")
    public Employee findItems(@PathVariable int id) {
        return employeeRepository.getEmployee(id);
    }

    @DeleteMapping("delete/{id}")
    public boolean remove(@PathVariable int id) {
        return employeeRepository.deleteEmployee(id);
    }

    @GetMapping("cacheClear")
    public void cacheClear() {
        this.cacheManager.getCacheNames().parallelStream().forEach(cacheName -> {
            Objects.requireNonNull(this.cacheManager.getCache(cacheName)).clear();
        });
    }
}
