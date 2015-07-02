package com.companyname.projectname.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.companyname.projectname.excelreader.excelReader;
import com.companyname.projectname.initializer.Initializer;
import com.companyname.projectname.utils.ReportGenerator;

public class GoogleTransaltionPageTest extends Initializer {
	
	@Test(dataProvider="GoogleTransaltionPageTest")
	public void testHomePage(String testData,String sourcetext, String expectedResult) throws IOException
	{
		try 
		{
			initialize();
			
			wd.get(configProp.getProperty("URL"));
			
			getWebelementByXpath("Home_TxtArea_SourceText_Type_Xpath").sendKeys(sourcetext);//0
			String result = wd.findElement(By.xpath("Home_TxtArea_ResultText_Type_Xpath")).getText();
			if(result.equals("expectedResult")){
				ReportGenerator.setLog("HomePageTest is Passed", " ",testData+"_"+"Pass");
			}
			else {
				ReportGenerator.setLog("HomePageTest is failed. Expected Result not matching with Actual Result : ",result.toString(),testData+"_"+"Fail");
			}
			//ReportGenerator.setLog("HomePageTest is Passed", " ",testData+"_"+"Pass");
		} catch (Throwable t)
			{
				ReportGenerator.setLog("HomePageTest is failed", t.getMessage().toString(),testData+"_"+"Fail");
			}
	}
	
	@DataProvider(name="GoogleTransaltionPageTest")
	public static Object[][] getHomePageData() throws Exception
	{
		if(excelReader.RunModeVerification("GoogleTransaltionPageTest")){
		Object[][] data=excelReader.selectSingleDataOrMulitiData("GoogleTransaltionPageTest");
		return data;
		}
		
		return null;
	}

}
