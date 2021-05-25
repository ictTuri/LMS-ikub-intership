package com.project.lms.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.dto.RezervationCreateUpdateDto;
import com.project.lms.dto.RezervationDto;
import com.project.lms.service.RezervationService;

@RestController
@RequestMapping("api/v1/rezervations")
public class RezervationController {

	private static final Logger logger = LogManager.getLogger(RezervationController.class);
	
	private RezervationService rezervationService;
	
	public RezervationController(RezervationService rezervationService) {
		super();
		this.rezervationService = rezervationService;
	}

	@GetMapping()
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_ADMIN')")
	public ResponseEntity<List<RezervationDto>> showAllRezervations(){
		logger.info("Showing all Rezervations");
		return new ResponseEntity<>(rezervationService.showAllRezervation(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_ADMIN')")
	public ResponseEntity<RezervationDto> showRezervationById(@PathVariable("id") long id){
		logger.info("Showing rezervations by id: {}",id);
		return new ResponseEntity<>(rezervationService.showRezervationById(id), HttpStatus.OK);
	}
	
	@PostMapping()
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_ADMIN')")
	public ResponseEntity<RezervationDto> createRezervation(@Valid @RequestBody RezervationCreateUpdateDto rezervation){
		logger.info("Creating new rezervation: {} - {}",rezervation.getBookTitle(), rezervation.getUsername());
		return new ResponseEntity<>(rezervationService.createRezervation(rezervation), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}/close")
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_ADMIN')")
	public ResponseEntity<RezervationDto> closeRezervation(@PathVariable("id") long id){
		logger.info("Closing rezervation with id: {} ",id);
		return new ResponseEntity<>(rezervationService.closeRezervation(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_ADMIN')")
	public ResponseEntity<RezervationDto> updateRezervation(@PathVariable("id") long id,@Valid @RequestBody RezervationCreateUpdateDto rezervation){
		logger.info("Creating new rezervation: {} - {}",rezervation.getBookTitle(), rezervation.getUsername());
		return new ResponseEntity<>(rezervationService.updateRezervation(id,rezervation), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_ADMIN')")
	public ResponseEntity<Void> deleteRezervation(@PathVariable("id") long id){
		logger.info("Deleting rezervation with id: {}",id);
		rezervationService.deleteRezervation(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
