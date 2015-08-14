package com.hdu.newlife.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hdu.newlife.bean.DummyBean;
import com.hdu.newlife.bean.ErrorBean;
import com.hdu.newlife.service.impl.UserServiceImpl;

@WebServlet(name="loginServlet", urlPatterns={"/loginServlet"})
public class LoginServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(LoginServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DummyBean dummyBean = new DummyBean();
		try {
			dummyBean.setSuccess(new UserServiceImpl().isLogin());
			writeJson(response, dummyBean);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			dummyBean.setSuccess(false);
			dummyBean.setError(new ErrorBean(e.getMessage()));
			writeJson(response, dummyBean);
		}
	}

}
