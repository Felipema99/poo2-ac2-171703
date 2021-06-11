package ac2.ac2lab.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ac2.ac2lab.dto.EventDTO;
import ac2.ac2lab.dto.EventInsertDTO;
import ac2.ac2lab.dto.EventUpdateDTO;
import ac2.ac2lab.services.EventService;
import ac2.ac2lab.entities.Attend;
import ac2.ac2lab.entities.View;
import ac2.ac2lab.dto.AttendTicketDTO;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getEvents(

        @RequestParam(value = "page",         defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
        @RequestParam(value = "direction",    defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy",      defaultValue = "id") String orderBy,
        @RequestParam(value = "name",         defaultValue = "") String name,
        @RequestParam(value = "description",  defaultValue = "") String description
        
    ) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        Page<EventDTO> list = eventService.getEvents(pageRequest, name, description);
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        EventDTO dto = eventService.getEventById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
	public ResponseEntity<EventDTO> insert(@RequestBody EventInsertDTO insertDTO){
		EventDTO dto = eventService.insert(insertDTO); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		eventService.delete(id); 
		return ResponseEntity.noContent().build();
	}
    
    @PutMapping("{id}")
	public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventUpdateDTO updateDTO){
		EventDTO dto = eventService.update(id, updateDTO); 
		return ResponseEntity.ok().body(dto);
	}
}

@PostMapping("{id}/places/{place}")
    public ResponseEntity<EventDTO> addPlace(@PathVariable Long id, @PathVariable Long place) {
        EventDTO dto = eventService.addPlace(id, place);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{id}/places/{place}")
	public ResponseEntity<Void> deletePlace(@PathVariable Long id, @PathVariable Long place){
		eventService.removePlace(id, place); 
		return ResponseEntity.noContent().build();
	}

    @GetMapping("{id}/tickets")
    public ResponseEntity<View> getTickets(@PathVariable Long id) {
        View view = eventService.getTickets(id);
        return ResponseEntity.ok(view);
    }

    @PostMapping("{id}/tickets")
    public ResponseEntity<Attend> insertTicket(@RequestBody AttendTicketDTO ticketDTO, @PathVariable Long id) {
        Attend atd = eventService.insertTicket(ticketDTO, id);
        return ResponseEntity.ok(atd);
    }

    @DeleteMapping("{id}/tickets")
	public ResponseEntity<Void> deleteTickets(@PathVariable Long id){
		eventService.deleteTickets(id); 
		return ResponseEntity.noContent().build();
	}

}
