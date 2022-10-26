package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Handler;
import org.example.Main;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAOPostgres;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

public class TicketController {
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
            ctx.result("Please make sure you are logged in and have entered an Amount, Description, and Type");
        } else {
            ctx.status(201);
            ctx.result("Ticket #"+registeredTicket.getId()+ " has been registered!");
        }

    };

    public Handler readTicketbyType = (ctx) -> {
        if (ctx == null) {
        }
        String json = ctx.body();
        Gson gson = new Gson();
        Ticket ticket = (Ticket) gson.fromJson(json, Ticket.class);
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        String newTicket = employeeDAOPostgres.readTicketsbyType(ticket.getType());
        String jsonString = "";
        if (newTicket.equals("Not logged in!")){
            jsonString = "Please login first!";
            ctx.status(401);
            ctx.result(jsonString);
        } else if(newTicket.equals("")) {
            ctx.status(404);
            ctx.result("No tickets exist with type " + ticket.getType()+".\n\rRemember this input is case-sensitive.");
        } else {
            ctx.status(200);
            ctx.result(newTicket);
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
            ctx.status(200);
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
        String ticketString = Main.ticketService.changeStatus(ticket.getId(), ticket.getStatus());
        switch (ticketString){
            case"This ticket has already been approved and cannot been changed.":
            case"This ticket has already been denied and cannot been changed.":
                ctx.status(401);
                break;
            case "Failed! Change did not go through!\r\nPlease verify the ticket id and status is correct!":
                ctx.status(400);
                break;
            default:
                ctx.status(202);
        }
        ctx.result(ticketString.toString());
    };

    public Handler updateTicketPicture = (ctx) -> {
        if (ctx == null) {
        }
        int id = Integer.parseInt(ctx.pathParam("id"));
        EmployeeDAOPostgres employeeDAOPostgres = new EmployeeDAOPostgres();
        String ticketString = employeeDAOPostgres.checkIfTicketExistsbyID(id);
        byte[] array = ctx.bodyAsBytes();
        if(ticketString == null){
            ctx.status(400);
            ctx.result("Not a valid ticket number!");
        } else {
            switch (ticketString){
                case "You cannot upload images for ticket's that don't belong to you.":
                case "Not logged in!":
                case "This ticket was already approved or denied!":
                    ctx.status(400);
                    ctx.result(ticketString);
                    break;
                default:
                    if (array == null){
                        ctx.status(400);
                        ctx.result("No file was detected!");
                    } else {
                        ctx.status(200);
                        ticketString = employeeDAOPostgres.updateTicketPicture(id, array);
                        // These lines of code create the image and put it into the resources file.
//                        System.out.println(array);
//                        ByteArrayInputStream bis = new ByteArrayInputStream(array);
//                        BufferedImage bImage = ImageIO.read(bis);
//                        ImageIO.write(bImage, "jpg", new File("ticketExample.jpg"));
                        String HTML_FORMAT = "<img src=\"data:image/jpeg;base64,%1$s\" />";
                        String b64Image = Base64.toBase64String(array);
                        String html = String.format(HTML_FORMAT, b64Image);
                        ctx.result(html);
                    }

            }
        }


    };
}
