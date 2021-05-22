package com.project.lms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<List<RezervationDto>> showAllRezervations(){
		logger.info("Showing all Rezervations");
		return new ResponseEntity<>(rezervationService.showAllRezervation(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RezervationDto> showRezervationById(@PathVariable("id") long id){
		logger.info("Showing rezervations by id: {}",id);
		return new ResponseEntity<>(rezervationService.showRezervationById(id), HttpStatus.OK);
	}
	
}
