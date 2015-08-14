package com.hdu.newlife.base.db;

import java.io.*;
import java.util.*;

/**
 * 配置文件读取类
 */
class PropertiesReader 
{
	/**
	 * 返回数据库连接字符串的数据
	 * @param KeyName连接名称（Driver：数据库驱动；DataBase：数据库连接；UserName：用户名称；UserPass：用户密码）
	 * @return 连接内容
	 */
	public String GetConnectionKey(String KeyName)
	{
		String configString=null;
		
		try
		{
			//读取CallDB.properties文本文件的内容，将其保存到inputStream对象中。
			InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("Config.properties");
			Properties props=new Properties();//创建操作CallDB.properties配置文件的对象
			props.load(inputStream);//将文件的内容和操作文件的对象相关联。
			configString=props.getProperty(KeyName);//输入键名，返回对应的键值。
		}
		catch(FileNotFoundException e)
		{
			System.out.println("数据库连接文件不存在！");
			e.printStackTrace();
		}
		catch(IOException e)
		{
			System.out.println("数据库连接文件读写错误！");
			e.printStackTrace();
		}
		return configString;
	}
}
