package operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import testCases.ToolExecute;

public class ReadObjectTool 
{
	Properties p = new Properties();
	 
	public Properties getObjectRepository() throws IOException
	{
		// Read object repository file
		InputStream stream = new FileInputStream(new File(ToolExecute.txt_SelectObjectFile.getText()));
		// load all objects
		p.load(stream);
		return p;
	}

}
