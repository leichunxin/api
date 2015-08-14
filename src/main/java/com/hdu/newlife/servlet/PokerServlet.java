package com.hdu.newlife.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hdu.newlife.core.PokerUtil;

@WebServlet(name = "pokerServlet", urlPatterns = { "/pokerServlet" })
public class PokerServlet extends BaseServlet {

	private static final long serialVersionUID = 5511371247259933430L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		writeJson(response, PokerUtil.initCardList());
	}

}
