package com.hdu.newlife.base.db;

import java.sql.*;
import java.util.*;

/**
 * DataBaseClass
 * 数据库访问类
 */
public class DBConnectionClass 
{
	private static String driver="";
	private static String dataBase="";
	private static String userName="";
	private static String userPass="";
	
	/**
	 * 静态构造函数：当程序运行的时候，由系统自动调用执行。
	 * 静态构造函数：他是和类相关联的，只要程序运行，静态构造函数就会被自动的调用只能被执行一次。
	 * 加载数据库的驱动
	 */
	static
	{
		try
		{
			PropertiesReader crp=new PropertiesReader();
			DBConnectionClass.driver=crp.GetConnectionKey("Driver");
			DBConnectionClass.dataBase=crp.GetConnectionKey("DataBase");
			DBConnectionClass.userName=crp.GetConnectionKey("UserName");
			DBConnectionClass.userPass=crp.GetConnectionKey("PassWord");
			Class.forName(DBConnectionClass.driver);
		}
		catch(Exception e)
		{
			System.out.println("加载数据库驱动出错！");
			e.printStackTrace();
		}
	}
	
	/**
	 * DataBaseClass()
	 * 构造函数:不能实例化
	 */
	private DBConnectionClass()
	{
		//
	}
	
	/**
	 * 创建数据库连接
	 * @return 数据库连接
	 */
	public static Connection GetConnection()
	{
		Connection conn=null;//建立和数据库的连接
		try
		{
			//创建数据库连接
			conn=DriverManager.getConnection(DBConnectionClass.dataBase,DBConnectionClass.userName,DBConnectionClass.userPass);
			return conn;
		}
		catch(Exception e)
		{
			System.out.println("创建数据库连接失败！");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 创建数据库操作对象
	 * @param conn 数据库连接
	 * @param cmdText SQL执行语句
	 * @param params SQL操作参数
	 * @return 执行结果，返回PreparedStatement
	 * @throws SQLException
	 */
	public static PreparedStatement CreateCommand(Connection conn,String cmdText,List<Object> params) throws SQLException
	{
		if (conn == null) return null;
		
		PreparedStatement cmd=conn.prepareStatement(cmdText);
		if (params!=null && params.size()>0)
		{
			for (int i=0;i<params.size();i++)
			{
				if(params.get(i) instanceof java.util.Date)
				{
					java.util.Date temp = (java.util.Date)params.get(i);
					java.sql.Date date = new java.sql.Date(temp.getTime());
					cmd.setDate(i+1, date);
				}
				else
				{
					cmd.setObject(i+1, params.get(i));
				}
			}
		}
		
		return cmd;
	}
	
	/**
	 * 执行数据库操作：添加，修改，删除操作
	 * @param cmdText SQL执行语句
	 * @return 执行结果，大于0代表成功
	 * @throws SQLException
	 */
	public static int execUpdate(String cmdText) throws SQLException
	{
		return DBConnectionClass.execUpdate(DBConnectionClass.GetConnection(),cmdText,null);
	}
	
	/**
	 * 执行数据库操作：添加，修改，删除操作
	 * @param cmdText SQL执行语句
	 * @param params SQL操作参数
	 * @return 执行结果，大于0代表成功
	 * @throws SQLException
	 */
	public static int execUpdate(String cmdText,List<Object> params) throws SQLException
	{
		return DBConnectionClass.execUpdate(DBConnectionClass.GetConnection(),cmdText,params);
	}
	
	/**
	 * 执行数据库操作：添加，修改，删除操作
	 * @param conn 数据库连接
	 * @param cmdText SQL执行语句
	 * @return 执行结果，大于0代表成功
	 * @throws SQLException
	 */
	public static int execUpdate(Connection conn,String cmdText) throws SQLException
	{
		return DBConnectionClass.execUpdate(conn,cmdText,null);
	}
	
	/**
	 * 执行数据库操作：添加，修改，删除操作
	 * @param conn 数据库连接
	 * @param cmdText SQL执行语句
	 * @param params SQL操作参数
	 * @return 执行结果，大于0代表成功
	 * @throws SQLException
	 */
	public static int execUpdate(Connection conn,String cmdText,List<Object> params) throws SQLException
	{
		if (conn == null) return 0;
		
		PreparedStatement cmd=DBConnectionClass.CreateCommand(conn,cmdText,params);
		int result=cmd.executeUpdate();
		
		if (!cmd.isClosed()) cmd.close();
		DBConnectionClass.CloseConnection(conn);
		
		return result;
	}
	
	/**
	 * 批量执行数据库操作：添加，修改，删除操作
	 * @param conn 数据库连接
	 * @param cmdList 数据库操作对象
	 * @return 执行结果，大于0代表成功
	 * @throws SQLException
	 */
	public static int execUpdate(Connection conn,PreparedStatement[] cmdList) throws SQLException
	{
		int result=0;
		
		if (conn == null) return 0;
		if (cmdList == null || cmdList.length<=0) return 0;
		
		conn.setAutoCommit(false);
		try
		{
			for (PreparedStatement cmd : cmdList)
			{
				result+=cmd.executeUpdate();
				if (!cmd.isClosed()) cmd.close();
			}
			conn.commit();
		}
		catch (SQLException e)
		{
			conn.rollback();
			result=-1;
		}
		conn.setAutoCommit(true);
		
		DBConnectionClass.CloseConnection(conn);
		
		return result;
	}
	
	/**
	 * 执行数据库操作：查询操作
	 * @param cmdText SQL执行语句
	 * @param className 反射类
	 * @return 执行结果，返回查询到的数据集合
	 * @throws SQLException
	 */
	public static List<Object> execQuery(String cmdText,String className) throws SQLException
	{
		return DBConnectionClass.execQuery(DBConnectionClass.GetConnection(),cmdText,null,className);
	}
	
	/**
	 * 执行数据库操作：查询操作
	 * @param cmdText SQL执行语句
	 * @param params  SQL操作参数
	 * @param className 反射类
	 * @return 执行结果，返回查询到的数据集合
	 * @throws SQLException
	 */
	public static List<Object> execQuery(String cmdText,List<Object> params,String className) throws SQLException
	{
		return DBConnectionClass.execQuery(DBConnectionClass.GetConnection(),cmdText,params,className);
	}
	
	/**
	 * 执行数据库操作：查询操作
	 * @param conn 数据库连接
	 * @param cmdText SQL执行语句
	 * @param className 反射类
	 * @return 执行结果，返回查询到的数据集合
	 * @throws SQLException
	 */
	public static List<Object> execQuery(Connection conn,String cmdText,String className) throws SQLException
	{
		return DBConnectionClass.execQuery(conn,cmdText,null,className);
	}
	
	/**
	 * 执行数据库操作：查询操作
	 * @param conn 数据库连接
	 * @param cmdText SQL执行语句
	 * @param params  SQL操作参数
	 * @param className 反射类
	 * @return 执行结果，返回查询到的数据集合
	 * @throws SQLException
	 */
	public static List<Object> execQuery(Connection conn,String cmdText,List<Object> params,String className) throws SQLException
	{
		if (conn == null) return null;
		
		PreparedStatement cmd=DBConnectionClass.CreateCommand(conn,cmdText,params);
		ResultSet rs=cmd.executeQuery();
		List<Object> list=Persistence.loadResultToSet(rs, className);
		
		if (!rs.isClosed()) rs.close();
		if (!cmd.isClosed()) cmd.close();
		DBConnectionClass.CloseConnection(conn);
		
		return list;
	}
	
	/**
	 * 执行数据库操作：查询操作
	 * @param conn 数据库连接
	 * @param cmdList 数据库操作对象
	 * @param className 反射类
	 * @return 执行结果，返回查询到的数据集合
	 * @throws SQLException
	 */
	public static List<Object> execQuery(Connection conn,PreparedStatement[] cmdList,String[] className) throws SQLException
	{
		List<Object> temp=new ArrayList<Object>();
		
		if (conn == null) return null;
		if (cmdList == null || cmdList.length<=0) return null;
		
		for (int i=0;i<cmdList.length;i++)
		{
			ResultSet rs=cmdList[i].executeQuery();
			List<Object> list=Persistence.loadResultToSet(rs, className[i]);
			if (!rs.isClosed()) rs.close();
			if (!cmdList[i].isClosed()) cmdList[i].close();
			temp.add(list);
		}
		
		DBConnectionClass.CloseConnection(conn);
		
		return temp;
	}
	
	/**
	 * 执行数据库操作：查询操作
	 * @param cmdText SQL执行语句
	 * @return 执行结果，返回查询到的数据集合
	 * @throws SQLException
	 */
	public static DataSet execReader(String cmdText) throws SQLException
	{
		return DBConnectionClass.execReader(DBConnectionClass.GetConnection(),cmdText,null);
	}
	
	/**
	 * 执行数据库操作：查询操作
	 * @param cmdText SQL执行语句
	 * @param params  SQL操作参数
	 * @return 执行结果，返回查询到的数据集合
	 * @throws SQLException
	 */
	public static DataSet execReader(String cmdText,List<Object> params) throws SQLException
	{
		return DBConnectionClass.execReader(DBConnectionClass.GetConnection(),cmdText,params);
	}
	
	/**
	 * 执行数据库操作：查询操作
	 * @param conn 数据库连接
	 * @param cmdText SQL执行语句
	 * @return 执行结果，返回查询到的数据集合
	 * @throws SQLException
	 */
	public static DataSet execReader(Connection conn,String cmdText) throws SQLException
	{
		return DBConnectionClass.execReader(conn,cmdText,null);
	}
	
	/**
	 * 执行数据库操作：查询操作
	 * @param conn 数据库连接
	 * @param cmdText SQL执行语句
	 * @param params  SQL操作参数
	 * @param className 反射类
	 * @return 执行结果，返回查询到的数据集合
	 * @throws SQLException
	 */
	public static DataSet execReader(Connection conn,String cmdText,List<Object> params) throws SQLException
	{
		if (conn == null) return null;
		
		PreparedStatement cmd=DBConnectionClass.CreateCommand(conn,cmdText,params);
		ResultSet rs=cmd.executeQuery();
		DataSet ds=new DataSet(rs);
		
		if (!rs.isClosed()) rs.close();
		if (!cmd.isClosed()) cmd.close();
		DBConnectionClass.CloseConnection(conn);
		
		return ds;
	}
	
	/**
	 * 执行数据库操作：查询操作
	 * @param conn 数据库连接
	 * @param cmdList 数据库操作对象
	 * @return 执行结果，返回查询到的数据集合
	 * @throws SQLException
	 */
	public static List<DataSet> execReader(Connection conn,PreparedStatement[] cmdList) throws SQLException
	{
		List<DataSet> temp=new ArrayList<DataSet>();
		
		if (conn == null) return null;
		if (cmdList == null || cmdList.length<=0) return null;
		
		for (int i=0;i<cmdList.length;i++)
		{
			ResultSet rs=cmdList[i].executeQuery();
			DataSet ds=new DataSet(rs);
			if (!rs.isClosed()) rs.close();
			if (!cmdList[i].isClosed()) cmdList[i].close();
			temp.add(ds);
		}
		
		DBConnectionClass.CloseConnection(conn);
		
		return temp;
	}
	
	/**
	 * 关闭数据库连接
	 * @param conn 数据库连接
	 */
	public static void CloseConnection(Connection conn)
	{
		try
		{
			if (conn!=null && !conn.isClosed())//判断数据库连接是否关闭
			{
				conn.close();
			}
		}
		catch(Exception e)
		{
			System.out.println("关闭数据库连接失败！");
			e.printStackTrace();
		}
	}
}
