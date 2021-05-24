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

import ac2.ac2lab.dto.PlaceDTO;
import ac2.ac2lab.dto.PlaceInsertDTO;
import ac2.ac2lab.dto.PlaceUpdateDTO;
import ac2.ac2lab.entities.Place;
import ac2.ac2lab.repositories.PlaceRepository;

@Service
public class PlaceService {
    
    @Autowired
    private PlaceRepository placeRepository;

    public Page<PlaceDTO> getPlaces(PageRequest pageRequest, String name) {
        Page<Place>list = placeRepository.find(pageRequest, name);
        return list.map( p -> new PlaceDTO(p) );
    }

    public PlaceDTO getPlaceById(Long id) {
        Optional<Place> op = placeRepository.findById(id);
        Place plc = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));

        return new PlaceDTO(plc);
    }

    public PlaceDTO insert(PlaceInsertDTO dto){

        if(dto.getName().isEmpty() || dto.getAddress().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preencha todos os campos!");
        }

        Place entity = new Place(dto);
        entity = placeRepository.save(entity);
        return new PlaceDTO(entity);
    }

    public void delete(Long id){
        try{
            placeRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado");
        }
    }

    public PlaceDTO update(Long id, PlaceUpdateDTO dto){

        try{
            if(dto.getName().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preencha todos os campos!");
            }

            Place entity = placeRepository.getOne(id);
            entity.setName(dto.getName());
            entity = placeRepository.save(entity);
            return new PlaceDTO(entity);
        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado");
        }  
    }
}
