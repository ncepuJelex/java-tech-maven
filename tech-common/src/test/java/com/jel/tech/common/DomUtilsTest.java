package com.jel.tech.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.dom4j.Document;
import org.junit.Before;
import org.junit.Test;

import com.jel.tech.common.xml.DomUtils;

public class DomUtilsTest {

	File f = null;
	@Before
	public void setUp() throws Exception {
		f = new File("/Users/zhenhua/temp/person.xml");
	}
	
	@Test
	public void test() {
		Document document = DomUtils.loadFromFile(f);
		System.out.println(document);
		System.out.println(document.asXML());
		System.out.println(document.getName());
		System.out.println(DomUtils.toXML(document));
		System.out.println(DomUtils.rootToXML(document));
		
		DomUtils.writeToFile(document, "/Users/zhenhua/temp/person2.xml");
	}
	
	@Test
	public void test2() throws IOException {
		InputStream in = new FileInputStream(f);
		Document document = DomUtils.loadFromStream(in);
		System.out.println(document.asXML());
		in.close();
	}
	
	@Test
	public void test3() throws FileNotFoundException {
        String strText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  "+
						"	<person age=\"30\" id=\"101\" name=\"张三\">  "+
						"	    <address id=\"1101\">               "+
						"	        <area>朝阳区</area>              "+
						"	        <city>北京市</city>              "+
						"	        <others>住着吃瓜群众</others>       "+
						"	        <province>北京</province>       "+
						"	    </address>                        "+
						"	</person>";
		Document document = DomUtils.loadFromStr(strText);
		System.out.println(document.asXML());
	}

	@Test
	public void test4() throws FileNotFoundException {
		Reader reader = new FileReader(f);
		Document document = DomUtils.loadFromReader(reader);
		System.out.println(document.asXML());
	}
}
