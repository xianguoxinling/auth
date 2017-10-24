package com.model.user;

import java.io.Serializable;

/**
 * @author xbayonet
 *
 */
public class UserLoginInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6939727123908935859L;
	
	private String username;
	private String password;
	private String email;
	private String phone;
	private String openid;
	
	private String id;
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getOpenid()
	{
		return openid;
	}
	public void setOpenid(String openid)
	{
		this.openid = openid;
	}
	
}
