package framework.framework.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import framework.framework.bean.ConfigBean;
import framework.framework.bean.TestCaseBean;

public class MainController {
	private static HashMap<String, TestCaseBean> testcases = new HashMap<String, TestCaseBean>();
	private static TestCaseBean testbean = null;
	private static String testid = null;
	private static String targetlink = null;
	private static HashMap<String, String> testrun = new HashMap<String, String>();
	private Framework_Config fc;
	private static HSSFSheet testsheet;
	private ArrayList<ConfigBean> config = new ArrayList<ConfigBean>();
	private FrameworkCustomFunctions fcf;
	private static String filepath = "framework_config.xml";
	private ConfigBean configbean;
	private static String keywordfile = null;
	private static String steps = null;

	public static void main(String[] args) {
		MainController con = new MainController();
		con.executeFramework();
	}

	public void executeFramework() {

		String testno = "";
		fcf = new FrameworkCustomFunctions();
		try {
			InputStream is = new FileInputStream(new File(filepath));
			fc = new Framework_Config(is);
			config = fc.getFrameworkConfig();

			WebdriverFunctions.getDriver();
			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			for (int i = 0; i < config.size(); i++) {
				configbean = config.get(i);
				fcf.SetConfig(configbean);
				XmlSuite suite = new XmlSuite();
				suite.setName(configbean.getSuitename());
				FileInputStream fstream = new FileInputStream(configbean.getTestfile());
				keywordfile = configbean.getKeywordfile();
				steps = configbean.getStepfile();
				testsheet = fcf.createsheet(fstream, 0);

				testcases = fcf.getSeleniumTestCasesDetails(testsheet);

				Set<String> testsets = testcases.keySet();
				Iterator<String> testrows = testsets.iterator();
				List<XmlTest> alltests = new ArrayList<XmlTest>();
				while (testrows.hasNext()) {
					testno = testrows.next();
					testbean = testcases.get(testno);

					XmlTest test = new XmlTest(suite);
					test.setName(testbean.getTestcasename());
					List<XmlClass> classes = new ArrayList<XmlClass>();
					if (testbean.getTestexecutecon().equalsIgnoreCase("Yes")) {

						Class testclass = null;
						if (testbean.isExeuserclass()) {
							// testclass =
							// Class.forName(testbean.getClassname());
							classes.add(new XmlClass(testbean.getClassname()));
							test.setXmlClasses(classes);
						} else {
							// testclass =
							// Class.forName("framework.framework.code.ExecuteTestcase");
							classes.add(new XmlClass("framework.framework.code.ExecuteTestcase"));
							test.setXmlClasses(classes);
							test.addParameter("testid", testno);
						}
						alltests.add(test);
					}
				}
				suite.setTests(alltests);
				suites.add(suite);
			}
			TestNG tng = new TestNG();
			tng.setXmlSuites(suites);
			tng.run();
			WebdriverFunctions.closeDriver();
		} catch (Exception ex) {
			ex.printStackTrace();

			WebdriverFunctions.fail();
		} finally {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {

			}

		}
	}

	public TestCaseBean getTestBean() {
		return testbean;
	}

	public HashMap<String, TestCaseBean> getTestcase() {
		return testcases;
	}

}
