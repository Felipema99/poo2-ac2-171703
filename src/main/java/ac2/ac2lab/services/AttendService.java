package ac2.ac2lab.services;

import java.util.List;
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
import ac2.ac2lab.entities.Admin;
import ac2.ac2lab.entities.Attend;
import ac2.ac2lab.repositories.AdminRepository;
import ac2.ac2lab.repositories.AttendRepository;

@Service
public class AttendService {
    
    @Autowired
    private AttendRepository attendRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Page<AttendDTO> getAttendees(PageRequest pageRequest, String name) {
        Page<Attend>list = attendRepository.find(pageRequest, name);
        return list.map( at -> new AttendDTO(at) );
    }

    public AttendDTO getAttendById(Long id) {
        Optional<Attend> op = attendRepository.findById(id);
        Attend atd = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado"));

        return new AttendDTO(atd);
    }

    public AttendDTO insert(AttendInsertDTO dto){

        if(dto.getName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "preencha os campos");
        }

        List<Admin> admins = adminRepository.findAll();
        for(Admin admin : admins){
            if(admin.getEmail().equals(dto.getEmail())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "já esta em uso");
            }
        }

        List<Attend> attends = attendRepository.findAll();
        for(Attend attend : attends){
            if(attend.getEmail().equals(dto.getEmail())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "já esta em uso");
            }
        }

        Attend entity = new Attend(dto);
        entity.setBalance(0.0);
        entity = attendRepository.save(entity);
        return new AttendDTO(entity);
    }

    public void delete(Long id){
        try{
            attendRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado");
        }
    }

    public AttendDTO update(Long id, AttendUpdateDTO dto){

        try{
            if(dto.getName().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "preencha os campos");
            }

            Attend entity = attendRepository.getOne(id);
            entity.setName(dto.getName());
            entity = attendRepository.save(entity);
            return new AttendDTO(entity);
        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado");
        }  
    }
}

