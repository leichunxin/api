package com.hdu.newlife.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

public class DBConnection {
	private static String driverClass = "";
	private static String jdbc_url = "";
	private static String jdbc_username = "";
	private static String jdbc_password = "";
	private static final Logger logger = Logger.getLogger(DBConnection.class);

	private DBConnection() {
		//
	}

	static {
		PropertiesReader propr = new PropertiesReader();
		driverClass = propr.getPropertykey("driverClass");
		jdbc_url = propr.getPropertykey("jdbc_url");
		jdbc_username = propr.getPropertykey("jdbc_username");
		jdbc_password = propr.getPropertykey("jdbc_password");
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			conn = null;
		}

		return conn;
	}

	public static List<Object> executeQuery(String cmdText, IBean bean) throws SQLException {
		return executeQuery(DBConnection.getConnection(), cmdText, null, bean);
	}

	public static List<Object> executeQuery(String cmdText, Object[] params, IBean bean) throws SQLException {
		return executeQuery(DBConnection.getConnection(), cmdText, params, bean);
	}

	public static List<Object> executeQuery(Connection conn, String cmdText, IBean bean) throws SQLException {
		return executeQuery(conn, cmdText, null, bean);
	}

	public static List<Object> executeQuery(Connection conn, String cmdText, Object[] params, IBean bean) throws SQLException {
		if (conn == null || conn.isClosed())
			return null;
		PreparedStatement cmd = conn.prepareStatement(cmdText);

		if (params != null && params.length > 0)
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof java.util.Date) {
					java.util.Date temp = (java.util.Date) params[i];
					java.sql.Date date = new java.sql.Date(temp.getTime());
					cmd.setDate(i + 1, date);
				} else {
					cmd.setObject(i + 1, params[i]);
				}
			}

		ResultSet rs = cmd.executeQuery();
		List<Object> list = bean.getResultSet(rs);

		if (!cmd.isClosed())
			cmd.close();
		conn.close();

		return list;
	}

	public static int executeUpdate(String cmdText) throws SQLException {
		return executeUpdate(DBConnection.getConnection(), cmdText, null);
	}

	public static int executeUpdate(String cmdText, Object[] params) throws SQLException {
		return executeUpdate(DBConnection.getConnection(), cmdText, params);
	}

	public static int executeUpdate(Connection conn, String cmdText) throws SQLException {
		return executeUpdate(conn, cmdText, null);
	}

	public static int executeUpdate(Connection conn, String cmdText, Object[] params) throws SQLException {
		if (conn == null || conn.isClosed())
			return -1;
		PreparedStatement cmd = conn.prepareStatement(cmdText);

		if (params != null && params.length > 0)
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof java.util.Date) {
					java.util.Date temp = (java.util.Date) params[i];
					java.sql.Date date = new java.sql.Date(temp.getTime());
					cmd.setDate(i + 1, date);
				} else {
					cmd.setObject(i + 1, params[i]);
				}
			}
		int lines = cmd.executeUpdate();

		if (!cmd.isClosed())
			cmd.close();
		conn.close();

		return lines;
	}

	/**
	 * 执行数据库操作：查询(返回无格式集合)
	 * 
	 * @param cmdText
	 *            SQL执行语句
	 * @return 执行结果，返回数据集DataTable
	 * @throws SQLException
	 */
	public static DataTable executeReader(String cmdText) throws SQLException {
		return executeReader(DBConnection.getConnection(), cmdText, null);
	}

	/**
	 * 执行数据库操作：查询(返回无格式集合)
	 * 
	 * @param cmdText
	 *            SQL执行语句
	 * @param parms
	 *            SQL语句参数
	 * @return 执行结果，返回数据集DataTable
	 * @throws SQLException
	 */
	public static DataTable executeReader(String cmdText, Object[] parms) throws SQLException {
		return executeReader(DBConnection.getConnection(), cmdText, parms);
	}

	/**
	 * 执行数据库操作：查询(返回无格式集合)
	 * 
	 * @param conn
	 *            数据库连接
	 * @param cmdText
	 *            SQL执行语句
	 * @return 执行结果，返回数据集DataTable
	 * @throws SQLException
	 */
	public static DataTable executeReader(Connection conn, String cmdText) throws SQLException {
		return executeReader(conn, cmdText, null);
	}

	/**
	 * 执行数据库操作：查询(返回无格式集合)
	 * 
	 * @param conn
	 *            数据库连接
	 * @param cmdText
	 *            SQL执行语句
	 * @param parms
	 *            SQL语句参数
	 * @return 执行结果，返回数据集DataTable
	 * @throws SQLException
	 */
	public static DataTable executeReader(Connection conn, String cmdText, Object[] parms) throws SQLException {
		if (conn == null || conn.isClosed())
			return null;

		PreparedStatement cmd = conn.prepareStatement(cmdText);
		
		if (parms != null && parms.length > 0) {
			for (int i = 0; i < parms.length; i++) {
				if (parms[i] instanceof java.util.Date) {
					java.util.Date temp = (java.util.Date) parms[i];
					java.sql.Date date = new java.sql.Date(temp.getTime());
					cmd.setDate(i + 1, date);
				} else {
					cmd.setObject(i + 1, parms[i]);
				}
			}
		}

		ResultSet rs = cmd.executeQuery();
		DataTable dt = new DataTable(rs);

		if (!cmd.isClosed())
			cmd.close();
		closeConnection(conn);

		return dt;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 *            数据库连接
	 */
	public static void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())// 判断当前数据库连接是否已经被关闭
			{
				conn.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
