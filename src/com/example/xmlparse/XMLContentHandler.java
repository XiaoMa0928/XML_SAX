package com.example.xmlparse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

//SAX类：DefaultHandler，它实现了ContentHandler接口。在实现的时候，只需要继承该类，重载相应的方法即可。
public class XMLContentHandler extends DefaultHandler {

	private List<Person> persons = null;
	private Person currentPerson;
	private String tagName = null;// 当前解析的元素标签

	public List<Person> getPersons() {
		return persons;
	}

	public List<Person> readXML(InputStream inStream) {
		
			// 创建解析器
//			获得解析器工厂
			SAXParserFactory spf = SAXParserFactory.newInstance();
//			创建解析器
			SAXParser saxParser;
			try {
				saxParser = spf.newSAXParser();
				// 设置解析器的相关特性，true表示开启命名空间特性
//				saxParser.setProperty("http://xml.org/sax/features/namespaces",
//						true);
				saxParser.parse(inStream, this);
				inStream.close();
				return getPersons();
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	// 接收文档开始的通知。当遇到文档的开头的时候，调用这个方法，可以在其中做一些预处理的工作。
	@Override
	public void startDocument() throws SAXException {
		Log.i("", "开始解析文档");
		persons = new ArrayList<Person>();
	}

	// 接收元素开始的通知。当读到一个开始标签的时候，会触发这个方法。其中namespaceURI表示元素的命名空间；
	// localName表示元素的本地名称（不带前缀）；qName表示元素的限定名（带前缀）；atts 表示元素的属性集合
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		Log.i("", "开始解析元素");
		if (localName.equals("person")) {
			currentPerson = new Person();
			currentPerson.setId(Integer.parseInt(atts.getValue("id")));
		}

		this.tagName = localName;
	}

	// 接收字符数据的通知。该方法用来处理在XML文件中读到的内容，第一个参数用于存放文件的内容，
	// 后面两个参数是读到的字符串在这个数组中的起始位置和长度，使用new String(ch,start,length)就可以获取内容。
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		Log.i("", "开始解析内容");
		if (tagName != null) {
			String data = new String(ch, start, length);
			if (tagName.equals("name")) {
				this.currentPerson.setName(data);
			} else if (tagName.equals("age")) {
				this.currentPerson.setAge(Short.parseShort(data));
			}
		}
	}

	// 接收文档的结尾的通知。在遇到结束标签的时候，调用这个方法。其中，uri表示元素的命名空间；
	// localName表示元素的本地名称（不带前缀）；name表示元素的限定名（带前缀）
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		Log.i("", "开始解析。。。。。。。。。。。");
		if (localName.equals("person")) {
			persons.add(currentPerson);
			currentPerson = null;
		}

		this.tagName = null;
	}
}
