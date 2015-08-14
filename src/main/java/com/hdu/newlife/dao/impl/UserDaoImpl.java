package com.hdu.newlife.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.hdu.newlife.base.DBConnection;
import com.hdu.newlife.dao.UserDao;
import com.hdu.newlife.model.Test;

public class UserDaoImpl implements UserDao {

	@Override
	public int getUser() throws SQLException {
		if (null != getAll() && getAll().size() > 0) {
			return getAll().size();
		}
		return 0;
	}

	@Override
	public List<Object> getAll() throws SQLException {
		String cmdText = "select * from test";
		List<Object> list = DBConnection.executeQuery(cmdText, new Test());
		return list;
	}

}
