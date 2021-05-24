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

import ac2.ac2lab.dto.AttendDTO;
import ac2.ac2lab.dto.AttendInsertDTO;
import ac2.ac2lab.dto.AttendUpdateDTO;
import ac2.ac2lab.entities.Attend;
import ac2.ac2lab.repositories.AttendRepository;

@Service
public class AttendService {
    
    @Autowired
    private AttendRepository attendRepository;

    public Page<AttendDTO> getAttendees(PageRequest pageRequest, String name) {
        Page<Attend>list = attendRepository.find(pageRequest, name);
        return list.map( at -> new AttendDTO(at) );
    }

    public AttendDTO getAttendById(Long id) {
        Optional<Attend> op = attendRepository.findById(id);
        Attend atd = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found"));

        return new AttendDTO(atd);
    }

  

    public void delete(Long id){
        try{
            attendRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado!");
        }
    }

    public AttendDTO insert(AttendInsertDTO dto){

        if(dto.getName().isEmpty() || dto.getEmail().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preencha todos os campos!");
        }
        if(dto.getBalance() < 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O saldo deve ser maior ou igual a 0!");
        }

        Attend entity = new Attend(dto);
        entity = attendRepository.save(entity);
        return new AttendDTO(entity);
    }

    public AttendDTO update(Long id, AttendUpdateDTO dto){

        try{
            if(dto.getEmail().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preencha todos os campos");
            }

            Attend entity = attendRepository.getOne(id);
            
            if(entity.getBalance() < (dto.getBalance() * -1)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tem apenas " + entity.getBalance() + " de equilibrio");
            }
            
            entity.setBalance((entity.getBalance() + dto.getBalance()));
            entity.setEmail(dto.getEmail());
            entity = attendRepository.save(entity);
            return new AttendDTO(entity);
        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado!");
        }  
    }
}
