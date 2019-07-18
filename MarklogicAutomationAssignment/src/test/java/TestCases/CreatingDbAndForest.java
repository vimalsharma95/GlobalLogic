package TestCases;


import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Base.BaseTest;

public class CreatingDbAndForest extends BaseTest {
	
	Date d = new Date();
	String DBName = "DB"+d.toString().replace(":", "_").replace(" ", "_");
	String ForestName = "Forest"+d.toString().replace(":", "_").replace(" ", "_");
	String path1  = System.getProperty("user.dir")+"/src/test/java/Utilities/shakespeare.2.00.xml";
    String path=path1.replace('\\','/');
	
	@Test(priority=1)
	public void createDbAndForest(){
		
		//waitForElement("query_text_CSS");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		 
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#query-text-space div[class= 'CodeMirror cm-s-default']")));
		
	    
		
	    //Selecting javascript value from the drop down list
		select("Dropdown_XPATH",OR.getProperty("dropdownValue"));
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		String query="var admin = require('/MarkLogic/admin.xqy'); var forestConfig = admin.getConfiguration(); forestConfig = admin.forestCreate(forestConfig, '"+ForestName+"', xdmp.host(), '', '', ''); admin.saveConfiguration(forestConfig); var dbConfig = admin.getConfiguration(); dbConfig = admin.databaseCreate(dbConfig, '"+DBName+"', xdmp.database('Security'), xdmp.database('Schemas')); admin.saveConfiguration(dbConfig); var forestAttachConfig = admin.getConfiguration(); forestAttachConfig = admin.databaseAttachForest(forestAttachConfig, xdmp.database('"+DBName+"'), xdmp.forest('"+ForestName+"')); admin.saveConfiguration(forestAttachConfig);";
		//System.out.println(query);
		//Query to create a database and forest
		
		WebElement codemirror = driver.findElement(By.cssSelector("#query-text-space div[class= 'CodeMirror cm-s-default']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
        
		//Entering query in the query text box
		js.executeScript("arguments[0].CodeMirror.setValue(\"" + query + "\");",codemirror);
		
		//Clicking on Run button for running the query
		click("RunButton_ID");
		
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"query-view-content\"]/div[2]/div/pre/code"))));
        
	}
	
	@Test(priority=2)
	public void verifyOutput() {
		
		
		//Getting the response text and storing it in a string for comparing it with expected string
		String output = text("ActualResponse_XPATH");
		
		//Expected String
		String validateoutput = OR.getProperty("Response_Expected");
		
		//Validating actual and expected result using assertion
		SoftAssert softassert = new SoftAssert();
		softassert.assertEquals(output, validateoutput);
		softassert.assertAll();
	}
	
   @Test(priority=3)
   public void InsertData() {
	  
	  driver.navigate().refresh();
	  
	  
	  WebDriverWait wait = new WebDriverWait(driver, 20);
		 
	  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#query-text-space div[class= 'CodeMirror cm-s-default']")));
	  
	  
	  WebElement dropdown = driver.findElement(By.xpath("//*[@id=\"source-databases\"]"));
	  
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  Select select = new Select(dropdown);
	  select.selectByVisibleText(DBName);
	  
	
	  String query="declareUpdate(); var pathToDocs = '"+path+"'; var xmlDocs = xdmp.filesystemDirectory(pathToDocs); xmlDocs.forEach(function(doc){xdmp.documentLoad(doc.pathname, {'uri': '/Docs/' + doc.filename})});";
	  System.out.println(query);
	  //Query to create a database and forest
	
   	  WebElement codemirror = driver.findElement(By.cssSelector("#query-text-space div[class= 'CodeMirror cm-s-default']"));
	  JavascriptExecutor js = (JavascriptExecutor)driver;
    
	  //Entering query in the query text box
	  js.executeScript("arguments[0].CodeMirror.setValue(\"" + query + "\");",codemirror);
	
  	  //Clicking on Run button for running the query
	  click("RunButton_ID");
	  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"query-view-content\"]/div[2]/div/pre/code"))));
		
	}
	
    @Test(priority=4)
	public void validateAdminInterface() {
		
		//Navigating to admin interface
		driver.get(Config.getProperty("adminurl"));
		
		//Clicking on the Database name which was created from query console
		
		driver.findElement(By.xpath("//*[(text()='"+DBName+"')]")).click();
		//click("DBName_XPATH");
		
		//Checking the database status by clicking on status tab
		click("DbStatus_XPATH");
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		
		js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("/html/body/table[1]/tbody/tr[3]/td[2]/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody/tr[2]/td/table[2]/tbody/tr[4]/td[4]")));
		
		//Getting total count of the document present in that database
		String count = text("DocumentCount_XPATH");
		
		//Printing the count
		log.info("total number of document ---"+count);
		System.out.println(count);
		
		/*SoftAssert softassert = new SoftAssert();
		softassert.assertEquals(count, expectedcount);
		softassert.assertAll();*/
		
		
		
		
	}

}
