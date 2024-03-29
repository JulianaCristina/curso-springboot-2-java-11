package com.iftm.curso.resources;

import java.net.URI;
import java.util.List;

import com.iftm.curso.dto.CategoryDTO;
import com.iftm.curso.dto.CategoryInsertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.iftm.curso.entities.Category;
import com.iftm.curso.services.CategoryService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value =  "/categories")
public class CategoryResources {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll(){
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/product/{productId}")
	public ResponseEntity<List<CategoryDTO>> findByProduct(@PathVariable Long productId){
		List<CategoryDTO> list  = service.findByProduct(productId);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
		CategoryDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryInsertDTO dto){
		CategoryDTO newDTO = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDTO.getId()).toUri();

		return ResponseEntity.created(uri).body(newDTO);
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
 	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id,@RequestBody CategoryDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}




} 
