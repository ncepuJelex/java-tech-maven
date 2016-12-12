package com.jel.tech.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jel.tech.mongodb.dao.PersonRepository;
import com.jel.tech.mongodb.model.Address;
import com.jel.tech.mongodb.model.Person;
import com.jel.tech.mongodb.model.Restanrant;
import com.jel.tech.mongodb.model.Sample;
import com.jel.tech.mongodb.model.Venue;
import com.jel.tech.mongodb.service.PersonService;
import com.mongodb.WriteResult;;

/**
 * Spring4JUnit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:mongodb-applicationContext.xml")
public class MongoTest {

	private static final Logger log = LoggerFactory.getLogger(MongoTest.class);
	
	//无name属性也可
	@Resource(name="mongoTemplate")
	private MongoOperations mongoOps;
	
	@Resource(name=PersonService.BeanName)
	PersonService personService;
	@Autowired
	PersonRepository repository;

	@Test
	public void fun() {
//		mongoOps.insert(new Person("1002", "高圆圆", 26));
		//就不要指定主键了，不然还得自己维护它
		mongoOps.insert(new Person("高圆圆", 26));
		log.info(mongoOps.findOne(new Query(where("name").is("高圆圆")), Person.class).toString());

		// mongoOps.dropCollection(Person.class);
	}
	
	/*
	 * 测试Type mapping，注意查看mongo库中保存的状态是不是如下：
	 	{ "_class" : "com.acme.Sample",
	      "value" : { "_class" : "com.acme.Person" }
	    }
	 */
	/*
	 * 结果如下：
	 > db.sample.find();
		{ "_id" : ObjectId("5842df826cdc950629c506f1"),
		 "_class" : "com.jel.tech.mongodb.model.Sample",
		  "value" : { "_class" : "com.jel.tech.mongodb.model.Person",
		   "_id" : null, "name" : "concactPerson", "age" : 33
		   }
		 }
	 */
	@Test
	public void fun2() {
		Sample sample = new Sample();
		sample.setValue(new Person("concactPerson",33));
		mongoOps.save(sample);
	}

	@Test
	public void testUpdateFirst() {
		WriteResult writeResult = mongoOps.updateFirst(query(where("name").is("高圆圆")), new Update().inc("age", -1), Person.class);
		log.info(writeResult.toString());
	}
	
	@Test
	public void testUpdateMulti() {
		WriteResult writeResult = mongoOps.updateMulti(query(where("name").is("高圆圆")), update("age","28"), Person.class);
		log.info(writeResult.toString());
	}
	
	@Test
	public void testUpsert() {
		WriteResult writeResult = mongoOps.upsert(query(where("name").is("CR7").and("age").is(31)), new Update().set("name","CR7").set("age", 31), Person.class);
		log.info(writeResult.toString());
	}
	
	@Test
	public void testFindAndModify() {
		
		mongoOps.insert(new Person("Tom",21));
		mongoOps.insert(new Person("Dick",22));
		mongoOps.insert(new Person("Harry",23));
		
		Query query = new Query(Criteria.where("name").is("Harry"));
		Update update = new Update().inc("age", 1);
		//this will return the old object
		Person p = mongoOps.findAndModify(query, update, Person.class);
		
		log.error(p.toString()); //age is 23
		
		p = mongoOps.findOne(query, Person.class);
		log.error(p.toString()); // now age is 24
		
		p = mongoOps.findAndModify(query, update, new FindAndModifyOptions().returnNew(true).upsert(true), Person.class);
		log.info(p.toString()); // the new updated person
		
//		mongoOps.remove(p);//删除
	}
	
	/**
	 * 如果你想级联插入，并且想让子集合自动生成主键，那么子集合也得有insert操作，
	 * 如果只想插入主集合，那么设置关联关系后保存到库中时，子集合的主键如果不主动设置子集合
	 * 的主键值，那么它为会为null
	 */
	@Test
	public void testJiLian() {
		Person p = new Person("Dony",28);
		Address addr = new Address("13457901234","weichat","1007792878","1007792878@@qq.com","中国河北保定韩庄乡华电路689号");
//		addr.setId("addr102");
		p.setAddress(addr);
		
		mongoOps.insert(addr);
		
		mongoOps.insert(p);
	}
	/*
	 * 使用原生的mongodb语句查询:
	 * 不过为什么查询子文档不好使呢？？？
	 */
	@Test
	public void testBasicQuery() {
		BasicQuery query = new BasicQuery("{age:{$gte:28},name:'Dony'}");
		log.info("ready to search..");
//		BasicQuery query = new BasicQuery("{age:{$gte:28},name:'Dony','address.weichat':/^weichat/}");
		List<Person> result = mongoOps.find(query, Person.class);
		log.info("============================");
		log.info("the result:{},date:{}", result,new Date());
	}
	/*
	 * 查询子文档
	 */
	@Test
	public void testQueryDoc() {
		List<Person> list = mongoOps.find(query(where("age").gte(28).and("address.qq").is("1007792878")), Person.class);
		System.out.println(list);
	}
	@Test
	public void testGeoSpatial() {
		Circle circle = new Circle(-73.99171, 40.738868, 0.01);
		List<Venue> venues = mongoOps.find(query(where("location").within(circle)), Venue.class);
		List<Venue> venues2 = mongoOps.find(query(where("location").withinSphere(circle)), Venue.class);
		
		//lower-left then upper-right
		Box box = new Box(new double[]{-73.99756, 40.73083}, new double[]{-73.988135, 40.741404});
		List<Venue> venues3 =mongoOps.find(new Query(Criteria.where("location").within(box)), Venue.class);
		
		Point point = new Point(-73.99171, 40.738868);
		List<Venue> venues4 = mongoOps.find(query(where("location").near(point).minDistance(0.01).maxDistance(100)), Venue.class);
		List<Venue> venues5 = mongoOps.find(query(where("location").nearSphere(point).maxDistance(0.003712240453784)), Venue.class);
		//find all restaurants in the surrounding 10 miles
		NearQuery query = NearQuery.near(point).maxDistance(new Distance(10, Metrics.MILES));
		GeoResults<Restanrant> nearRestanrants = mongoOps.geoNear(query, Restanrant.class);
	}
	
	/**
	 * Query by Example
	 */
	@Test
	public void fun3() {
		Person person = new Person("高圆圆",26);
		Example<Person> example = Example.<Person>of(person);
	}
	@Test
	public void fun4() {
		Person person = new Person("高圆圆",26);
		ExampleMatcher matcher = ExampleMatcher.matching()
			.withIgnorePaths("age")
			.withIncludeNullValues();
		Example<Person> example = Example.<Person>of(person,matcher);
	}
	@Test
	public void fun5() {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("name", new GenericPropertyMatcher().endsWith())
				.withMatcher("address.weichat", match->match.ignoreCase())
				.withMatcher("address.qq", match->match.caseSensitive())
				.withMatcher("address.email", match->match.regex());
	}
	@Test
	public void fun6() {
		personService.findAll();
		System.out.println(personService);
	}
	@Test
	public void fun7() {
		Person probe = new Person("高圆圆",26);
		Pageable pageable = new PageRequest(0,5);
		Page<Person> page = personService.findAll(probe,pageable);
		page.forEach(ele->System.out.print(ele));
		System.out.println("**************");
		System.err.println(page.getSize());
	}
	
}