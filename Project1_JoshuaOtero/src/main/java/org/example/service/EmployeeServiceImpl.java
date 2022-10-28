package org.example.service;

import org.example.Main;
import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAO;

import java.util.ArrayList;

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
    public ArrayList<Ticket> login(Employee employee) {
        if (employee.getUsername().length() == 0) {
            throw new RuntimeException("username cannot be empty");
        } else if (employee.getPassword().length() == 0) {
            throw new RuntimeException("password cannot be empty");
        } else {
            ArrayList<Ticket> savedEmployee = this.employeeDAO.login(employee);
            return savedEmployee;
        }
    }


    @Override
    public String updateAdminPrivilege(Employee employee) {
            String savedEmployee = this.employeeDAO.updateAdminPrivilege(employee);
            return savedEmployee;
    }


    @Override
    public String updateEmployeePicture(int id, byte[] array) {
        if (id <= 0) {
            throw new RuntimeException("Ticket number cannot be 0 or lower.");
        } else if(array.length == 0) {
            throw new RuntimeException("No picture was sent.");
        }else {
            String savedTicket = this.employeeDAO.updateEmployeePicture(id, array);
            return savedTicket;
        }
    }

    @Override
    public String updateEmployeeinfo(Employee employee) {
        String savedTicket = this.employeeDAO.updateEmployeeinfo(employee);
        return savedTicket;
    }
}
