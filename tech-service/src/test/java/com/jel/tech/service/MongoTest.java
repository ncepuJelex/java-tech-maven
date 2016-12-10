package com.jel.tech.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jel.tech.mongodb.model.Person;

/**
 * 为了测试service中可以可以使用注入的mongodb
 * @author zhenhua
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:mongodb-applicationContext.xml")
public class MongoTest {

	@Autowired
	private MongoOperations mongoOps;
	
//	private ApplicationContext ctx;

//	@Before
//	public void setUp() {
//		ctx = new ClassPathXmlApplicationContext("classpath:mongodb-applicationContext.xml");
//		System.out.println(ctx);
//		mongoOps = ctx.getBean("mongoTemplate", MongoTemplate.class);
//		System.out.println(mongoOps);
//	}

	@Test
	public void test() {
		mongoOps.insert(new Person("Messi10",29));
	}

}
