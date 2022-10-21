package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Handler;
import org.example.Main;
import org.example.entities.Employee;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAOPostgres;
import java.util.ArrayList;

public class EmployeeController {

    public EmployeeController() {

    }

    public Handler createEmployee = (ctx) -> {
        if (ctx == null) {
        }
        String json = ctx.body();
        Gson gson = new Gson();
        Employee employee = (Employee) gson.fromJson(json, Employee.class);
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        Employee newEmployee = Main.employeeService.createEmployee(employee);
        if (newEmployee == null) {
            ctx.status(401);
            ctx.result("Username is taken or data entered incorrectly!\r\nPlease use the following JSON format\r\n{\r\"username\": \"Desired Username\"\r\n\"password\": \"password\"\r\n}");
        } else {
            ctx.status(201);
            ctx.result("Employee "+newEmployee.getUsername()+" has been created!");
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

    public Handler updateAdmin = (ctx) -> {
        if (ctx == null) {
        }
        String json = ctx.body();
        Gson gson = new Gson();
        Employee employee = (Employee) gson.fromJson(json, Employee.class);
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        String newEmployee = employeeDAOPostgres.updateAdminPrivilege(employee);
        if (newEmployee == null) {
            ctx.status(401);
            ctx.result("Username is taken!");
        } else {
            ctx.status(201);
            ctx.result(newEmployee.toString());
        }
        System.out.println();
    };

}
