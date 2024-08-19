package operation;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Data.Contans.CaseResult;

public class UIOperation {
	WebDriver driver;

	public UIOperation(WebDriver driver) {
		this.driver = driver;
	}

	public void perform(Properties p, String operation, String objectName, String objectType, String value)
			throws Exception {

		System.out.println("");
		switch (operation.toUpperCase()) {
		case "CLICK":
			// Perform click
			try 
			{
				driver.findElement(this.getObject(p, objectName, objectType)).click();
				CaseResult.IsPass = 1;
			} 
			catch (Exception e) {
				CaseResult.IsPass = 0;
			}
			Thread.sleep(1000);
			break;

		case "SETTEXT":
			// Set text on control
			try 
			{
				driver.findElement(this.getObject(p, objectName, objectType)).sendKeys(value);
				CaseResult.IsPass = 1;
			} 
			catch (Exception e) {
				CaseResult.IsPass = 0;
			}
			Thread.sleep(1000);
			break;

		case "GOTOURL":
			// Get url of application
			try 
			{
				driver.get(p.getProperty(value));
				CaseResult.IsPass = 1;
			} 
			catch (Exception e) {
				CaseResult.IsPass = 0;
			}
			Thread.sleep(3000);
			break;

		case "SLEEP_3S":
			// Sleep 3 seconds
			try 
			{
				Thread.sleep(3000);
				CaseResult.IsPass = 1;
			} 
			catch (Exception e) {
				CaseResult.IsPass = 0;
			}

			break;
			
		case "SLEEP_6S":
			// Sleep 3 seconds
			try 
			{
				Thread.sleep(6000);
				CaseResult.IsPass = 1;
			} 
			catch (Exception e) {
				CaseResult.IsPass = 0;
			}

			break;	
			
		case "GETTEXT":
			// Get text of an element
			driver.findElement(this.getObject(p, objectName, objectType)).getText().trim();
			Thread.sleep(1000);
			break;
			
		case "CHECK_VALUE_DISPLAY":
			// Check value display correctly
			String oValue = driver.findElement(this.getObject(p, objectName, objectType)).getText().trim();
			if (oValue.contentEquals(value)) 
			{
				System.out.println("CHECK: Value displays correct");
				CaseResult.IsPass = 1;
			} 
			else 
			{
				System.out.println("CHECK: Value displays incorrect");
				CaseResult.IsPass = 0;
			}
			Thread.sleep(1000);
			break;

		case "CHECK_OBJECT_EXISTS":
			// Check object is exists
			List<WebElement> oObject = driver.findElements(this.getObject(p, objectName, objectType));
			if (oObject.size() != 0) 
			{
				System.out.println("CHECK: Object is exists");
				CaseResult.IsPass = 1;
			} 
			else 
			{
				System.out.println("CHECK: Object is not exists");
				CaseResult.IsPass = 0;
			}
			Thread.sleep(1000);
			break;

		default:
			break;
		}
	}

	/**
	 * Find element BY using object type and value
	 * 
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @return
	 * @throws Exception
	 */
	private By getObject(Properties p, String objectName, String objectType) throws Exception {
		// Find by xpath
		if (objectType.equalsIgnoreCase("XPATH")) {

			return By.xpath(p.getProperty(objectName));
		}
		// find by class
		else if (objectType.equalsIgnoreCase("CLASSNAME")) {

			return By.className(p.getProperty(objectName));

		}
		// find by name
		else if (objectType.equalsIgnoreCase("NAME")) {

			return By.name(p.getProperty(objectName));

		}
		// Find by css
		else if (objectType.equalsIgnoreCase("CSS")) {

			return By.cssSelector(p.getProperty(objectName));

		}
		// find by link
		else if (objectType.equalsIgnoreCase("LINK")) {

			return By.linkText(p.getProperty(objectName));

		}
		// find by partial link
		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {

			return By.partialLinkText(p.getProperty(objectName));

		} 
		else {
			throw new Exception("Wrong object type");
		}
	}

}
