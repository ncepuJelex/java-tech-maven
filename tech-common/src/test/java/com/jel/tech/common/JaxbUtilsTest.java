package com.jel.tech.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;

import com.jel.tech.common.model.Address;
import com.jel.tech.common.model.Person;
import com.jel.tech.common.xml.JaxbUtils;

public class JaxbUtilsTest {

	@Test
	public void test() {
		
		Person person = new Person();
		person .setId(101);
		person .setName( "张三" );
		person .setAge(30);
		
		Address address = new Address();
		address .setId(1101);
		address .setProvince( "北京" );
		address .setCity( "北京市" );
		address .setArea( "朝阳区" );
		address .setOthers( "住着吃瓜群众" );
		person .setAddress( address );
		
		String xml = JaxbUtils.marshal(person);
		System.out.println(xml);
	}
	
	@Test
	public void fun() {
		Person person = new Person();
		person .setId(101);
		person .setName( "张三" );
		person .setAge(30);
		
		Address address = new Address();
		address .setId(1101);
		address .setProvince("北京");
		address .setCity("北京市");
		address .setArea("朝阳区");
		address .setOthers("住着吃瓜群众");
		person .setAddress(address);
		
		JaxbUtils.marshal(person, new File("/Users/zhenhua/temp/person.xml"));
	}

	@Test
	public void fun2() {
		Person person = new Person();
		person .setId(101);
		person .setName( "张三" );
		person .setAge(30);
		
		Address address = new Address();
		address .setId(1101);
		address .setProvince("北京");
		address .setCity("北京市");
		address .setArea("东城区");
		address .setOthers("住在雍和宫");
		person .setAddress(address);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JaxbUtils.marshal(person, outputStream);
		
		String string = new String(outputStream.toByteArray());
		System.out.println(string);
	}
	
	@Test
	public void fun3() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
					"<person age=\"30\" id=\"101\" name=\"张三\">"+
					 "   <address id=\"1101\">"+
					"        <area>东城区</area>"+
					 "       <city>北京市</city>"+
					 "       <others>住在雍和宫</others>"+
					 "       <province>北京</province>"+
					"    </address>"+
					"</person>";
		System.out.println(xml);
		Person person = JaxbUtils.unmarshal(xml, Person.class);
		System.out.println(person);
	}
	
	@Test
	public void fun4() {
		Person person = JaxbUtils.unmarshal(new File("/Users/zhenhua/temp/person.xml"), Person.class);
		System.out.println(person);
	}
	
	@Test
	public void fun5() throws FileNotFoundException {
		InputStream in = new FileInputStream(new File("/Users/zhenhua/temp/person.xml"));
		Person person = JaxbUtils.unmarshal(in, Person.class);
		System.out.println(person);
	}
}
