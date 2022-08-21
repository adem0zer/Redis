package com.example.redis.repository;

import com.example.redis.dto.Employee;
import com.example.redis.dto.Shopping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {

    private final String hashReference = "Employee";

    @Resource(name = "redisTemplate")
    private HashOperations<String, Integer, Employee> hashOperations;

    public Employee save(Employee employee) {
        hashOperations.putIfAbsent(hashReference, employee.getEmpId(), employee);
        return hashOperations.get(hashReference, employee.getEmpId());
    }

    public Employee getEmployee(Integer id) {
        return hashOperations.get(hashReference, id);
    }

    public void updateEmployee(Employee employee) {
        hashOperations.put(hashReference, employee.getEmpId(), employee);
    }

    @Cacheable(value = "Employee")
    public List<Employee> getAllEmployee() {
        System.out.println("GETALL");
        List<Employee> employees = new ArrayList<>();
        hashOperations.entries(hashReference).forEach((integer, employee) -> {
            employees.add(employee);
        });
        return employees;
    }

    public boolean deleteEmployee(int id) {
        Long delete = hashOperations.delete(hashReference, id);
        return delete.intValue() == id;
    }
}
