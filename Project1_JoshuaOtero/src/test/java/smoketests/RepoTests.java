package smoketests;

import org.eclipse.jetty.util.DateCache;
import org.example.Main;
import org.example.entities.Employee;
import org.example.entities.Status;
import org.example.entities.Ticket;
import org.example.repositories.EmployeeDAOPostgres;
import org.example.repositories.TicketDAOPostgres;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepoTests {
    public RepoTests() {
    }

    EmployeeDAOPostgres employeeDAOMock = new EmployeeDAOPostgres();
    TicketDAOPostgres ticketDAOMock = new TicketDAOPostgres();

    @Test
    @Order(1)
    void create_employee_test() {
        Employee employee = new Employee();
        employee.setUsername("Test1");
        employee.setPassword("password");
        employeeDAOMock.deleteEmployee(employee);
        Employee newEmployee = employeeDAOMock.createEmployee(employee);
        Assertions.assertEquals(newEmployee.getUsername(), employee.getUsername());
    }

    @Test
    @Order(2)
    void create_admin_test() {
        Employee employee = new Employee();
        employee.setUsername("Admin1");
        employee.setPassword("password");
        employee.setAdmin(true);
        employeeDAOMock.deleteEmployee(employee);
        Employee newEmployee = employeeDAOMock.createEmployee(employee);
        Assertions.assertEquals(newEmployee.isAdmin(), employee.isAdmin());
    }

    @Test
    @Order(3)
    void create_duplicate_test() {
        Employee employee = new Employee();
        employee.setUsername("Admin1");
        employee.setPassword("password");
        employee.setAdmin(true);
        Employee newEmployee = employeeDAOMock.createEmployee(employee);
        Assertions.assertNull(newEmployee);
    }

    @Test
    @Order(4)
    void login_test() {
        Employee employee = new Employee();
        employee.setUsername("Test1");
        employee.setPassword("password");
        employeeDAOMock.login(employee);
        Assertions.assertEquals(Main.currentLoggedEmployee.getUsername(), employee.getUsername());
    }
    @Test
    @Order(5)
    void create_ticket_test() {
        Ticket ticket = new Ticket();
        ticket.setAmount("20000");
        ticket.setDescription("Moving to NY");
        ticket.setType("Travel");
        Ticket newTicket = ticketDAOMock.createTicket(ticket);
        Assertions.assertEquals(newTicket.getStatus(), Status.PENDING);
    }

    @Test
    @Order(6)
    void create_bad_ticket_test() {
        Ticket ticket = new Ticket();
        ticket.setDescription("Moving to NY");
        ticket.setType("Travel");
        Ticket newTicket = ticketDAOMock.createTicket(ticket);
        Assertions.assertNull(newTicket);

    }

    @Test
    @Order(7)
    void admin_pending_tickets_test() {
        Employee employee = new Employee();
        employee.setUsername("Admin1");
        employee.setPassword("password");
        employeeDAOMock.login(employee);
        String tickets = employeeDAOMock.employeeGetTickets();
        Assertions.assertTrue(tickets.length() > 100);
    }

    @Test
    @Order(8)
    void approve_ticket_test() {
        Employee employee = new Employee();
        employee.setUsername("Admin1");
        employee.setPassword("password");
        employeeDAOMock.login(employee);
        Ticket ticket = new Ticket();
        ticket.setAmount("20000");
        ticket.setDescription("Moving to NY");
        ticket.setType("Travel");
        Ticket newTicket = ticketDAOMock.createTicket(ticket);
        String approval = ticketDAOMock.updateTicketStatus(newTicket.getId(), Status.APPROVED);
        Assertions.assertEquals(approval, "Success! Ticket #" + newTicket.getId() + " has been updated to " + Status.APPROVED);
    }

    @Test
    @Order(9)
    void employee_approve_ticket_test() {
        Employee employee = new Employee();
        employee.setUsername("Test1");
        employee.setPassword("password");
        employeeDAOMock.login(employee);
        Ticket ticket = new Ticket();
        ticket.setAmount("20000");
        ticket.setDescription("Moving to NY");
        ticket.setType("Travel");
        Ticket newTicket = ticketDAOMock.createTicket(ticket);
        String denial = ticketDAOMock.updateTicketStatus(newTicket.getId(), Status.APPROVED);
        Assertions.assertEquals(denial, "You don't have permission to edit these!");
    }



    @Test
    @Order(10)
    void change_ticket_status_fail_test() {
        Employee employee = new Employee();
        employee.setUsername("Test1");
        employee.setPassword("password");
        employeeDAOMock.login(employee);
        String tickets = employeeDAOMock.employeeGetTickets();
        Assertions.assertTrue(tickets.length() > 100);
    }

    @Test
    @Order(11)
    void cleanup_test_tickets(){
        ticketDAOMock.deleteTicketsbyUsername("Test1");
        ticketDAOMock.deleteTicketsbyUsername("Admin1");
        Employee employee = new Employee();
        employee.setUsername("Test1");
        employee.setPassword("password");
        employeeDAOMock.login(employee);
        String tickets = employeeDAOMock.employeeGetTickets();
        Assertions.assertTrue(tickets.equals(""));

    }




}
