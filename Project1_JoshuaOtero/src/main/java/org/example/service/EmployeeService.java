package org.example.service;

import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;

public interface EmployeeService {

    Employee createEmployee(Employee var1);

    Employee login(Employee var1);

    String updateAdminPrivilege(Employee employee);

    String updateEmployeePicture(int id, byte[] array);

    String updateEmployeeinfo(Employee employee);
}
