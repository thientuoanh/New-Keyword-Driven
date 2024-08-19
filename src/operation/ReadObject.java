package operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import Data.Contans;

public class ReadObject 
{

	Properties p = new Properties();
 
	public Properties getObjectRepository() throws IOException
	{
		// Read object repository file
		InputStream stream = new FileInputStream(new File(Contans.HomePath + Contans.ObjectName));
		// load all objects
		p.load(stream);
		return p;
	}
	
}
