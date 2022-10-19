package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    String username;
    String password;
    String name;
    String address;
    String phoneNumber;
    List<Ticket> pendingTickets = new ArrayList<>();

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Employee(String username, String password, String name, String address, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Ticket> getPendingTickets() {
        return pendingTickets;
    }

    public void setPendingTickets(List<Ticket> pendingTickets) {
        this.pendingTickets = pendingTickets;
    }
}
