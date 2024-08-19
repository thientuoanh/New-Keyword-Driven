package Data;

public class Contans 
{
	public static String HomePath = System.getProperty("user.dir")+"\\";
	public static String TestcaseFile = "TestCase.xlsx";
	public static String SheetName = "KeywordFramework";
	public static String ObjectName = "object.properties";
	public static int ResultColumn = 5;
	public static class CaseResult
	{
		public static int IsPass = 0;
		public static String RED = "red";
		public static String GREEN = "green";
		public static String PASS = "PASS";
		public static String FAIL = "FAIL";
	}
}
