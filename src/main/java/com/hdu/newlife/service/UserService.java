package com.hdu.newlife.service;

import java.sql.SQLException;

import com.hdu.newlife.bean.UserBean;
import com.hdu.newlife.bean.UserListBean;

public interface UserService {

	boolean isLogin() throws SQLException;

	UserListBean getAll();
	
	UserBean getById(int id);

}
