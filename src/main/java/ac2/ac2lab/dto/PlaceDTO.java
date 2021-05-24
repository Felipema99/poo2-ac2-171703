package ac2.ac2lab.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ac2.ac2lab.entities.Event;
import ac2.ac2lab.entities.Place;

public class PlaceDTO {
    
    private Long id;
    private String name;
    private String address;

    @JsonIgnore
    private List<Event> events;

    public PlaceDTO(Long id, String name, String address, List<Event> events){
        this.id = id;
        this.name = name;
        this.address = address;
        this.events = events;
    }

    public PlaceDTO(Place plc){
        this.id = plc.getId();
        this.name = plc.getName();
        this.address = plc.getAddress();
        this.events = plc.getEvents();
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

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    
    
}
