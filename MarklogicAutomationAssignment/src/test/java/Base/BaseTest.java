package Base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public static WebDriver driver;
	public static Properties OR = new Properties();
	public static Properties Config = new Properties();
	public static WebDriverWait wait;
	public static WebElement dropdown;
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(BaseTest.class.getName());
	
	
	
	public void click(String key) {
		
		try{
			if(key.endsWith("_XPATH")) {
		
			driver.findElement(By.xpath(OR.getProperty(key))).click();
		}else if(key.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(key))).click();
		}else if(key.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(key))).click();
		}else if(key.endsWith("_CLASS")) {
			driver.findElement(By.className(OR.getProperty(key))).click();
		}
	}catch(Throwable t) {
		log.error(t.getMessage());
	}
		
		log.info("clicking on element------"+key);
	}
	
	
	public void type(String key, String value) {
		
		try{
			if(key.endsWith("_XPATH")) {
		
			driver.findElement(By.xpath(OR.getProperty(key))).sendKeys(value);
		}else if(key.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(key))).sendKeys(value);
		}else if(key.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(key))).sendKeys(value);
		}else if(key.endsWith("_CLASS")) {
			driver.findElement(By.className(OR.getProperty(key))).sendKeys(value);
		}
	}catch(Throwable t) {
		log.error(t.getMessage());
	}
		
		log.info("typing in element ------"+key+"entered value ------"+value);
		
	}
	
	public void select(String key, String value) {
	
		try{
			if(key.endsWith("_XPATH")) {
		
				dropdown=driver.findElement(By.xpath(OR.getProperty(key)));
		}else if(key.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(key)));
		}else if(key.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(key)));
		}else if(key.endsWith("_CLASS")) {
			driver.findElement(By.className(OR.getProperty(key)));
		}
	}catch(Throwable t) {
		log.error(t.getMessage());
	}
		
	    Select select = new Select(dropdown);
	    select.selectByValue(value);
		log.info("selecting element ----"+key+"with value ------"+value);
		
	}
	
	
   public String text(String key) {
	   String data=null;
		
		try{
			if(key.endsWith("_XPATH")) {
		
			data = driver.findElement(By.xpath(OR.getProperty(key))).getText();
		}else if(key.endsWith("_CSS")) {
			data = driver.findElement(By.cssSelector(OR.getProperty(key))).getText();
		}else if(key.endsWith("_ID")) {
			data = driver.findElement(By.id(OR.getProperty(key))).getText();
		}else if(key.endsWith("_CLASS")) {
			data = driver.findElement(By.className(OR.getProperty(key))).getText();
		}
	 }catch(Throwable t) {
		log.error(t.getMessage());
	}
		return data;
		
	}
	
	
	public boolean IsElementPresent(String key) {
		
		try{
			if(key.endsWith("_XPATH")) {
		
			driver.findElement(By.xpath(OR.getProperty(key)));
		}else if(key.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(key)));
		}else if(key.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(key)));
		}else if(key.endsWith("_CLASS")) {
			driver.findElement(By.className(OR.getProperty(key)));
		}
	}catch(Throwable t) {
		log.error(t.getMessage());
		return false;
	}
		
		log.info("Checking if element present ------"+key);
		return true;
	}
	
	
	public static void waitForElement(String key)
	{
		
		try{
			if(key.endsWith("_XPATH")) {
		
				wait = new WebDriverWait(driver,20);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(key))));
		   }else if(key.endsWith("_CSS")) {
			   wait = new WebDriverWait(driver,20);
			   wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(OR.getProperty(key))));
		}else if(key.endsWith("_ID")) {
			wait = new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(key))));
		}else if(key.endsWith("_CLASS")) {
			wait = new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(OR.getProperty(key))));
		}
	}catch(Throwable t) {
		log.error(t.getMessage());
	}
		
	}	
	
	@BeforeSuite
	public void setup() {
		
		Date d = new Date();
		System.setProperty("current.date", d.toString().replace(":", "_").replace(" ", "_"));
		PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
		
		if(driver==null) {
			
			try {
				fis = new FileInputStream("./src/test/resources/properties/Config.properties");
				Config.load(fis);
				log.info("config file loaded");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			try {
			fis = new FileInputStream("./src/test/resources/properties/OR.properties");
			OR.load(fis);
			log.info("OR file loaded");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			if(Config.getProperty("browser").equals("chrome")){
				
				WebDriverManager.chromedriver().setup();
				driver= new ChromeDriver();
				log.info("chrome browser launched");
				driver.manage().window().maximize();
				
			}else if(Config.getProperty("browser").equals("firefox")) {
				
				WebDriverManager.firefoxdriver().setup();
				driver= new FirefoxDriver();
				log.info("firefox browser launched");
			}else if(Config.getProperty("browser").equals("IE")) {
				
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				
			}
			
		}
	         
		driver.get(Config.getProperty("consoleurl"));	
		log.info("navigating to ---"+Config.getProperty("consoleurl"));
	
	}
	
    @AfterSuite
	public void close() {
		driver.quit();
		log.info("test execution completed");
		
	}
	
}
