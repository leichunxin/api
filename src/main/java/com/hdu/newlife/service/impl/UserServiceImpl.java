package com.hdu.newlife.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.hdu.newlife.bean.ErrorBean;
import com.hdu.newlife.bean.UserBean;
import com.hdu.newlife.bean.UserListBean;
import com.hdu.newlife.dao.UserDao;
import com.hdu.newlife.dao.impl.UserDaoImpl;
import com.hdu.newlife.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();

	@Override
	public boolean isLogin() throws SQLException {
		return userDao.getUser() > 0;
	}

	@Override
	public UserListBean getAll() {
		UserListBean userListBean = new UserListBean();
		try {
			List<Object> objList = userDao.getAll();
			if(null != objList && objList.size() > 0) {
				userListBean.setResult(objList);
			}
		} catch (SQLException e) {
			userListBean.setSuccess(false);
			userListBean.setError(new ErrorBean(e.getMessage()));
		}
		return userListBean;
	}

	@Override
	public UserBean getById(int id) {
		System.out.println("from db ...");
		UserBean userBean = new UserBean();
		userBean.setId(id);
		userBean.setUsername("username" + id);
		userBean.setPassword("password" + id);
		return userBean;
	}

}
