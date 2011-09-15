package com.mpaike.core.util.xml;

import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
/**
 * xml
 * @author niko
 * @version 1.0
 */
public class XmlForJdomUtil {

	private XmlForJdomUtil() {
	}

	public static String encoding = "UTF-8";

	public static String xmlhead = "<?xml version=\"1.0\" encoding=\""
			+ encoding + "\"?>";

	public static String xml2string(Element xmlelement) {
		XMLOutputter outer = new XMLOutputter();
		return outer.outputString(xmlelement);
	}

	public static void save(java.io.File file, Element xmlelement)
			throws IOException {
		file.getAbsoluteFile().getParentFile().mkdirs();
		java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
		fos.write((xmlhead + "\n" + xml2string(xmlelement)).getBytes(encoding));
		fos.close();
	}

	public static void save(java.io.File file, Document doc) throws IOException {
		file.getAbsoluteFile().getParentFile().mkdirs();
		java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
		fos.write((xmlhead + "\n" + xml2string(doc.getRootElement()))
				.getBytes(encoding));
		fos.close();
	}

	public static Document parser(String xmlcontent) throws JDOMException,
			IOException {
		java.io.StringReader sr = new java.io.StringReader(xmlcontent);
		SAXBuilder sb = new SAXBuilder();
		return sb.build(sr);
	}

	public static Document parser(java.io.File file) throws JDOMException,
			IOException {
		SAXBuilder sb = new SAXBuilder();

		return sb.build(file);
	}

	public static Document parser(InputStream instream) throws JDOMException,
			IOException {
		SAXBuilder sb = new SAXBuilder();
		return sb.build(instream);
	}

	public static Document parserURI(String url) throws JDOMException,
			IOException {
		SAXBuilder sb = new SAXBuilder();
		return sb.build(url);
	}

	public static void main(String[] args) {
		try {
			Document doc = parser("E:/instanceConfig.xml");
			System.out.println(doc.getRootElement().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存xml文件element内容
	 */
	public static Element saveChildText(Element elmt, String elementName,
			String elementValue) {
		//element名称为空，则返回原element
		if (elementName == null || elementName.length() <= 0) {
			return elmt;
		}

		//
		if (elementValue == null) {
			elementValue = "";
		}

		//若element存在，改变其value，并返回
		if (elmt.getChild(elementName) != null) {
			elmt.getChild(elementName).setText(elementValue);
			return elmt;
		}

		//若element不存在，新建此element，保存值，并返回
		elmt.addContent(new Element(elementName));
		elmt.getChild(elementName).setText(elementValue);
		return elmt;
	}

	/**
	 * 添加xml文件element内容
	 */
	public static Element addChildText(Element elmt, String elementName,
			String elementValue) {
		//element名称为空，则返回原element
		if (elementName == null || elementName.length() <= 0) {
			return elmt;
		}

		//element值为空，设置为""
		if (elementValue == null) {
			elementValue = "";
		}

		//新建此element，保存值，并返回
		Element element = new Element(elementName);
		elmt.addContent(element);
		element.setText(elementValue);
		return elmt;
	}

}