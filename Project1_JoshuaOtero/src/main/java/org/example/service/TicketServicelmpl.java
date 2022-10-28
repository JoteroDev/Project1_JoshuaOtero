package org.example.service;

import org.example.entities.Status;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAO;
import org.example.repositories.TicketDAO;

public class TicketServicelmpl implements TicketService{

    private TicketDAO ticketDAO;

    public TicketServicelmpl(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }
    @Override
    public Ticket createTicket(Ticket ticket ) {
//        if (ticket.getDescription().length() == 0) {
//            throw new RuntimeException("description cannot be empty");
//        } else if (ticket.getDescription().length() > 100) {
//            throw new RuntimeException("Description was too long!");
//        }else if (ticket.getAmount().length() == 0) {
//            throw new RuntimeException("amount cannot be empty");
//        }else if (ticket.getAmount().length() > 10) {
//            throw new RuntimeException("amount was too long!");
//        } else {
//
//        }
        Ticket savedTicket = this.ticketDAO.createTicket(ticket);
        return savedTicket;
    }

    @Override
    public String changeStatus(int ticketID, Status status) {
        if (ticketID <= 0) {
            throw new RuntimeException("Ticket number cannot be 0 or lower.");
        } else if (status.equals(Status.PENDING)) {
            throw new RuntimeException("Cannot change a ticket to pending.");
        } else {
            String savedTicket = this.ticketDAO.updateTicketStatus(ticketID, status);
            return savedTicket;
        }
    }

    @Override
    public String readTicketsbyType(String type) {
        if (type == null) {
            throw new RuntimeException("type cannot be empty");
        } else if(type.trim().equals("")){
            throw new RuntimeException("type cannot be empty");
        }
        else {
            String savedType = this.ticketDAO.readTicketsbyType(type);
            return savedType;
        }
    }

    @Override
    public String checkIfTicketExistsbyID(int id) {
        if (id <= 0) {
            throw new RuntimeException("Ticket number cannot be 0 or lower.");
        } else {
            String savedTicket = this.ticketDAO.checkIfTicketExistsbyID(id);
            return savedTicket;
        }
    }

    @Override
    public String updateTicketPicture(int id, byte[] array) {
        if (id <= 0) {
            throw new RuntimeException("Ticket number cannot be 0 or lower.");
        } else if(array.length == 0) {
            throw new RuntimeException("No picture was sent.");
        }else {
            String savedTicket = this.ticketDAO.updateTicketPicture(id, array);
            return savedTicket;
        }
    }
}
