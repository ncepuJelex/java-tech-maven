package com.jel.tech.mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jel.tech.mongodb.model.Person;

/**
 * Query By Example DAO
 * @author zhenhua
 *
 */
public interface PersonRepository extends MongoRepository<Person,String> {

}
