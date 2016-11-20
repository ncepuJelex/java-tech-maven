package com.jel.tech.common.xml;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * 
 * @author Jelex
 *	xml工具类，使用Jaxb技术实现xml 到 javabean 的互转
 */
public class JaxbUtils {

	private JaxbUtils() {}
	
	/**
	 * 将JavaBean 转换成 String
	 * @param obj
	 * @return
	 */
	public static <T> String marshal(T obj) {
		StringWriter xmlWriter = new StringWriter();
		try {
			JAXB.marshal(obj, xmlWriter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return xmlWriter.toString();
	}
	/**
	 * 将JavaBean 转换成XML 并保存到一个文件中
	 * @param obj
	 * @param f
	 */
	public static <T> void marshal(T obj, File f) {
		try {
			JAXB.marshal(obj, f);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 将JavaBean 转换成XML 并保存到一个输出流中
	 * @param obj
	 * @param output
	 */
	public static <T> void marshal(T obj, OutputStream output) {
		try {
			JAXB.marshal(obj, output);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 将XML String 转换成JavaBean
	 * @param xml
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> T unmarshal(String xml, Class<T> c) {
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshal = context.createUnmarshaller();
			return (T) unmarshal.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 将文件中的xml文本转换成JavaBean
	 * @param xmlFile
	 * @param obj
	 * @return
	 */
	public static<T> T unmarshal(File xmlFile, Class<T> c) {
		try {
			return JAXB.unmarshal(xmlFile, c);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 将输入流中的内容转换成JavaBean
	 * @param in
	 * @param obj
	 * @return
	 */
	public static<T> T unmarshal(InputStream in, Class<T> c) {
		try {
			return JAXB.unmarshal(in, c);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
