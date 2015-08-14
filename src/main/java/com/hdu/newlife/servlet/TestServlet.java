package com.hdu.newlife.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hdu.newlife.service.impl.UserServiceImpl;

@WebServlet(name="testServlet", urlPatterns={"/testServlet"})
public class TestServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		writeJson(response, new UserServiceImpl().getAll());
	}

}
