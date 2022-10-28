package org.example.repositories;

import org.example.Main;
import org.example.entities.Status;
import org.example.entities.Ticket;
import org.example.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;

public class TicketDAOPostgres implements TicketDAO {
    @Override
    public Ticket createTicket(Ticket ticket) {
        if (Main.currentLoggedEmployee == null){
            return null;
        }
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "insert into ticket values (default, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, Main.currentLoggedEmployee.getUsername());
            preparedStatement.setString(2, ticket.getAmount());
            preparedStatement.setString(3, ticket.getDescription());
            preparedStatement.setString(4, ticket.getStatus().name());
            preparedStatement.setString(5, ticket.getType());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys(); //Returns the id that was created
            resultSet.next(); //you need to move the cursor to the first valid record, or you will get a null response.
            int generatedKey = resultSet.getInt("Ticketid");
            ticket.setId(generatedKey);
            return ticket;
        } catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Ticket> getTicketbyStatus(String status) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "select * from ticket where Status = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,status);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("TicketId"));
                ticket.setUser(rs.getString("username"));
                ticket.setAmount(rs.getString("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(ticket.getStatus().valueOf(rs.getString("status")));
                ticket.setType(rs.getString("type"));
                tickets.add(ticket);
            }
            return tickets;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateTicketStatus(int ticketID, Status status) {
        if (Main.currentLoggedEmployee == null){
            return "Not logged in!";
        } else if(!Main.currentLoggedEmployee.isAdmin()){
            return "You don't have permission to edit these!";
        }
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "select * from ticket where ticketid = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, ticketID);
            ResultSet resultSets = preparedStatement.executeQuery();
            resultSets.next();
            Ticket ticket = new Ticket();
            ticket.setId(resultSets.getInt("TicketId"));
            ticket.setStatus(ticket.getStatus().valueOf(resultSets.getString("status")));
            if (ticket.getStatus().equals(Status.APPROVED)){
                return "This ticket has already been approved and cannot been changed.";
            }
            if (ticket.getStatus().equals(Status.DENIED)){
                return "This ticket has already been denied and cannot been changed.";
            }
            sql = "update ticket set status = ? where ticketid = ?";
            preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, ticketID);

            int rs = preparedStatement.executeUpdate();
            if (rs == 0){
                return "Failed! Change did not go through!\r\nPlease verify the ticket id and status is correct!";
            } else {
                return "Success! Ticket #" + ticketID + " has been updated to " + status;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String readTicketsbyType(String type) {
        if (Main.currentLoggedEmployee == null){
            return "Not logged in!";
        }
        ArrayList<Ticket> tickets = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()){
            if (Main.currentLoggedEmployee.isAdmin()){
                String sql = "select * from ticket where type = ? and status = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,type);
                ps.setString(2,"PENDING");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Ticket ticket = new Ticket();
                    ticket.setId(rs.getInt("TicketId"));
                    ticket.setUser(rs.getString("username"));
                    ticket.setAmount(rs.getString("amount"));
                    ticket.setDescription(rs.getString("description"));
                    ticket.setStatus(ticket.getStatus().valueOf(rs.getString("status")));
                    ticket.setType(rs.getString("type"));
                    tickets.add(ticket);
                }
                String jsonString = "";
                for (int i = 0; i < tickets.size(); i++){
                    jsonString += tickets.get(i).toString() + "\n\r";
                }
                return jsonString;
            } else {
                String sql = "select * from ticket where type = ? and username = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,type);
                ps.setString(2,Main.currentLoggedEmployee.getUsername());
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Ticket ticket = new Ticket();
                    ticket.setId(rs.getInt("TicketId"));
                    ticket.setUser(rs.getString("username"));
                    ticket.setAmount(rs.getString("amount"));
                    ticket.setDescription(rs.getString("description"));
                    ticket.setStatus(ticket.getStatus().valueOf(rs.getString("status")));
                    ticket.setType(rs.getString("type"));
                    tickets.add(ticket);
                }
                String jsonString = "";
                for (int i = 0; i < tickets.size(); i++){
                    jsonString += tickets.get(i).toString() + "\n\r";
                }
                return jsonString;
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String checkIfTicketExistsbyID(int id) {
        if (Main.currentLoggedEmployee == null){
            return "Not logged in!";
        }
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "select * from ticket where ticketid = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSets = preparedStatement.executeQuery();
            resultSets.next();
            Ticket ticket = new Ticket();
            ticket.setStatus(ticket.getStatus().valueOf(resultSets.getString("status")));
            if (!Main.currentLoggedEmployee.getUsername().equals(resultSets.getString("username"))){
                return "You cannot upload images for ticket's that don't belong to you.";
            } else if (!ticket.getStatus().equals(Status.PENDING)) {
                return "This ticket was already approved or denied!";
            } else{
                return "valid";
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateTicketPicture(int id, byte[] array) {
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "update ticket set image = ? where ticketid = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setBytes(1, array);
            preparedStatement.setInt(2, id);
            int rs = preparedStatement.executeUpdate();
            if (rs == 0){
                return "Failed! Change did not go through!\r\nPlease make sure id# is a valid ticket!";
            } else {
                return "Success! Picture was uploaded to ticket #"+id;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int deleteTicketsbyUsername(String username) {
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "delete from ticket where username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys(); //Returns the id that was created
            resultSet.next(); //you need to move the cursor to the first valid record, or you will get a null response.
            return 0;
        } catch(SQLException|NullPointerException e){
            e.printStackTrace();
        }
        return 0;
    }

}
