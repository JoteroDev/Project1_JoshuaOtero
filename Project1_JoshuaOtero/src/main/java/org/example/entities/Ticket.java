package org.example.entities;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

public class Ticket {

    int id;

    String user;
    String amount;
    String description;
    Status status = Status.PENDING;

    String type;

    byte[] image;

    public Ticket() {
    }

    public Ticket(String user, String amount, String description, String type) {
        this.user = user;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.type = type;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        String html;
        if (image != null){
            String HTML_FORMAT = "<img src=\"data:image/jpeg;base64,%1$s\" />";
            String b64Image = Base64.toBase64String(image);
            html = String.format(HTML_FORMAT, b64Image);
        } else {
            html = "No image";
        }

        return "Ticket{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", image='" + html + '\'' +
                '}';
    }
}
