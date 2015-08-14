package com.hdu.newlife.servlet;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@WebServlet(name="mySQLBasic", urlPatterns={"/test"})
public class MySQLBasic extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			/***** 1. 填写数据库相关信息(请查找数据库详情页) *****/
			String username = "188r7cEF4EP36hfMRrphAncn";// 用户名(api key);
			String password = "tHjwpE9QlUIb6GiVZP6v0MlN9xGwu5yg";// 密码(secret key)
			String driverName = "com.mysql.jdbc.Driver";
			String connName = "jdbc:mysql://" + "sqld.duapp.com" + ":" + "4050" + "/" + "jOljcTevPgxsvALmaoTk";

			/****** 2. 接着连接并选择数据库名为databaseName的服务器 ******/
			Class.forName(driverName);
			connection = (Connection) DriverManager.getConnection(connName, username, password);
			stmt = (Statement) connection.createStatement();
			/* 至此连接已完全建立，就可对当前数据库进行相应的操作了 */
			/* 3. 接下来就可以使用其它标准mysql函数操作进行数据库操作 */
			// 创建一个数据库表
			sql = "select * from test";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", rs.getObject("id"));
				map.put("username", rs.getString("username"));
				map.put("password", rs.getString("password"));
				list.add(map);
			}
			response.getWriter().write(JSON.toJSONString(list));
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
		} finally {
			try {
				if (null != rs && !rs.isClosed()) {
					rs.close();
				}
				if (null != stmt && !stmt.isClosed()) {
					stmt.close();
				}
				if (null != connection && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

}
