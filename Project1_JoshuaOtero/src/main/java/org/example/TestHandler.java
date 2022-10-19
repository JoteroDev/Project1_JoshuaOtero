package org.example;

import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.entities.Employee;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAOPostgres;
import org.jetbrains.annotations.NotNull;

import javax.xml.ws.handler.MessageContext;
import java.util.ArrayList;

public class TestHandler{

    public TestHandler() {

    }
    public Handler createTicket = (ctx) -> {
        if (ctx == null) {
        }
        String json = ctx.body();
        Gson gson = new Gson();
        Ticket ticket = (Ticket) gson.fromJson(json, Ticket.class);
        Ticket registeredTicket = Main.employeeDAO.createTicket(ticket);
        String ticketJson = gson.toJson(registeredTicket);
        ctx.status(201);
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        ArrayList newTicket = employeeDAOPostgres.getTicketbyStatus("pending");
        String jsonString = "";
        for (int i = 0; i < newTicket.size(); i++){
            jsonString += newTicket.get(i).toString() + "\n\r";
        }
        ctx.result(jsonString);
    };

    public Handler createEmployee = (ctx) -> {
        if (ctx == null) {
        }
        String json = ctx.body();
        Gson gson = new Gson();
        Employee employee = (Employee) gson.fromJson(json, Employee.class);
        Employee registeredEmployee = Main.employeeDAO.createEmployee(employee);
        String employeeJson = gson.toJson(registeredEmployee);
        ctx.status(201);
        ctx.result(employeeJson);
    };

}
