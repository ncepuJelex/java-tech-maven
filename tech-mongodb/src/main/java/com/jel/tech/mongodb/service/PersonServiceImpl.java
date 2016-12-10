package com.jel.tech.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jel.tech.mongodb.dao.PersonRepository;
import com.jel.tech.mongodb.model.Person;

@Service(PersonService.BeanName)
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;
	
	@Override
	public List<Person> findAll() {
		System.out.println("findAll() executed..");
		System.out.println(personRepository);
		return null;
	}

	@Override
	public Page<Person> findAll(Person probe, Pageable pageable) {
		System.out.println("findAll(probe,pageable) executed..");
		return personRepository.findAll(Example.of(probe), pageable);
	}

	@Override
	public List<Person> findByName(Person probe) {
		System.out.println("findByName(probe) executed..");
		
		return (List<Person>) personRepository.findAll(Example.of(probe));
	}

	@Override
	public long getCount(Person probe) {
		
		System.out.println("getCount(probe) executed..");
		return personRepository.count(Example.of(probe));
	}

}
