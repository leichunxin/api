package com.hdu.newlife.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hdu.newlife.bean.LoginBean;

public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession(true);
		LoginBean user = (LoginBean) session.getAttribute("loginBean");
		String uri = ((HttpServletRequest) request).getRequestURI();
		if (uri.indexOf("login") == -1 && null == user) {
			((HttpServletResponse) response).sendRedirect("../index.jsp");
		} else {
			chain.doFilter(request, response);
		}
	}

	public static void main(String[] args) {
		System.out.println("werlogonfuaiso".indexOf("login"));
	}

	@Override
	public void destroy() {

	}

}
