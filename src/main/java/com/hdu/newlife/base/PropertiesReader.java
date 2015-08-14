package com.hdu.newlife.base;

import java.io.*;
import java.util.*;

public class PropertiesReader 
{
	
	public String getPropertykey(String name)
	{
		String configString = "";
		try
		{
			InputStream inconfig = this.getClass().getClassLoader().getResourceAsStream("config.properties");
			Properties prop = new Properties();
			prop.load(inconfig);
			configString = prop.getProperty(name);
			inconfig.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return configString;
	}
	
}
