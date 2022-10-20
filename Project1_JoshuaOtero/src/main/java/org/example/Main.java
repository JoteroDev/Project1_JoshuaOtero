package org.example;

import io.javalin.Javalin;
import org.example.entities.Employee;

public class Main {
    public static Employee currentLoggedEmployee;
    public static void main(String[] args) {
        Javalin app = Javalin.create();
        EmployeeController employeeController = new EmployeeController();
        app.post("/createEmployee", employeeController.createEmployee);
        app.post("/createTicket", employeeController.createTicket);
        app.post("/login", employeeController.login);
        app.get("/getTickets", employeeController.viewTickets);
        app.post("/changeStatus", employeeController.updateTicket);
        app.start();


    }
}