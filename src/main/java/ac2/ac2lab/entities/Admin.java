package ac2.ac2lab.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ac2.ac2lab.dto.AdminInsertDTO;

@Entity
@Table(name = "TB_ADMINS")
@PrimaryKeyJoinColumn(name = "USER_ID")
public class Admin extends BaseUser{

    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private List<Event> events = new ArrayList<>();

    public Admin() {
     
	}

    public Admin(AdminInsertDTO dto) {
        this.setName(dto.getName());
        this.setEmail(dto.getEmail());
        this.phoneNumber = dto.getPhoneNumber();
	}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }
}
