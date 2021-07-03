package org.compare.XMLfiles;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.utility.Utility;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class XMLComparisonTest {
	static ExtentTest test;
	static ExtentReports report;
	
	 static final String BASEPATH = System.getProperty("user.dir");

		public String filePath1= BASEPATH + File.separator+"TestFiles"+File.separator+"Source_File.xml";
		public String filePath2=BASEPATH + File.separator+"TestFiles"+File.separator+"Target_File.xml";

	List<String> XMLNodesFile1 = new ArrayList<>();
	List<String> XMLNodesFile2 = new ArrayList<>();
	
	
	@Test
	public void XMLElementComparisonCheck() throws IOException, ParserConfigurationException, SAXException {
		Utility objUtility = new Utility();

		NodeList XMLNodeListFile1 = objUtility.XMLNodeList(filePath1, "*");
		NodeList XMLNodeListFile2 = objUtility.XMLNodeList(filePath2, "*");

		XMLNodesFile1 = Utility.GetXMLElements(XMLNodeListFile1);
		XMLNodesFile2 = Utility.GetXMLElements(XMLNodeListFile2);

		for (int i = 0; i < XMLNodeListFile1.getLength(); i++) {
			boolean bVal = XMLNodesFile2.contains(XMLNodeListFile1.item(i).getNodeName());
			if (bVal) {
				assertTrue(bVal);
				test.log(LogStatus.PASS, "XML element is same as on second XML file. Actual = "
						+ XMLNodeListFile1.item(i).getNodeName() + " and Expected = " + XMLNodeListFile2.item(i).getNodeName());
			}
		}
	}
	
	
	@Test
	public void XMLNumberofTransactionBlocksComparisonCheck() throws IOException, ParserConfigurationException, SAXException {
		Utility objUtility = new Utility();

		NodeList XMLNodeListFile1 = objUtility.XMLNodeList(filePath1, "ns0:transaction");
		NodeList XMLNodeListFile2 = objUtility.XMLNodeList(filePath2, "ns0:transaction");
		
		if(XMLNodeListFile1.getLength() == XMLNodeListFile2.getLength()) {
			assertTrue(true);
			test.log(LogStatus.PASS, "VERIFICATION OF NUMBER OF TRANSACTION BLOCKS, Actual = "+XMLNodeListFile1.getLength()+ " in file1 ["+filePath1+"] And Expected = "+XMLNodeListFile2.getLength() +" in file2 ["+filePath2+"]");
		}else {
			assertFalse(false);
			test.log(LogStatus.WARNING, "VERIFICATION OF NUMBER OF TRANSACTION BLOCKS, Actual = "+XMLNodeListFile1.getLength()+ " in file1 ["+filePath1+"] And Expected = "+XMLNodeListFile2.getLength() +" in file2 ["+filePath2+"]");		}
	}
	

	@Test
	public void TransactionBlockValuesCheck() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
		
		String Xpath = "/Envelope/Body/response_card_posted_transactions/body/transaction_details/transaction[*]";
		List<String> XMLNodeValue1 = Utility.GetNodeValuesOfTransaction(filePath1, Xpath, 1);
		
		List<String> XMLNodeValue2 = Utility.GetNodeValuesOfTransaction(filePath2, Xpath, 1);
		
		boolean bval = XMLNodeValue1.equals(XMLNodeValue2);
		if (bval){
			assertTrue(bval);
			test.log(LogStatus.PASS, "COMPARISON ON NODE VALUES --:: Actual = "+XMLNodeValue1+ " And Expected = "+XMLNodeValue2);
		}{
			assertFalse(bval);
			test.log(LogStatus.FAIL, "COMPARISON ON NODE VALUES --:: Actual = "+XMLNodeValue1+ " And Expected = "+XMLNodeValue2);
		}	
	}
	
	@Test
	public void TransactionBlockNodesCheck() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
		
		String Xpath = "/Envelope/Body/response_card_posted_transactions/body/transaction_details/transaction[*]";
		List<String> XMLNodeValue1 = Utility.GetNodeNamesOfTransaction(filePath1, Xpath, 4);
	
		List<String> XMLNodeValue2 = Utility.GetNodeNamesOfTransaction(filePath2, Xpath, 4);
		
		boolean bval = XMLNodeValue1.equals(XMLNodeValue2);
		if (bval){
			assertTrue(bval);
			test.log(LogStatus.PASS, "COMPARISON ON NODE NAMES --:: Actual = "+XMLNodeValue1+ " And Expected = "+XMLNodeValue2);
		}{
			assertFalse(bval);
			test.log(LogStatus.FAIL, "COMPARISON ON NODE NAMES --:: Actual = "+XMLNodeValue1+ " And Expected = "+XMLNodeValue2);
		}	
	}

	@BeforeTest
	public void startTest() {
		report = new ExtentReports(System.getProperty("user.dir") + "\\ExtentReport\\ExtentReportResults.html");
		test = report.startTest("XMLComparisonTest");
	}

	@AfterTest
	public void afterTest() {
		report.endTest(test);
		report.flush();
	}

}