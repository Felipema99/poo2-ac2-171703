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

import ac2.ac2lab.dto.AttendDTO;
import ac2.ac2lab.dto.AttendInsertDTO;
import ac2.ac2lab.dto.AttendUpdateDTO;
import ac2.ac2lab.services.AttendService;

@RestController
@RequestMapping("/attendees")
public class AttendController {
    
    @Autowired
    private AttendService attendService;

    @GetMapping
    public ResponseEntity<Page<AttendDTO>> getAttendees(

        @RequestParam(value = "page",         defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
        @RequestParam(value = "direction",    defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy",      defaultValue = "id") String orderBy,
        @RequestParam(value = "name",         defaultValue = "") String name
        
    ) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        Page<AttendDTO> list = attendService.getAttendees(pageRequest, name);
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<AttendDTO> getAttendById(@PathVariable Long id) {
        AttendDTO dto = attendService.getAttendById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
	public ResponseEntity<AttendDTO> insert(@RequestBody AttendInsertDTO insertDTO){
		AttendDTO dto = attendService.insert(insertDTO); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		attendService.delete(id); 
		return ResponseEntity.noContent().build();
	}

    @PutMapping("{id}")
	public ResponseEntity<AttendDTO> update(@PathVariable Long id, @RequestBody AttendUpdateDTO updateDTO){
		AttendDTO dto = attendService.update(id, updateDTO); 
		return ResponseEntity.ok().body(dto);
	}
}
