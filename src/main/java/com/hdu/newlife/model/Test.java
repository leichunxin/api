package com.hdu.newlife.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hdu.newlife.base.IBean;

/**
 * 
 * @author newlife
 * 
 */
public class Test implements IBean {

	public static final String _ID = "id";
	public static final String _USERNAME = "username";
	public static final String _PASSWORD = "password";
	private Integer id;
	private String username;
	private String password;

	public Test() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public List<Object> getResultSet(ResultSet rs) throws SQLException {
		List<Object> list = new ArrayList<Object>();
		while (rs.next()) {
			Test test = new Test();
			test.setId(rs.getInt(_ID));
			test.setUsername(rs.getString(_USERNAME));
			test.setPassword(rs.getString(_PASSWORD));
			list.add(test);
		}
		return list;
	}

}
