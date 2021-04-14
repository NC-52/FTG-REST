package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


import java.util.List;
import java.util.Optional;



@RestController
public class PersonController {
	
	PersonRepository personRepository;
	
	public PersonController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	@GetMapping("/people")
	ResponseEntity<List<Person>> all() {
		List<Person> people = new ArrayList<>();
		Iterable<Person> iterable = personRepository.findAll();
		iterable.forEach(person -> {
			people.add(person);
		});
	return new ResponseEntity<List<Person>>(people, HttpStatus.OK);
	}

	@GetMapping("/people/{id}")
	ResponseEntity<Person> one(@PathVariable Long id) {
		Optional<Person> request = personRepository.findById(id);
		if (!request.isPresent())
			return new ResponseEntity<Person>(new Person(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Person>(request.get(), HttpStatus.OK);
	}

	@PostMapping("/people")
	ResponseEntity<Person> newPerson(@RequestBody Person person) {
		return new ResponseEntity<Person>(personRepository.save(person), HttpStatus.OK);
	}

	@PutMapping("/people/{id}")
	ResponseEntity<Person> newPerson(@RequestBody Person person, @PathVariable Long id) {
		Optional<Person> request = personRepository.findById(id);
		if (!request.isPresent())
			return new ResponseEntity<Person>(new Person(), HttpStatus.BAD_REQUEST);
		Person c = request.get();
		c.setName(person.getName());
		c.setEmail(person.getEmail());
		c.setPhone(person.getPhone());
		c.setAddress(person.getAddress());
		personRepository.save(c);
		return new ResponseEntity<Person>(c, HttpStatus.OK);
	}

	@DeleteMapping("/people/{id}")
	ResponseEntity<Person> deletePerson(@PathVariable Long id) {
		Optional<Person> request = personRepository.findById(id);
		if (!request.isPresent())
			return new ResponseEntity<Person>(new Person(), HttpStatus.BAD_REQUEST);
		Person c = request.get();
		personRepository.deleteById(id);
		return new ResponseEntity<Person>(new Person(), HttpStatus.OK);
	}
}