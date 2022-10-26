package org.example.service;

import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAO;

public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    @Override
    public Employee createEmployee(Employee employee) {
        if (employee.getUsername().length() == 0) {
            throw new RuntimeException("username cannot be empty");
        } else if (employee.getPassword().length() == 0) {
            throw new RuntimeException("password cannot be empty");
        } else {
            Employee savedEmployee = this.employeeDAO.createEmployee(employee);
            return savedEmployee;
        }
    }

    @Override
    public Employee login(Employee var1) {
        return null;
    }


    @Override
    public String updateAdminPrivilege(Employee employee) {
        return null;
    }


    @Override
    public String updateEmployeePicture(int id, byte[] array) {
        return null;
    }

    @Override
    public String updateEmployeeinfo(Employee employee) {
        return null;
    }
}
