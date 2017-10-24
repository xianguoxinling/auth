package com.service.interfaces;

import com.model.user.UserLoginInfo;

public interface LoginServiceInterface 
{
	
	public int userRegister(UserLoginInfo userLoginInfo,String magicKey);
	public int userLogin(UserLoginInfo userLoginInfo,String magicKey);
	public int userLogout(UserLoginInfo userLoginInfo,String magicKey);
	public int checkUserNameExist(String username,String magicKey);
	public int checkEmailExist(String email,String magicKey);
	public int checkPhoneExist(String phone,String magicKey);
	public int checkThirdUserExist(String openid,String magicKey);
	
}
