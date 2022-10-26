package org.example.service;

import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;

public interface TicketService {
    Ticket createTicket(Ticket var1);

    String changeStatus(int ticketID, Status status);

    String readTicketsbyType(String type);

    String checkIfTicketExistsbyID(int id);

    String updateTicketPicture(int id, byte[] array);
}
