package org.example.repositories;

import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;

import java.util.ArrayList;

public interface EmployeeDAO {
    Employee createEmployee(Employee employee);

    int deleteEmployee(Employee employee);

    ArrayList<Ticket> login(Employee employee);

    String employeeGetTickets();

    String updateAdminPrivilege(Employee employee);

    String updateEmployeePicture(int id, byte[] array);

    String updateEmployeeinfo(Employee employee);
}
