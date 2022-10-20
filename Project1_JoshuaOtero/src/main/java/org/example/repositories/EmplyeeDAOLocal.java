package org.example.repositories;

import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//public class EmplyeeDAOLocal implements EmployeeDAO{
//    private Map<Integer, Employee> employeeTable = new HashMap();
//    private Map<Integer, Ticket> ticketTable = new HashMap();
//    int idcount = 1;
//    int ticketid = 1;
//
//    public EmplyeeDAOLocal() {
//    }
//
//    @Override
//    public Employee createEmployee(Employee employee) {
//        employee.setId(idcount);
//        idcount++;
//        this.employeeTable.put(employee.getId(), employee);
//        System.out.println(this.employeeTable.values());
//        return employee;
//    }
//
//    @Override
//    public Ticket createTicket(Ticket ticket) {
//        ticket.setId(ticketid);
//        ticketid++;
//        this.ticketTable.put(ticket.getId(), ticket);
//        System.out.println(this.ticketTable.values());
//        return ticket;
//    }
//
//    @Override
//    public ArrayList<Ticket> getTicketbyStatus(String status) {
//        return null;
//    }
//
//    @Override
//    public ArrayList<Ticket> login(Employee employee) {
//        return null;
//    }
//
//    @Override
//    public String employeeGetTickets() {
//        return null;
//    }
//
//    @Override
//    public String changeStatus(int ticketid, Status status) {
//        return null;
//    }
//}
