package com.hdu.newlife.bean;

/**
 * 
 * @author newlife
 * 
 */
public class UserBean {

	private Integer id;
	private String username;
	private String password;

	// Constructors

	/** default constructor */
	public UserBean() {
	}

	/** minimal constructor */
	public UserBean(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}