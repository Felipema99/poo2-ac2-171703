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

import ac2.ac2lab.dto.AdminInsertDTO;
import ac2.ac2lab.dto.AdminUpdateDTO;
import ac2.ac2lab.dto.AdminDTO;
import ac2.ac2lab.entities.Admin;
import ac2.ac2lab.repositories.AdminRepository;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;

    public Page<AdminDTO> getAdmins(PageRequest pageRequest, String name) {
        Page<Admin> list = adminRepository.find(pageRequest, name);
        return list.map( a -> new AdminDTO(a) );
    }

    public AdminDTO getAdminById(Long id) {
        Optional<Admin> op = adminRepository.findById(id);
        Admin adm = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));

        return new AdminDTO(adm);
    }

    public void delete(Long id){
        try{
            adminRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado");
        }
    }
    
    public AdminDTO insert(AdminInsertDTO dto){

        if(dto.getName().isEmpty() || dto.getEmail().isEmpty() || dto.getPhoneNumber().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preencha todos os campos!");
        }

        Admin entity = new Admin(dto);
        entity = adminRepository.save(entity);
        return new AdminDTO(entity);
    }

    

    public AdminDTO update(Long id, AdminUpdateDTO dto){
        try{
            if(dto.getEmail().isEmpty() || dto.getPhoneNumber().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preencha todos os campos!");
            }

            Admin entity = adminRepository.getOne(id);
            entity.setEmail(dto.getEmail());
            entity.setPhoneNumber(dto.getPhoneNumber());
            entity = adminRepository.save(entity);
            return new AdminDTO(entity);
        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preencha todos os campos!");
        }  
    }
}
