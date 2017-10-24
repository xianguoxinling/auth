package com.service.login;

import com.model.user.UserLoginInfo;
import com.until.encrypt.EncryptSHA;

public class LoginServiceByPhone extends LoginService
{
	public int userRegister(UserLoginInfo userLoginInfo, String magicKey)
	{
		//数据库查询是否有相同用户名
		int result = checkUserExist(userLoginInfo,magicKey);
		if(0 == result)
		{
			return 1;
		}
		
		if(null == encrypt)
		{
			encrypt = EncryptSHA.getInstances();
		}
		//密码加密
		String password = encrypt.encrypt(userLoginInfo.getPassword());
		//短信验证验证？
		
		//数据库存储,应该是创建用户相关的多个信息
		result = userLoginDBmanager.registerUserByPhone(userLoginInfo.getUsername(), password, userLoginInfo.getPhone(),magicKey);	
		result = userLoginDBmanager.getUserIdByUserPhone(userLoginInfo,magicKey);
		if (0 != result)
		{
			for (int i = 0; i < 3; i++)
			{
				result = userLoginDBmanager.getUserIdByUserPhone(userLoginInfo,magicKey);
				if (0 == result)
				{
					break;
				}
			}
		}
		result = userLoginDBmanager.writeUserInfo(userLoginInfo,magicKey);
		
		return result;
	}	

}
