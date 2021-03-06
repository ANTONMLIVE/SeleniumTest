package com.companyname.projectname.initializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Initializer {
	public static Logger addLog=LoggerFactory.getLogger(Initializer.class);
	public static FileInputStream configfis = null;
	public static Properties configProp = null;
	
	public static FileInputStream locatorfis = null;
	public static Properties locatorProp = null;
	
	public static WebDriver wd=null;
	
	public static void initialize() throws IOException
	{
		PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "com" + File.separator + "companyname" + File.separator + "projectname" + File.separator+ "config" + File.separator + "log4j.properties");
		
		configfis = new FileInputStream(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "com" + File.separator + "companyname" + File.separator + "projectname" + File.separator+ "config" + File.separator + "config.properties"));
		configProp = new Properties();
		configProp.load(configfis);
		
		locatorfis = new FileInputStream(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "com" + File.separator + "companyname" + File.separator + "projectname" + File.separator+ "config" + File.separator + "locators.properties"));
		locatorProp = new Properties();
		locatorProp.load(locatorfis);
		if(wd==null){
		if (configProp.getProperty("browser").equals("firefox"))
		{
			wd=new FirefoxDriver();
		} else if (configProp.getProperty("browser").equals("chrome"))
		{
			wd=new ChromeDriver();
		}  else if (configProp.getProperty("browser").equals("ie"))
		{
			wd=new InternetExplorerDriver();
		}
		}
		
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//wd.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		wd.manage().window().maximize();
	}
	
	public static WebElement getWebelementByXpath(String xpath) throws IOException
	{
		try{
			HighLightMyElement(wd.findElement(By.xpath(locatorProp.getProperty(xpath))));
		return wd.findElement(By.xpath(locatorProp.getProperty(xpath)));
		} catch (Throwable e)
		{
			File scrFile=((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+File.separator+"elementFailScreenshot"+File.separator+xpath+".jpg"));
		}
		return null;
	
	}
	
	public static void HighLightMyElement(WebElement element) throws IOException 
	{
		
		for (int i = 0; i < 7; i++)
			
		{ 
			JavascriptExecutor js = (JavascriptExecutor) wd; 
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", 
					element, "color: red; border: 5px solid red;"); 
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		} 
		
	}

}
