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

import ac2.ac2lab.dto.AdminDTO;
import ac2.ac2lab.dto.AdminInsertDTO;
import ac2.ac2lab.dto.AdminUpdateDTO;
import ac2.ac2lab.services.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<Page<AdminDTO>> getAdmins(

        @RequestParam(value = "page",         defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
        @RequestParam(value = "direction",    defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy",      defaultValue = "id") String orderBy,
        @RequestParam(value = "name",         defaultValue = "") String name
        
    ) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        Page<AdminDTO> list = adminService.getAdmins(pageRequest, name);
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        AdminDTO dto = adminService.getAdminById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
	public ResponseEntity<AdminDTO> insert(@RequestBody AdminInsertDTO insertDTO){
		AdminDTO dto = adminService.insert(insertDTO); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		adminService.delete(id); 
		return ResponseEntity.noContent().build();
	}

    @PutMapping("{id}")
	public ResponseEntity<AdminDTO> update(@PathVariable Long id, @RequestBody AdminUpdateDTO updateDTO){
		AdminDTO dto = adminService.update(id, updateDTO); 
		return ResponseEntity.ok().body(dto);
	}
    
}
