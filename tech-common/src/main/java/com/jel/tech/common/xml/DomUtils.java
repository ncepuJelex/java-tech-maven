package com.jel.tech.common.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Reader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * DOM4J 工具类
 * @author Jelex
 *
 */
public class DomUtils {

	private DomUtils() {}
	
	/**
	 * 从一个文件中读取内容，转换成Document对象
	 * @param f
	 * @return
	 */
	public static Document loadFromFile(File f) {
		SAXReader saxReader = new SAXReader();
		try {
			return saxReader.read(f);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 读取输入流中内容，转换成Document对象
	 * @param in
	 * @return
	 */
	public static Document loadFromStream(InputStream in) {
		SAXReader saxReader = new SAXReader();
		try {
			return saxReader.read(in);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 读取Reader中的内容，转换成Document对象
	 * @param reader
	 * @return
	 */
	public static Document loadFromReader(Reader reader) {
		SAXReader saxReader = new SAXReader();
		try {
			return saxReader.read(reader);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 把字符串转换成Document对象
	 * @param strText
	 * @return
	 */
	public static Document loadFromStr(String strText) {
		try {
			return DocumentHelper.parseText(strText);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 将Document写入文件中保存
	 * @param doc
	 * @param fileName
	 */
	public static void writeToFile(Document doc, String fileName) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(fileName),format);
			xmlWriter.write(doc);
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 将Docuemnt对象转换成XML字符串
	 * @param doc
	 * @return
	 */
	public static String toXML(Document doc) {
		try {
			return doc.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 将Document从根节点开始转换成XML字符串
	 * @param doc
	 * @return
	 */
	public static String rootToXML(Document doc) {
		try {
			return doc.getRootElement().asXML();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
