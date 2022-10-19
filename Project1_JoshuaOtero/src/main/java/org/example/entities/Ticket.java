package org.example.entities;

public class Ticket {
    String amount;
    String description;
    String status;

    public Ticket(String amount, String description) {
        this.amount = amount;
        this.description = description;
        status = "pending";
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
