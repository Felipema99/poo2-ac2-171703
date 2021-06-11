package ac2.ac2lab.services;

import java.util.Optional;

import java.time.Instant;
import java.time.LocalDateTime;

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

import ac2.ac2lab.repositories.PlaceRepository;
import ac2.ac2lab.repositories.TicketRepository;
import ac2.ac2lab.repositories.AdminRepository;
import ac2.ac2lab.repositories.AttendRepository;
import ac2.ac2lab.entities.Place;
import ac2.ac2lab.entities.Ticket;
import ac2.ac2lab.entities.View;
import ac2.ac2lab.entities.Admin;
import ac2.ac2lab.entities.Attend;
import ac2.ac2lab.dto.AttendTicketDTO;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private AttendRepository attendRepository;

    @Autowired
    private TicketRepository ticketRepository;



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
	
	LocalDateTime start = LocalDateTime.of(dto.getStartDate(), dto.getStartTime());
        LocalDateTime end = LocalDateTime.of(dto.getEndDate(), dto.getEndTime());

        if(dto.getName().isEmpty() || dto.getDescription().isEmpty() || dto.getEmailContact().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "preencher todos os campos");
        }

        if(dto.getAmountFreeTickets() < 0 || dto.getAmountPayedTickets() < 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deve ser maior ou igual a 0");
        }

        if(dto.getPriceTicket() < 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O preço deve ser maior ou igual a 0");
        }

        if(start.isAfter(end)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A data de termino deve ser posterior a de inicio");
        }

        if(start.isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deve ser posterior a data atual");
        }

        Optional<Admin> op = adminRepository.findById(dto.getAdmin());
        op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado!"));
        

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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A data de término deve ser posterior à data de início!");
            }

            if(start.isAfter(end)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A data de término deve ser posterior à data de início!");
            }
    
            if(start.isBefore(LocalDateTime.now())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "deve ser posterior à data atual!");
            }


            Event entity = eventRepository.getOne(id);
            
	    LocalDateTime start_event = LocalDateTime.of(entity.getStartDate(), entity.getStartTime());
            LocalDateTime end_event = LocalDateTime.of(entity.getEndDate(), entity.getEndTime());

            if(LocalDateTime.now().isAfter(start_event) && LocalDateTime.now().isBefore(end_event)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "nao esta ocorrendo");
            }
	    

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

public EventDTO addPlace(Long id, Long place) {
        Optional<Event> op = eventRepository.findById(id);
        Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado"));

        Optional<Place> op2 = placeRepository.findById(place);
        Place plc = op2.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado"));

        eve.addPlace(plc);
        eve = eventRepository.save(eve);
        return new EventDTO(eve);
    }

    public void removePlace(Long id, Long place){
        try{
            Optional<Event> op = eventRepository.findById(id);
            Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado"));

            Optional<Place> op2 = placeRepository.findById(place);
            Place plc = op2.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado"));

            for(Place event_place : eve.getPlaces()){
                if(event_place.getId() == plc.getId()){
                    eve.removePlace(plc);
                    eve = eventRepository.save(eve);
                    return;
                }
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "nao esta associado a lugar algum");      
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 not found!");
        }
    }

    public View getTickets(Long id){
        Optional<Event> op = eventRepository.findById(id);
        Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado"));

        View view = new View();

        view.setTotalFreeTickets(eve.getAmountFreeTickets());
        view.setTotalPayedTickets(eve.getAmountPayedTickets());

        for(Ticket ticket : eve.getTickets()){        
            if(ticket.getType().equals("Free")){
                view.setSoldFreeTickets(view.getSoldFreeTickets() + 1);
                view.addListFreeTickets(ticket.getAttend().getName());
            }
            else{
                view.setSoldPayedTickets(view.getSoldPayedTickets() + 1);
                view.addListPayedTickets(ticket.getAttend().getName());
            }
        }

        return view;
    }

    public Attend insertTicket(AttendTicketDTO dto, Long id){
        
	Optional<Attend> op2 = attendRepository.findById(dto.getId());
        Attend atd = op2.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "não encontrado!"));

	Optional<Event> op = eventRepository.findById(id);
        Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "não encontrado!"));

        

        if(!dto.getType().equals("Free") && !dto.getType().equals("Payed")){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de uso gratuito ou pago");
        }

        if(eve.hasTicket(dto.getType()) != true){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe ingressos suficientes -> " + dto.getType());
        }

        Ticket ticket = new Ticket();
        ticket.setType(dto.getType());
        ticket.setAttend(atd);
        ticket.setEvent(eve);
        ticket.setPrice(eve.getPriceTicket());
        ticket.setDate(Instant.now());
        ticket = ticketRepository.save(ticket);

        return atd;
    }

    public void deleteTickets(Long id){
        Optional<Event> op = eventRepository.findById(id);
        Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado!"));

        for(Ticket ticket : eve.getTickets()){
            eve.removeTicket(ticket);
            eve = eventRepository.save(eve);
        }
    }

}
