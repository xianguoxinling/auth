package com.model.user;

import java.io.Serializable;

public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7242094691074105625L;
	protected String id = "0";
	protected String userName = null;
	protected String nickName = null;
	
	//用于手机验证֤
	protected String phone  = null;
	//用与邮件验证֤
	protected String email  = null;
	//身份证信息，考虑是否需要
	protected String percardNum = null;
	
	protected String isArtist = null;
	
	protected String realName = null;
	protected String memberLevel = null;
	protected String address = null;
	protected String introduce = null;
	
	protected String sex = null;
	
	protected String headPic = null;
	protected String coinNum = null;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPercardNum()
	{
		return percardNum;
	}
	public void setPercardNum(String percardNum)
	{
		this.percardNum = percardNum;
	}
	public String getIsArtist()
	{
		return isArtist;
	}
	public void setIsArtist(String isArtist)
	{
		this.isArtist = isArtist;
	}
	public String getRealName()
	{
		return realName;
	}
	public void setRealName(String realName)
	{
		this.realName = realName;
	}
	public String getMemberLevel()
	{
		return memberLevel;
	}
	public void setMemberLevel(String memberLevel)
	{
		this.memberLevel = memberLevel;
	}
	public String getNickName()
	{
		return nickName;
	}
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}
	public String getIntroduce()
	{
		return introduce;
	}
	public void setIntroduce(String introduce)
	{
		this.introduce = introduce;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getHeadPic()
	{
		return headPic;
	}
	public void setHeadPic(String headPic)
	{
		this.headPic = headPic;
	}
	public String getCoinNum()
	{
		return coinNum;
	}
	public void setCoinNum(String coinNum)
	{
		this.coinNum = coinNum;
	}
	
	

}
