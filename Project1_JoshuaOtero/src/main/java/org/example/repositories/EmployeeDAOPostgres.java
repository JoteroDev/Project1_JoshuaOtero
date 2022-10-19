package org.example.repositories;

import org.example.entities.Employee;
import org.example.entities.Ticket;
import org.example.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAOPostgres implements EmployeeDAO{
    @Override
    public Employee createEmployee(Employee employee) {
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "insert into employee values (default, ?, ?, NULL, NULL, NULL)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getUsername());
            preparedStatement.setString(2, employee.getPassword());

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
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "insert into ticket values (default, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ticket.getUser());
            preparedStatement.setString(2, ticket.getAmount());
            preparedStatement.setString(3, ticket.getDescription());
            preparedStatement.setString(4, ticket.getStatus());
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
                ticket.setUser(rs.getString("User"));
                ticket.setAmount(rs.getString("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(rs.getString("status"));
                ticket.setType(rs.getString("type"));
                tickets.add(ticket);
            }

//            Ticket ticket = new Ticket();
//            ticket.setId(rs.getInt("TicketId"));
//            ticket.setUser(rs.getString("User"));
//            ticket.setAmount(rs.getString("amount"));
//            ticket.setDescription(rs.getString("description"));
//            ticket.setStatus(rs.getString("status"));
//            ticket.setType(rs.getString("type"));
            return tickets;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
