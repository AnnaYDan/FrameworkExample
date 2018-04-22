package framework.framework.code;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebdriverFunctions {
	private static WebDriver driver;

	public static void getDriver() {
		System.setProperty("webdriver.gecko.driver", "E:\\Softwares\\geckodriver\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	public static void openBrowser(String url) {
		driver.get(url);
	}

	public static List<WebElement> getWebElements(String locatortype,
			String locator) throws ElementNotVisibleException{
		List<WebElement> webelements = null;
		if (locatortype.equals("name")) {
			webelements = driver.findElements(By.name(locator));
		} else if (locatortype.equals("css")) {
			webelements = driver.findElements(By.cssSelector(locator));
		} else if (locatortype.equals("id")) {
			webelements = driver.findElements(By.id(locator));
		} else if (locatortype.equals("classname")) {
			webelements = driver.findElements(By.className(locator));
		} else if (locatortype.equals("link")) {
			webelements = driver.findElements(By.linkText(locator));
		} else if (locatortype.equals("tagname")) {
			webelements = driver.findElements(By.tagName(locator));
		} else if (locatortype.equals("xpath")) {
			webelements = driver.findElements(By.xpath(locator));
		}
		return webelements;
	}

	public static WebElement getWebElement(String locatortype, String locator) throws ElementNotVisibleException{
		WebElement webelement = null;
		if (locatortype.equals("name")) {
			webelement = driver.findElement(By.name(locator));
		} else if (locatortype.equals("css")) {
			webelement = driver.findElement(By.cssSelector(locator));
		} else if (locatortype.equals("id")) {
			webelement = driver.findElement(By.id(locator));
		} else if (locatortype.equals("classname")) {
			webelement = driver.findElement(By.className(locator));
		} else if (locatortype.equals("link")) {
			webelement = driver.findElement(By.linkText(locator));
		} else if (locatortype.equals("tagname")) {
			webelement = driver.findElement(By.tagName(locator));
		} else if (locatortype.equals("xpath")) {
			webelement = driver.findElement(By.xpath(locator));
		}
		return webelement;
	}
   
	public static void click_element(WebElement logoele) {
		// TODO Auto-generated method stub
		logoele.click();
		
	}

	public static boolean verifyTitle(String title) {
		String pagetitle = driver.getTitle();
		System.out.println(pagetitle);
		pagetitle = pagetitle.trim();
		if (title.equals(pagetitle)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void closeDriver() {
		// TODO Auto-generated method stub
		try{
		driver.close();
		}catch(Exception e){
		  e.printStackTrace();			
		}
	}

	public static String getCurrentURL() {
	
		return driver.getCurrentUrl();
	}
	public static Actions getActions() {
		// TODO Auto-generated method stub
		return new Actions(driver);
	}
	public static WebDriverWait getWaitDriver(int time){
		return new WebDriverWait(driver, time);
		
	}
	public static void waitForElement(String locatetype, String locator) throws Exception{
		WebDriverWait pageload = new WebDriverWait(driver, 120);
		  if(locatetype!=null && locatetype.length()>0){
		if (locatetype.equals("css"))
			pageload.until(ExpectedConditions.presenceOfElementLocated(By
					.cssSelector(locator)));
		else if (locatetype.equals("id"))
			pageload.until(ExpectedConditions.presenceOfElementLocated(By
					.id(locator)));
		else if (locatetype.equals("name"))
			pageload.until(ExpectedConditions.presenceOfElementLocated(By
					.name(locator)));
		else if (locatetype.equals("xpath"))
			pageload.until(ExpectedConditions.presenceOfElementLocated(By
					.xpath(locator)));
		else if (locatetype.equals("tagname"))
			pageload.until(ExpectedConditions.presenceOfElementLocated(By
					.tagName(locator)));
		else if (locatetype.equals("link"))
			pageload.until(ExpectedConditions.presenceOfElementLocated(By
					.linkText(locator)));
		else if (locatetype.equals("classname"))
			pageload.until(ExpectedConditions.presenceOfElementLocated(By
					.className(locator)));
		  }	
		  }
	public static void Type(String locatetype,String locate,String value){
		WebElement ele = getWebElement(locatetype, locate);
		ele.clear();
		ele.sendKeys(value);
	}

	public static void selectList(String locatetype, String locatevalue,
			String value) {
		WebElement selele = getWebElement(locatetype, locatevalue);
		new Select(selele).selectByVisibleText(value);
		
	}
	public static void AlertBox(String text){
		
		try{
		    String js = "alert('"+text+"');";
	      ((JavascriptExecutor)driver).executeScript(js);
	       Thread.sleep(3000);
	       js= "alert('Test is failed')";
	       ((JavascriptExecutor)driver).executeScript(js);
	       Thread.sleep(3000);
	       Alert alert = driver.switchTo().alert();
	       alert.dismiss();
		}
		catch(Exception e){
			
		}
	}

	public static void sleep(String value) throws InterruptedException{
	     Thread.sleep(Long.parseLong(value));
		
	}
	public static String getTitle(){
		String title = driver.getTitle();
		return title;
	}

	public static String getAttributeName(String locatetype, String locatevalue) {
	    int att_index = locatevalue.lastIndexOf('@');
	    if(att_index>1){
	  String att = locatevalue.substring(att_index+1);
	    locatevalue = locatevalue.substring(0,att_index);
	    System.out.println(locatevalue);
	   
	    String attr = getWebElement(locatetype, locatevalue).getAttribute(att);
	    return attr;
	    }
	    return null;
		
	}
	public static void fail(){
		try{
			Assert.fail();
		}
		catch(AssertionFailedError e){
			AlertBox("Test failed");
		    closeDriver();
		}
		
	}
}
