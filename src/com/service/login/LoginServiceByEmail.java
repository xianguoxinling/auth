package com.service.login;

import com.model.user.UserLoginInfo;
import com.platform.log.MagicLogManager;
import com.until.encrypt.EncryptSHA;
import com.until.errorcode.MAGICCODE;

public class LoginServiceByEmail extends LoginService
{
	public int userRegister(UserLoginInfo userLoginInfo, String magicKey)
	{
		// 数据库查询是否有相同用户名
		int result = checkUserExist(userLoginInfo,magicKey);
		if (MAGICCODE.USER_NOT_EXIST != result)
		{
			return MAGICCODE.USER_EXIST;
		}

		if (null == encrypt)
		{
			encrypt = EncryptSHA.getInstances();
		}
		// 密码加密
		String password = encrypt.encrypt(userLoginInfo.getPassword());

		// 数据库存储,应该是创建用户相关的多个信息
		result = userLoginDBmanager.registerUserByEmail(userLoginInfo.getUsername(),
				password, userLoginInfo.getEmail(),magicKey);

		if (MAGICCODE.OK != result)
		{
			return result;
		}

		result = userLoginDBmanager.getUserIdByUserEmail(userLoginInfo,magicKey);
		if (MAGICCODE.OK != result)
		{
			for (int i = 0; i < 3; i++)
			{
				result = userLoginDBmanager.getUserIdByUserEmail(userLoginInfo,magicKey);
				if (MAGICCODE.OK == result)
				{
					break;
				}
			}
		}

		result = userLoginDBmanager.writeUserInfo(userLoginInfo,magicKey);
		MagicLogManager.info("Register", "New Register,user name is " +userLoginInfo.getUsername());
		
		return result;
	}

}
