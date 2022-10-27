package smoketests;

import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAOPostgres;
import org.example.repositories.EmployeeDAO;
import org.example.service.EmployeeService;
import org.example.service.EmployeeServiceImpl;
import org.example.service.TicketService;
import org.example.service.TicketServicelmpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class RepoTests {
    public RepoTests() {
    }

    EmployeeDAOPostgres employeeDAOMock = mock(EmployeeDAOPostgres.class);
   EmployeeService employeeService = new EmployeeServiceImpl(employeeDAOMock);
    TicketService ticketService =new TicketServicelmpl(employeeDAOMock);

    @Test
    @Order(1)
    void create_employee_test() {
        Employee employee = new Employee();
        employee.setUsername("Joshua");
        employee.setPassword("password");
        employee.setAdmin(true);
        when(employeeDAOMock.createEmployee(employee)).thenReturn(employee);
        Assertions.assertEquals(employeeService.createEmployee(employee), employee);
    }

    @Test
    @Order(2)
    void login_test() {
        Employee employee = new Employee();
        employee.setUsername("Joshua");
        employee.setPassword("password");
        ArrayList<Ticket> list = new ArrayList<>();
        list.add(new Ticket("Joshua", "2000", "Moving to NY", "Lodging"));
        when(employeeDAOMock.login(employee)).thenReturn(list);
        Assertions.assertNotNull(employeeService.login(employee));
    }
    @Test
    @Order(3)
    void create_ticket_test() {
        Ticket ticket = new Ticket();
        ticket.setAmount("20000");
        ticket.setDescription("Moving to NY");
        when(employeeDAOMock.createTicket(ticket)).thenReturn(ticket);
        Assertions.assertNotNull(ticketService.createTicket(ticket));
    }

    @Test
    @Order(4)
    void update_admin_test() {
        Employee employee = new Employee();
        employee.setUsername("Joshua");
        employee.setPassword("password");
        employee.setId(1);
        employee.setAdmin(true);
        when(employeeDAOMock.updateAdminPrivilege(employee)).thenReturn("Success! Employee #" + employee.getId() + " has been updated to Admin!");
        Assertions.assertEquals(employeeService.updateAdminPrivilege(employee), "Success! Employee #" + employee.getId() + " has been updated to Admin!");
    }

    @Test
    @Order(5)
    void update_employee_info_test() {
        Employee employee = new Employee();
        employee.setUsername("Joshua");
        employee.setPassword("password");
        employee.setId(1);
        employee.setAdmin(true);
        when(employeeDAOMock.updateEmployeeinfo(employee)).thenReturn("Success! Employee #"+employee.getId() + " was updated!");
        employee.setPassword("password2");
        employee.setPhoneNumber("2012223333");
        Assertions.assertEquals(employeeService.updateEmployeeinfo(employee), "Success! Employee #"+employee.getId() + " was updated!");
    }

    @Test
    @Order(6)
    void change_ticket_status_test() {
        Ticket ticket = new Ticket();
        ticket.setStatus(Status.APPROVED);
        ticket.setId(1);
        when(employeeDAOMock.updateTicketStatus(1, Status.APPROVED)).thenReturn("Success! Ticket #1 has been updated to " + Status.APPROVED);
        Assertions.assertEquals(ticketService.changeStatus(ticket.getId(), ticket.getStatus()), "Success! Ticket #1 has been updated to " + Status.APPROVED);
    }



    @Test
    @Order(7)
    void change_ticket_status_fail_test() {
        Ticket ticket = new Ticket();
        ticket.setStatus(Status.PENDING);
        ticket.setId(1);
        when(employeeDAOMock.updateTicketStatus(1, Status.APPROVED)).thenReturn("Success! Ticket #1 has been updated to " + Status.APPROVED);
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Assertions.assertEquals(ticketService.changeStatus(ticket.getId(), ticket.getStatus()), "Success! Ticket #1 has been updated to " + Status.APPROVED);
            }
        });


    }




}