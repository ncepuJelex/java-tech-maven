package com.jel.tech.mongodb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jel.tech.mongodb.model.Person;

/**
 * Query by Example service
 * @author zhenhua
 *
 */
public interface PersonService {

	public static final String BeanName = "personService";
	
	List<Person> findAll();
	Page<Person> findAll(Person probe, Pageable pageable);
	List<Person> findByName(Person probe);
	long getCount(Person probe);
}
