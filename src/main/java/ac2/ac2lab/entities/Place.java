package ac2.ac2lab.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ac2.ac2lab.dto.PlaceInsertDTO;


@Entity
@Table(name = "TB_PLACES")
public class Place implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "TB_EVENTS_PLACES",
        joinColumns = @JoinColumn(name = "PLACE_ID"),
        inverseJoinColumns = @JoinColumn(name = "EVENT_ID")
    )
    private List<Event> events = new ArrayList<>();

    public Place() {
    }

    public Place(Long id, String name, String address, List<Event> events) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.events = events;
    }

    public Place(PlaceInsertDTO dto) {
        this.name = dto.getName();
        this.address = dto.getAddress();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvents(Event event) {
        this.events.add(event);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Place other = (Place) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }  
}