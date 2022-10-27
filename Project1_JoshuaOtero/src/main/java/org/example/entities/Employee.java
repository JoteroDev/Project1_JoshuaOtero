package org.example.entities;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    int id;
    String username;
    String password;
    String name;
    String address;
    String phoneNumber;

    boolean isAdmin;

    byte[] image;

    public Employee() {
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        String admin;
        if (isAdmin){
            admin = "Admin";
        } else {
            admin = "Employee";
        }
        String fullNameString;
        if (name == null){
            fullNameString = "No full name entered.";
        } else{
            fullNameString = name;
        }
        String addressString;
        if (address == null){
            addressString = "No address entered.";
        } else{
            addressString = address;
        }
        String phoneNumberString;
        if (phoneNumber == null){
            phoneNumberString = "No phone number entered.";
        } else{
            phoneNumberString = phoneNumber;
        }
        String html;
        if (image != null){
            String HTML_FORMAT = "<img src=\"data:image/jpeg;base64,%1$s\" />";
            String b64Image = Base64.toBase64String(image);
            html = "<img id=\"ticketImage\" src=\"data:image/jpg;base64, "+b64Image+"\"width=\"150\" height=\"150\"";
        } else {
            html = "No image";
        }

        return "<style>\n" +
                "table, th, td {\n" +
                "  border:1px solid black;\n" +
                "}\n" +
                "</style>" +
                "<table style=\"width:100%\"> " +
                "<tr>\n" +
                "    <th>Employee #</th>\n" +
                "    <th>Username</th>\n" +
                "    <th>Password</th>\n" +
                "    <th>Privileges</th>\n" +
                "    <th>Full Name</th>\n" +
                "    <th>Address</th>\n" +
                "    <th>Phone Number</th>\n" +
                "    <th>Picture</th>\n" +
                "  </tr>" +
                "<tr>\n" +
                "    <td><center>"+ id+"\n</center>" +
                "    <td><center>"+ username+"\n</center>" +
                "    <td><center>"+ password +"</center></td>\n" +
                "    <td><center>"+ admin +"</center></td>\n" +
                "    <td><center>"+ fullNameString +"</center></td>\n" +
                "    <td><center>"+ addressString +"</center></td>\n" +
                "    <td><center>"+ phoneNumberString +"</center></td>\n" +
                "    <td><center>"+ html +"</center></td>\n" +
                "  </tr>";
    }
}
