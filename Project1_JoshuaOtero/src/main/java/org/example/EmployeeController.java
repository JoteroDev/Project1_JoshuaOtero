package org.example;

import com.google.gson.Gson;
import io.javalin.http.Handler;
import org.example.entities.Employee;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAOPostgres;
import java.util.ArrayList;

public class EmployeeController {

    public EmployeeController() {

    }
    public Handler createTicket = (ctx) -> {
        if (ctx == null) {
        }
        String json = ctx.body();
        Gson gson = new Gson();
        Ticket ticket = (Ticket) gson.fromJson(json, Ticket.class);
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        Ticket registeredTicket = employeeDAOPostgres.createTicket(ticket);
        if (registeredTicket == null){
            ctx.status(401);
            ctx.result("Please make sure you are logged in!");
        } else {
            ctx.status(201);
            ctx.result(registeredTicket.toString());
        }

    };

    public Handler createEmployee = (ctx) -> {
        if (ctx == null) {
        }
        String json = ctx.body();
        Gson gson = new Gson();
        Employee employee = (Employee) gson.fromJson(json, Employee.class);
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        Employee newEmployee = employeeDAOPostgres.createEmployee(employee);
        if (newEmployee == null) {
            ctx.status(401);
            ctx.result("Username is taken!");
        } else {
            ctx.status(201);
            ctx.result(newEmployee.toString());
        }
    };

    public Handler login = (ctx) -> {
        if (ctx == null) {
        }
        String json = ctx.body();
        Gson gson = new Gson();
        Employee employee = (Employee) gson.fromJson(json, Employee.class);
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        ArrayList<Ticket> newTicket = employeeDAOPostgres.login(employee);
        String jsonString = "";
        if (newTicket == null){
            jsonString = "Invalid username or password";
            ctx.status(401);
            ctx.result(jsonString);
        } else if(newTicket.size() == 0) {
            //Main.currentLoggedEmployee = employee;
            ctx.status(401);
            ctx.result("No tickets");
            } else {
            //Main.currentLoggedEmployee = employee;
            for (int i = 0; i < newTicket.size(); i++){
                jsonString += newTicket.get(i).toString() + "\n\r";
            }
            ctx.status(201);
            ctx.result(jsonString);
        }
    };

    public Handler viewTickets = (ctx) -> {
        if (ctx == null) {
        }
        String json = ctx.body();
        Gson gson = new Gson();
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        String tickets = employeeDAOPostgres.employeeGetTickets();
        if (tickets.equals("Not logged in!")){
            ctx.status(401);
        } else{
            ctx.status(201);
        }
        ctx.result(tickets);
    };

    public Handler updateTicket = (ctx) -> {
        if (ctx == null) {
        }
        String json = ctx.body();
        Gson gson = new Gson();
        Ticket ticket = (Ticket) gson.fromJson(json, Ticket.class);
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        String newEmployee = employeeDAOPostgres.changeStatus(ticket.getId(), ticket.getStatus());
        if (newEmployee == null) {
            ctx.status(401);
            ctx.result("Username is taken!");
        } else {
            ctx.status(201);
            ctx.result(newEmployee.toString());
        }
    };

}
