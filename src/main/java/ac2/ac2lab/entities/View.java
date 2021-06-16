package ac2.ac2lab.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class View implements Serializable{
    
    private List<String> listFreeTickets = new ArrayList<>();
    private List<String> listPayedTickets = new ArrayList<>();
    private Long totalFreeTickets;
    private Long totalPayedTickets;
    private int soldFreeTickets;
    private int soldPayedTickets;

    public List<String> getListFreeTickets() {
        return listFreeTickets;
    }

    public void addListFreeTickets(String nameFreeTicket) {
        this.listFreeTickets.add(nameFreeTicket);
    }

    public List<String> getListPayedTickets() {
        return listPayedTickets;
    }

    public void addListPayedTickets(String namePayedTicket) {
        this.listPayedTickets.add(namePayedTicket);
    }

    public Long getTotalFreeTickets() {
        return totalFreeTickets;
    }

    public void setTotalFreeTickets(Long totalFreeTickets) {
        this.totalFreeTickets = totalFreeTickets;
    }

    public Long getTotalPayedTickets() {
        return totalPayedTickets;
    }

    public void setTotalPayedTickets(Long totalPayedTickets) {
        this.totalPayedTickets = totalPayedTickets;
    }

    public int getSoldFreeTickets() {
        return soldFreeTickets;
    }

    public void setSoldFreeTickets(int soldFreeTickets) {
        this.soldFreeTickets = soldFreeTickets;
    }

    public int getSoldPayedTickets() {
        return soldPayedTickets;
    }

    public void setSoldPayedTickets(int soldPayedTickets) {
        this.soldPayedTickets = soldPayedTickets;
    }
}
