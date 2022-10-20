package org.example;

import io.javalin.Javalin;
import org.example.entities.Employee;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAO;
import org.example.repositories.EmployeeDAOPostgres;
import org.example.repositories.EmplyeeDAOLocal;
import org.example.utils.ConnectionFactory;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static EmployeeDAO employeeDAO = new EmplyeeDAOLocal();
    public static Employee currentLoggedEmployee;
    public static void main(String[] args) {
        Javalin app = Javalin.create();
        TestHandler helloHandler = new TestHandler();
        app.post("/createEmployee", helloHandler.createEmployee);
        app.post("/createTicket", helloHandler.createTicket);
        app.post("/login", helloHandler.login);
        app.get("/getTickets", helloHandler.viewTickets);
        app.start();
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        // Employee employee = new Employee("Joshua Otero", "password");
        //if (employeeDAOPostgres.login(employee) == null){
        //    System.out.println("User doesn't exist");
       //}
        //Ticket ticket = new Ticket(employee.getUsername(), "10000", "Moving from florida to NJ", "Lodging");
        //employeeDAOPostgres.createEmployee(employee);
        //employeeDAOPostgres.createTicket(ticket);
        //ArrayList newTicket = employeeDAOPostgres.getTicketbyStatus("pending");
        //System.out.println(newTicket.size());
//        for (int i=0; i < newTicket.size(); i++){
//            System.out.println(newTicket.get(i).toString());
//        }

    }
}