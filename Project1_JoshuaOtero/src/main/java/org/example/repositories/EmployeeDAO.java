package org.example.repositories;

import org.example.entities.Employee;
import org.example.entities.Ticket;

import java.util.ArrayList;
import java.util.List;

public interface EmployeeDAO {
    Employee createEmployee(Employee employee);

    Ticket createTicket(Ticket ticket);

    ArrayList<Ticket> getTicketbyStatus(String status);

    //List<Book> getAllBooks();

   //Book updateBook(Book var1);

    //boolean deleteBookById(int var1);
}
