package com.hdu.newlife.dao;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

	int getUser() throws SQLException;

	List<Object> getAll() throws SQLException;

}
