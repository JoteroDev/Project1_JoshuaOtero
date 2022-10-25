package org.example;

import io.javalin.Javalin;
import org.example.controllers.EmployeeController;
import org.example.controllers.TicketController;
import org.example.entities.Employee;
import org.example.repositories.EmployeeDAOPostgres;
import org.example.service.EmployeeService;
import org.example.service.EmployeeServiceImpl;

public class Main {
    public static Employee currentLoggedEmployee;

    public static EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDAOPostgres());
    public static void main(String[] args) {
        Javalin app = Javalin.create();
        EmployeeController employeeController = new EmployeeController();
        TicketController ticketController = new TicketController();
        app.post("/createEmployee", employeeController.createEmployee);
        app.post("/createTicket", ticketController.createTicket);
        app.post("/login", employeeController.login);
        app.get("/getTickets", ticketController.viewTickets);
        app.post("/changeStatus", ticketController.updateTicket);
        app.post("/getTicketbyType", ticketController.readTicketbyType);
        app.post("/updateEmployeeAdmin", employeeController.updateAdmin);
        app.post("/picture/{id}", employeeController.updateTicketPicture);
        app.start();


    }
}