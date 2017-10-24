package com.service.login;

import com.model.user.UserLoginInfo;
import com.until.errorcode.MAGICCODE;

public class XCXLoginService extends LoginService
{
	public int userRegister(UserLoginInfo userLoginInfo, String magicKey)
	{
		int result = MAGICCODE.OK;
		if(null ==  userLoginInfo)
		{
			return MAGICCODE.ERROR;
		}
		String openid = userLoginInfo.getOpenid();
		if(null == openid || "".equals(openid))
		{
			return MAGICCODE.ERROR;
		}
		
		result = checkThirdUserExist(openid,magicKey);
		if(MAGICCODE.USER_EXIST == result)
		{
			return MAGICCODE.OK;
		}
		
		if(MAGICCODE.DB_ERROR == result)
		{
			return result;
		}
		
		result = userLoginDBmanager.thirdPartRegister(openid,magicKey);
		
		if(MAGICCODE.OK != result)
		{
			
		}
		
        String userID = userLoginDBmanager.queryUserIdByOpenId(openid,magicKey);
        if(null == userID)
        {
        	return MAGICCODE.ERROR;
        }
		
        userLoginInfo.setId(userID);
		result = userLoginDBmanager.writeUserInfo(userLoginInfo,magicKey);
		if(MAGICCODE.OK != result)
		{
			
		}
		
		return result;
	}
	
	public int checkThirdUserExist(String openid, String magicKey)
	{
		int result = MAGICCODE.OK;
		if(null == openid || "".equals(openid))
		{
			return MAGICCODE.ERROR;
		}
		
		result = userLoginDBmanager.checkThirdUserExist(openid,magicKey);
		return result;
	}
}
