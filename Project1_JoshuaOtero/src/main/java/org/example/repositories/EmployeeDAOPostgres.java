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
        } catch(SQLException|NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int deleteEmployee(Employee employee) {
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "delete from employee where username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getUsername());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys(); //Returns the id that was created
            resultSet.next(); //you need to move the cursor to the first valid record, or you will get a null response.
            return 0;
        } catch(SQLException|NullPointerException e){
            e.printStackTrace();
        }
        return 0;
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
            verified.setPassword(rs.getString("password"));
            verified.setPhoneNumber(rs.getString("phone"));
            verified.setAddress(rs.getString("address"));
            verified.setImage(rs.getBytes("image"));
            verified.setAdmin(rs.getBoolean("isadmin"));
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
                    ticket.setImage(rs.getBytes("image"));
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
                    ticket.setImage(rs.getBytes("image"));
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
    public String employeeGetTickets(){
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
                    ticket.setImage(rs.getBytes("image"));
                    System.out.println("ID #" + ticket.getId());
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
                    ticket.setImage(rs.getBytes("image"));
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
    public String updateAdminPrivilege(Employee employee) {
        if (Main.currentLoggedEmployee == null){
            return "Not logged in!";
        } else if(!Main.currentLoggedEmployee.isAdmin()){
            return "You don't have permission to edit these!";
        } else if(employee.getId() == Main.currentLoggedEmployee.getId()){
            return "You cannot modify your own account!";
        }
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "update employee set isadmin = ? where employeeid = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setBoolean(1, employee.isAdmin());
            preparedStatement.setInt(2, employee.getId());
            int rs = preparedStatement.executeUpdate();
            if (rs == 0){
                return "Failed! Change did not go through!\r\nPlease make sure id# is a valid employee!";
            } else {
                if (employee.isAdmin()){
                    return "Success! Employee #" + employee.getId() + " has been updated to Admin!";
                } else {
                    return "Success! Employee #" + employee.getId() + " has been updated to Employee!";
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String updateEmployeePicture(int id, byte[] array) {
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "update employee set image = ? where employeeid = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setBytes(1, array);
            preparedStatement.setInt(2, id);
            int rs = preparedStatement.executeUpdate();
            if (rs == 0){
                return "Failed! Change did not go through!\r\nPlease make sure id# is a valid employee!";
            } else {
                Main.currentLoggedEmployee.setImage(array);
                return "Success! Picture was uploaded to Employee #"+id;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateEmployeeinfo(Employee employee) {
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "update employee set password = ?, name = ?, address = ?, phone = ? where employeeid = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getPassword());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getAddress());
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setInt(5, Main.currentLoggedEmployee.getId());
            int rs = preparedStatement.executeUpdate();
            if (rs == 0){
                return "Failed! Change did not go through!\r\nPlease make sure id# is a valid employee!";
            } else {
                Main.currentLoggedEmployee = employee;
                return "Success! Employee #"+employee.getId() + " was updated!";
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
