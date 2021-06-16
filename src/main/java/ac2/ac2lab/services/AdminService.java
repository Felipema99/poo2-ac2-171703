package ac2.ac2lab.services;

import java.util.Optional;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ac2.ac2lab.dto.AdminInsertDTO;
import ac2.ac2lab.dto.AdminUpdateDTO;
import ac2.ac2lab.dto.AdminDTO;
import ac2.ac2lab.entities.Admin;
import ac2.ac2lab.repositories.AdminRepository;
import ac2.ac2lab.entities.Attend;
import ac2.ac2lab.repositories.AttendRepository;

@Service
public class AdminService {
    
     @Autowired
    private AdminRepository adminRepository;

    @Autowired 
    private AttendRepository attendRepository;

    public Page<AdminDTO> getAdmins(PageRequest pageRequest, String name) {
        Page<Admin> list = adminRepository.find(pageRequest, name);
        return list.map( a -> new AdminDTO(a) );
    }

    public AdminDTO getAdminById(Long id) {
        Optional<Admin> op = adminRepository.findById(id);
        Admin adm = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado"));

        return new AdminDTO(adm);
    }

    public AdminDTO insert(AdminInsertDTO dto){

        if(dto.getName().isEmpty() || dto.getEmail().isEmpty() || dto.getPhoneNumber().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "favor preencher os campos!");
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

        Admin entity = new Admin(dto);
        entity = adminRepository.save(entity);
        return new AdminDTO(entity);
    }

    public void delete(Long id){
        try{
            adminRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado");
        }
    }

    public AdminDTO update(Long id, AdminUpdateDTO dto){
        try{
            if(dto.getName().isEmpty() || dto.getPhoneNumber().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "preencha os campos");
            }

            Admin entity = adminRepository.getOne(id);
            entity.setName(dto.getName());
            entity.setPhoneNumber(dto.getPhoneNumber());
            entity = adminRepository.save(entity);
            return new AdminDTO(entity);
        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "nao encontrado");
        }  
    }
}
