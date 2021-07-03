package org.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Utility {

	public NodeList XMLNodeList(String XMLPath, String TagName)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(XMLPath);
		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getElementsByTagName(TagName);
		return nodeList;
	}

	public static List<String> GetXMLElements(NodeList XMLNodeListFile) {
		List<String> XMLNodes = new ArrayList<>();
		for (int itr = 0; itr < XMLNodeListFile.getLength(); itr++) {
			Node node = XMLNodeListFile.item(itr);
			XMLNodes.add(node.getNodeName());
		}
		return XMLNodes;
	}
	

	public static List<String> GetNodeValuesOfTransaction(String file, String Xpath, int index) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		List<String> XMLNodeValues = new ArrayList<>();
		List<String> XMLNodeNames = new ArrayList<>();
		
		DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = Factory.newDocumentBuilder();
		Document doc = builder.parse(file);
	
		doc.getDocumentElement().normalize();
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile(Xpath);		
		
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		
		for (int i = 0; i < index; i++) {
			Element el = (Element) nodes.item(i);
	
			NodeList children = el.getChildNodes();
			for (int k = 0; k < children.getLength(); k++) {
				Node child = children.item(k);
				if (child.getNodeType() != Node.TEXT_NODE) {
					
					XMLNodeNames.add(child.getNodeName());
					try {
						if (child.getFirstChild().getNodeType() == Node.TEXT_NODE)
							
						XMLNodeValues.add(child.getFirstChild().getNodeValue());
					} catch (NullPointerException e) {
						//System.out.println("inner child value: null");

					}
				}
			}
		}

		return XMLNodeValues;
	}
	
	public static List<String> GetNodeNamesOfTransaction(String file, String Xpath, int index) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		List<String> XMLNodeValues = new ArrayList<>();
		List<String> XMLNodeNames = new ArrayList<>();
		
		DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = Factory.newDocumentBuilder();
		Document doc = builder.parse(file);
	
		doc.getDocumentElement().normalize();
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile(Xpath);		
		
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;

		System.out.println(nodes.getLength());
		
		for (int i = 0; i < index; i++) {
			Element el = (Element) nodes.item(i);			
			NodeList children = el.getChildNodes();
			for (int k = 0; k < children.getLength(); k++) {
				Node child = children.item(k);
				if (child.getNodeType() != Node.TEXT_NODE) {
					
					XMLNodeNames.add(child.getNodeName());
					try {
						if (child.getFirstChild().getNodeType() == Node.TEXT_NODE)
							
						XMLNodeValues.add(child.getFirstChild().getNodeValue());
					} catch (NullPointerException e) {
						//System.out.println("inner child value: null");
					}
				}
			}
		}

		return XMLNodeNames;
	}

}
