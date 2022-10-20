package org.example.repositories;

import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;

import java.util.ArrayList;
import java.util.List;

public interface EmployeeDAO {
    Employee createEmployee(Employee employee);

    Ticket createTicket(Ticket ticket);

    ArrayList<Ticket> getTicketbyStatus(String status);

    ArrayList<Ticket> login(Employee employee);

    String employeeGetTickets();

    String changeStatus(int ticketID, Status status);

    String readTicketsbyType(String type);
}
