package ac2.ac2lab.dto;

import java.util.List;

import ac2.ac2lab.entities.Attend;
import ac2.ac2lab.entities.Ticket;

public class AttendDTO {
    
    private Long id;
    private String name;
    private String email;
    private Double balance;
    private List<Ticket> tickets;

    public AttendDTO(Long id, String name, String email, Double balance, List<Ticket> tickets){
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.tickets = tickets;
    }

    public AttendDTO(Attend atd){
        this.id = atd.getId();
        this.name = atd.getName();
        this.email = atd.getEmail();
        this.balance = atd.getBalance();
        this.tickets = atd.getTickets();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    
}
