package com.aryancodes.employee.services;

import com.aryancodes.employee.entity.EmployeeEntity;
import com.aryancodes.employee.model.Employee;
import com.aryancodes.employee.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeRepository.save(employeeEntity);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        return employeeEntities
                .stream()
                .map(employeeEntity -> new Employee(
                        employeeEntity.getId(),
                        employeeEntity.getFirstName(),
                        employeeEntity.getLastName(),
                        employeeEntity.getEmailId()
                    ))
                .toList();
    }

    @Override
    public boolean deleteEmployee(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        if(employeeEntity == null){
            return false;
        }
        employeeRepository.delete(employeeEntity);
        return true;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        if(employeeEntity != null){
            return new Employee(
                    employeeEntity.getId(),
                    employeeEntity.getFirstName(),
                    employeeEntity.getLastName(),
                    employeeEntity.getEmailId()
            );
        }
        return null;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        if(employeeEntity != null){
            employeeEntity.setFirstName(employee.getFirstName());
            employeeEntity.setLastName(employee.getLastName());
            employeeEntity.setEmailId(employee.getEmailId());
            employeeRepository.save(employeeEntity);
        }
        return employee;
    }
}
