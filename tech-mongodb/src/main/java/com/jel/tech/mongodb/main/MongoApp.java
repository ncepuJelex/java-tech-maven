package com.jel.tech.mongodb.main;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Query;

import com.jel.tech.mongodb.model.Person;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MongoApp {
	//以后就用这种方式做日志了。。。
	private static final Log log = LogFactory.getLog(MongoApp.class);
	
	public static void main(String[] args) {
		//先这样写着，回头再改用MongoDbFactory
//		MongoOperations mongoOps = new MongoTemplate(new Mongo(),"learn");
//		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(),"learn"));
		
		//localhost:host名称,learn:collection名称
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient("localhost"), "learn"));
		
		mongoOps.insert(new Person("Jim",35));
		Person p = mongoOps.findOne(new Query(where("name").is("Joe")), Person.class);
		log.info(p);
		
		//我不想删除啊！
//		mongoOps.dropCollection(Person.class);
//		mongoOps.dropCollection("person");
		//Find
		mongoOps.findById(p.getId(), Person.class);
		log.info("Found:" + p);
		
		//update
		WriteResult writeResult = mongoOps.updateFirst(query(where("name").is("Joe")), update("age",25), Person.class);
		log.info(writeResult);
		
		p = mongoOps.findOne(query(where("age").is(25)), Person.class);
		log.info("Updated:" + p);
		
		//delete
		mongoOps.remove(p);
		
		//Check that delete worked
		List<Person> people = mongoOps.findAll(Person.class);
		log.info("Number of people:" + people.size());
	}
}
