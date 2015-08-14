package com.hdu.newlife.service;

import java.sql.SQLException;

import com.hdu.newlife.bean.UserListBean;

public interface UserService {

	boolean isLogin() throws SQLException;

	UserListBean getAll();

}
