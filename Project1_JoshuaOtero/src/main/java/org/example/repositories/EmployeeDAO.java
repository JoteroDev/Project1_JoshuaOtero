package org.example.repositories;

import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;

import java.util.ArrayList;

public interface EmployeeDAO {
    Employee createEmployee(Employee employee);

    int deleteEmployee(Employee employee);

    Ticket createTicket(Ticket ticket);

    ArrayList<Ticket> getTicketbyStatus(String status);

    ArrayList<Ticket> login(Employee employee);

    String employeeGetTickets();

    String updateTicketStatus(int ticketID, Status status);

    String readTicketsbyType(String type);

    String updateAdminPrivilege(Employee employee);

    String checkIfTicketExistsbyID(int id);

    String updateTicketPicture(int id, byte[] array);

    String updateEmployeePicture(int id, byte[] array);

    String updateEmployeeinfo(Employee employee);
}
