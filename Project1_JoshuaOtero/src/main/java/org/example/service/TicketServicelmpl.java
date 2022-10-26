package org.example.service;

import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAO;

public class TicketServicelmpl implements TicketService{

    private EmployeeDAO employeeDAO;

    public TicketServicelmpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    @Override
    public Ticket createTicket(Ticket ticket ) {
        if (ticket.getDescription().length() == 0) {
            throw new RuntimeException("description cannot be empty");
        } else if (ticket.getAmount().length() == 0) {
            throw new RuntimeException("amount cannot be empty");
        } else {
            Ticket savedTicket = this.employeeDAO.createTicket(ticket);
            return savedTicket;
        }
    }

    @Override
    public String changeStatus(int ticketID, Status status) {
        if (ticketID <= 0) {
            throw new RuntimeException("Ticket number cannot be 0 or lower.");
        } else if (status.equals(Status.PENDING)) {
            throw new RuntimeException("Cannot change a ticket to pending.");
        } else {
            String savedTicket = this.employeeDAO.changeStatus(ticketID, status);
            return savedTicket;
        }
    }

    @Override
    public String readTicketsbyType(String type) {
        if (type == null) {
            throw new RuntimeException("type cannot be empty");
        } else if(type.equals("")){
            throw new RuntimeException("type cannot be empty");
        }
        else {
            String savedType = this.employeeDAO.readTicketsbyType(type);
            return savedType;
        }
    }

    @Override
    public String checkIfTicketExistsbyID(int id) {
        return null;
    }

    @Override
    public String updateTicketPicture(int id, byte[] array) {
        return null;
    }
}
