package ac2.ac2lab.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ac2.ac2lab.dto.EventDTO;
import ac2.ac2lab.dto.EventInsertDTO;
import ac2.ac2lab.dto.EventUpdateDTO;
import ac2.ac2lab.entities.Event;
import ac2.ac2lab.repositories.EventRepository;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;

    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description) {
        Page<Event> list = eventRepository.find(pageRequest, name, description);
        return list.map( e -> new EventDTO(e) );
    }

    public EventDTO getEventById(Long id) {
        Optional<Event> op = eventRepository.findById(id);
        Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        return new EventDTO(eve);
    }

    public EventDTO insert(EventInsertDTO dto){

        if(dto.getName().isEmpty() || dto.getDescription().isEmpty() || dto.getEmailContact().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fill in all fields!");
        }

        if(dto.getAmountFreeTickets() < 0 || dto.getAmountPayedTickets() < 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The number of Tickets must be greater than or equal to 0!");
        }

        if(dto.getPriceTicket() < 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tickets price must be greater than or equal to 0!");
        }

        if((dto.getStartDate().isAfter(dto.getEndDate()))){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The end date must be greater than the start date!");
        }

        if(dto.getStartTime().isAfter(dto.getEndTime()) && dto.getStartDate().isEqual(dto.getEndDate())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The end date must be greater than the start date!");
        }

        Event entity = new Event(dto);
        entity = eventRepository.save(entity);
        return new EventDTO(entity);
    }

    public void delete(Long id){
        try{
            eventRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    public EventDTO update(Long id, EventUpdateDTO dto){
        try{
            if(dto.getDescription().isEmpty() || dto.getEmailContact().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preencha todos os campos!");
            }
    
            if(dto.getAmountFreeTickets() < 0 || dto.getAmountPayedTickets() < 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O número deve ser maior ou igual a 0!");
            }
    
            if(dto.getPriceTicket() < 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O preço deve ser maior ou igual a 0!");
            }
    
            if(dto.getStartDate().isAfter(dto.getEndDate())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A data de término deve ser posterior à data de início!
");
            }

            if(dto.getStartTime().isAfter(dto.getEndTime()) && dto.getStartDate().isEqual(dto.getEndDate())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "
A data de término deve ser posterior à data de início! ");
            }

            Event entity = eventRepository.getOne(id);
            entity.setDescription(dto.getDescription());
            entity.setStartDate(dto.getStartDate());
            entity.setEndDate(dto.getEndDate());
            entity.setStartTime(dto.getStartTime());
            entity.setEndTime(dto.getEndTime());
            entity.setEmailContact(dto.getEmailContact());
            entity.setAmountFreeTickets(dto.getAmountFreeTickets());
            entity.setAmountPayedTickets(dto.getAmountPayedTickets());
            entity.setPriceTicket(dto.getPriceTicket());
            entity = eventRepository.save(entity);
            return new EventDTO(entity);
        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado!");
        }  
    }

}
