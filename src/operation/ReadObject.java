package operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import Data.Contans;
//import Data.Contans;
import testCases.ToolExecute;

public class ReadObject 
{

	Properties p = new Properties();
 
	public Properties getObjectRepository() throws IOException
	{
		// Read object repository file
		InputStream stream = new FileInputStream(new File(Contans.HomePath + Contans.ObjectName));
//		InputStream stream = new FileInputStream(new File(ToolExecute.txt_SelectObjectFile.getText()));
		// load all objects
		p.load(stream);
		return p;
	}
	
}
