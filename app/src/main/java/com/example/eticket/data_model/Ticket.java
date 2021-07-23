package com.example.eticket.data_model;

public class Ticket {
    String ticketNo;
    String busName;
    String price;
    String ticketTo;
    String ticketFrom;
    String ticketTime;



    public Ticket() {

    }
    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTicketTo() {
        return ticketTo;
    }

    public void setTicketTo(String ticketTo) {
        this.ticketTo = ticketTo;
    }

    public String getTicketFrom() {
        return ticketFrom;
    }

    public void setTicketFrom(String ticketFrom) {
        this.ticketFrom = ticketFrom;
    }
}
