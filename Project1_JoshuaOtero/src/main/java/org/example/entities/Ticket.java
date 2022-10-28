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
            html = "<img id=\"ticketImage\" src=\"data:image/jpg;base64, "+b64Image+"\"width=\"150\" height=\"150\"";
        } else {
            html = "No image";
        }
        String statusString = "";
        if (status.equals(Status.APPROVED)){
            statusString = "<p style = \"color:green;\"> "+status+"</p>";
        } else if(status.equals(Status.DENIED)){
            statusString = "<p style = \"color:red;\"> "+status+"</p>";
        } else {
            statusString = "<p style = \"color:#E3B032;\"> "+status+"</p>";
        }

        return "<style>\n" +
                "table, th, td {\n" +
                "  border:1px solid black;\n" +
                "}\n" +
                "</style>" +
                "<table style=\"width:100%\"> " +
                "<tr>\n" +
                "    <th>Ticket#</th>\n" +
                "    <th>User</th>\n" +
                "    <th>Amount</th>\n" +
                "    <th>Description</th>\n" +
                "    <th>Status</th>\n" +
                "    <th>Type</th>\n" +
                "    <th>Picture</th>\n" +
                "  </tr>" +
                "<tr>\n" +
                "    <td><center>"+ id+"\n</center>" +
                "    <td><center>"+ user+"\n</center>" +
                "    <td><center>"+ amount +"</center></td>\n" +
                "    <td><center>"+ description +"</center></td>\n" +
                "    <td><center>"+ statusString +"</center></td>\n" +
                "    <td><center>"+ type +"</center></td>\n" +
                "    <td><center>"+ html +"</center></td>\n" +
                "  </tr>";
    }
}
