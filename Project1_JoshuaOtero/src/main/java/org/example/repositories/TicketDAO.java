package org.example.repositories;

import org.example.entities.Status;
import org.example.entities.Ticket;

import java.util.ArrayList;

public interface TicketDAO {

    Ticket createTicket(Ticket ticket);

    ArrayList<Ticket> getTicketbyStatus(String status);

    String updateTicketStatus(int ticketID, Status status);

    String readTicketsbyType(String type);

    String checkIfTicketExistsbyID(int id);

    String updateTicketPicture(int id, byte[] array);

    int deleteTicketsbyUsername(String username);

}
