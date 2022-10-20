package org.example.repositories;

import org.example.Main;
import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;
import org.example.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAOPostgres implements EmployeeDAO{
    @Override
    public Employee createEmployee(Employee employee) {
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "insert into employee values (default, ?, ?, ?, NULL, NULL, NULL)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getUsername());
            preparedStatement.setString(2, employee.getPassword());
            preparedStatement.setBoolean(3, employee.isAdmin());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys(); //Returns the id that was created
            resultSet.next(); //you need to move the cursor to the first valid record, or you will get a null response.
            int generatedKey = resultSet.getInt("Employeeid");
            employee.setId(generatedKey);
            return employee;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

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
        } catch(SQLException e){
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
    public ArrayList<Ticket> login(Employee employee) {
        try(Connection conn = ConnectionFactory.getConnection()){
            ArrayList<Ticket> tickets = new ArrayList<>();
            String sql = "select * from employee where Username = ? and password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getUsername());
            preparedStatement.setString(2, employee.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            Employee verified = new Employee();
            verified.setId(rs.getInt("employeeid"));
            verified.setUsername(rs.getString("username"));
            verified.setAdmin(rs.getBoolean("isadmin"));
            System.out.println(rs.getBoolean("isadmin"));
            Main.currentLoggedEmployee = verified;
            if (verified.isAdmin()){
                sql = "select * from ticket where Status = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "PENDING");

                rs = ps.executeQuery();
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
            } else {
                sql = "select * from ticket where username = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, employee.getUsername());

                rs = ps.executeQuery();
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
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String employeeGetTickets() {
        if (Main.currentLoggedEmployee == null){
            return "Not logged in!";
        }
        try(Connection conn = ConnectionFactory.getConnection()){
            ArrayList<Ticket> tickets = new ArrayList<>();
            if (Main.currentLoggedEmployee.isAdmin()){
                String sql = "select * from ticket where status = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "PENDING");
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
                String sql = "select * from ticket where username = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, Main.currentLoggedEmployee.getUsername());

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
        return "Failed";
    }

    @Override
    public String changeStatus(int ticketID, Status status) {
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
}
