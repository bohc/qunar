package com.bhc.util;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.thoughtworks.xstream.XStream;

/**
 * ���ڽ��������־û���XML�ļ���������̵ĳ־û��ࣨdom4j,xstreamʵ�֣�
 * 
 * @author: sitinspring(junglesong@gmail.com)
 * @date: 2008-1-8
 */
public class XmlPersistence<T> {
	// XML�ļ���
	private final String xmlFile;

	// XML �ĵ�����
	private Document document;

	// ���ڵ�
	private Element root;

	// ���ڵ�����
	private final String rootText = "root";

	/**
	 * ���������캯����ָ���洢���ļ���
	 * 
	 * @param xmlFile
	 */
	public XmlPersistence(String xmlFile) {
		this.xmlFile = xmlFile;

		init();
	}

	/**
	 * ���һ�����󌦑��Ĺ��c��XML�ļ�
	 * 
	 * @param type
	 */
	public void add(T type) {
		// ������ת��ΪXML����

		try {
			XStream xStream = new XStream();
			xStream.alias(type.getClass().getName(), type.getClass()); 
			String xml = xStream.toXML(type);

			// ��ת��������ֱ�ɽڵ�
			Document docTmp = DocumentHelper.parseText(xml);
			Element typeElm = docTmp.getRootElement();

			// �������ڵ�
			root.add(typeElm);

			// �����ļ�
			saveDocumentToFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ��XML�ļ���,ɾ��һ�����󌦑��Ĺ��c
	 * 
	 * @param type
	 */
	public void del(T type) {
		// ������ת��ΪXML����
		XStream xStream = new XStream();
		String xml = xStream.toXML(type);

		try {
			List nodes = root.elements();

			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element companyElm = (Element) it.next();

				// ���ң��ڵ�ȫ����ͬ�ض�Ԫ����ͬ
				if (companyElm.asXML().equals(xml)) {
					// ɾ��ԭ�нڵ�
					root.remove(companyElm);

					// �����ļ�
					saveDocumentToFile();
					return;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ��XML��ȡ�����ж���
	 * 
	 * @return
	 */
	public List<T> loadAll() {
		List<T> retval = new ArrayList<T>();

		try {
			List nodes = root.elements();

			for (Iterator it = nodes.iterator(); it.hasNext();) {
				// ȡ��ÿ�����c
				Element companyElm = (Element) it.next();
				
				// �����c�D���錦��
				XStream xStream = new XStream();
				T t = (T) xStream.fromXML(companyElm.asXML());

				retval.add(t);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return retval;
	}

	// ��ʼ���ĵ����󼰸��ڵ�
	private void init() {
		File file = new File(xmlFile);

		try {
			// �ж��ļ��Ĵ�������ǿ����Ľ�׳��
			if (file.exists()) {
				// �ļ�����,ֱ�Ӵ��ļ���ȡ�ĵ�����
				SAXReader reader = new SAXReader();
				document = reader.read(file);
				root = document.getRootElement();
			} else {
				// �ļ�������,�����ĵ�����
				document = DocumentHelper.createDocument();
				root = document.addElement(rootText);// �������ڵ�
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ��Documentд���ļ�
	 * 
	 */
	private void saveDocumentToFile() {
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK"); // ָ��XML����
			XMLWriter writer = new XMLWriter(new FileWriter(xmlFile), format);

			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}