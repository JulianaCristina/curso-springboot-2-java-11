package com.iftm.curso.resources;

import java.util.List;

import com.iftm.curso.dto.OrderDTO;
import com.iftm.curso.dto.OrderItemDTO;
import com.iftm.curso.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iftm.curso.entities.Order;
import com.iftm.curso.services.OrderService;

@RestController
@RequestMapping(value =  "/orders")
public class OrderResources {
	
	@Autowired
	private OrderService service;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<OrderDTO>> findAll(){
		List<OrderDTO> list = service.findAll();
		return ResponseEntity.ok().body(list );
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable Long id){
		OrderDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/{id}/items")
	public ResponseEntity<List<OrderItemDTO>> findItems(@PathVariable Long id){
		List<OrderItemDTO> list = service.findItems(id);
		return ResponseEntity.ok().body(list);
	}


	@GetMapping(value = "/myorders")
	public ResponseEntity<List<OrderDTO>> findByClient(){
		List<OrderDTO> list = service.findByClient();
		return ResponseEntity.ok().body(list );
	}
} 
