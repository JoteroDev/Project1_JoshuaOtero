package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    int id;
    String username;
    String password;
    String name;
    String address;
    String phoneNumber;
    List<Ticket> pendingTickets = new ArrayList<>();

    boolean isAdmin;

    public Employee() {
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }

    public Employee(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
//                ", name='" + name + '\'' +
//                ", address='" + address + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
                ", pendingTickets=" + pendingTickets +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
