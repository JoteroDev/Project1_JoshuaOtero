package org.example.service;

import org.example.entities.Employee;
import org.example.repositories.EmployeeDAO;

public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    @Override
    public Employee createEmployee(Employee employee) {
        if (employee.getUsername().length() == 0) {
            throw new RuntimeException("Book's title cannot be empty");
        } else if (employee.getPassword().length() == 0) {
            throw new RuntimeException("Book's authors cannot be empty");
        } else {
            Employee savedBook = this.employeeDAO.createEmployee(employee);
            return savedBook;
        }
    }
}
