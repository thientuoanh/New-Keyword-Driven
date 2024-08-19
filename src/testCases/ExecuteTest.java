package testCases;

import java.util.Properties;

import operation.ReadObject;
import operation.UIOperation;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.Test;

import Data.Contans;
import Data.Contans.CaseResult;
import excelExportAndFileIO.ReadExcel;
import excelExportAndFileIO.WriteExcel;

/**
 * THIS IS THE EXAMPLE OF KEYWORD DRIVEN TEST CASE
 *
 */



public class ExecuteTest 
{

	@Test
	
    public void testLogin() throws Exception 
    {
    	String drivePath = Contans.HomePath + "chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", drivePath);
    
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		ReadExcel file = new ReadExcel();
        ReadObject object = new ReadObject();
        Properties allObjects =  object.getObjectRepository();
        UIOperation operation = new UIOperation(driver);
        
        //Read keyword sheet
        Sheet Sheet = file.readExcel(Contans.HomePath, Contans.TestcaseFile , Contans.SheetName);
        
        //Find number of rows in excel file
    	int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();
    	
    	//Create a loop over all the rows of excel file to read it
    	for (int i = 1; i < rowCount+1; i++) 
    	{
    		//Loop over all the rows
    		Row row = Sheet.getRow(i);
    		
    		//Check if the first cell contain a value, if yes, That means it is the new testcase name
    		if(row.getCell(0).toString().length()==0){
    		
    			//Print testcase detail on console
    			System.out.println(row.getCell(1).toString()+"----"+ row.getCell(2).toString()+"----"+
    			row.getCell(3).toString()+"----"+ row.getCell(4).toString());
    			
    			//Call perform function to perform operation on UI
    			operation.perform(allObjects, row.getCell(1).toString(), row.getCell(2).toString(),
    				row.getCell(3).toString(), row.getCell(4).toString());
    			
    			String PahtFile = Contans.HomePath + Contans.TestcaseFile;
    			if(CaseResult.IsPass == 1)
    			{
    				// Set PASS value and Green color when the case passed 
    				WriteExcel.setCellData(CaseResult.PASS, i, Contans.ResultColumn, Contans.SheetName, PahtFile, CaseResult.GREEN);
    			}
    			else 
    			{
    				// Set FAIL value and Red color when the case failed 
    				WriteExcel.setCellData(CaseResult.FAIL, i, Contans.ResultColumn, Contans.SheetName, PahtFile, CaseResult.RED);
    			}

    	    }
    		else
    		{
    			//Print the new  testcase name when it started
    				System.out.println("New Testcase->"+row.getCell(0).toString() +" Started");
    			}
    		}
	}

}
