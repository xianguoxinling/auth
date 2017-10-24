package com.service.login;

import com.db.manager.UserLoginDBManager;
import com.model.user.UserLoginInfo;
import com.service.interfaces.LoginServiceInterface;
import com.until.encrypt.Encrypt;
import com.until.encrypt.EncryptSHA;
import com.until.errorcode.MAGICCODE;

public class LoginService implements LoginServiceInterface
{

	protected UserLoginDBManager userLoginDBmanager;
	protected Encrypt encrypt;

	public LoginService()
	{
		userLoginDBmanager = new UserLoginDBManager();
	}

	public int userRegister(UserLoginInfo userLoginInfo,String magicKey)
	{
		return 0;
	}

	/*
	 * 应该支持用户名和邮箱两种方式的验证֤
	 */
	public int userLogin(UserLoginInfo userLoginInfo,String magicKey)
	{
		int result = 1;

//		System.out.println("***" + userLoginInfo.getPassword());
		if (null == userLoginDBmanager)
		{
			userLoginDBmanager = new UserLoginDBManager();
		}

		if ((null == userLoginInfo.getPassword()))
		{
			return MAGICCODE.USER_PASSWORD_NULL;
		}

		result = checkUserExist(userLoginInfo,magicKey);
		if (MAGICCODE.USER_NOT_EXIST == result)
		{
			return result;
		}

		// 根据用户名、邮件、电话号码检索出用户密码
		String passwordEncrypt = getEncryptPassword(userLoginInfo,magicKey);

		// 默认使用SHA512加密
		if (null == encrypt)
		{
			encrypt = EncryptSHA.getInstances();
		}
//
//		System.out.println("now get password is :" + userLoginInfo.getPassword());
//		 获取登陆信息的密码，并加密处理，两个密文进行比较，如果匹配则返回true
		result = encrypt.checkPassword(passwordEncrypt, userLoginInfo.getPassword());

		if (MAGICCODE.OK != result)
		{
			return result;
		}

		result = userLoginDBmanager.getUserIdByUserName(userLoginInfo,magicKey);
		if (MAGICCODE.OK != result)
		{
//			return result;
		}
		
		if(null == userLoginInfo.getId())
		{
			if(null == userLoginInfo.getEmail() || "".equals(userLoginInfo.getEmail()))
			{
				userLoginInfo.setEmail(userLoginInfo.getUsername());
			}
			result = userLoginDBmanager.getUserIdByUserEmail(userLoginInfo,magicKey);
		}
		
		if(null == userLoginInfo.getId())
		{
			if(null == userLoginInfo.getPhone() || "".equals(userLoginInfo.getPhone()))
			{
				userLoginInfo.setPhone(userLoginInfo.getUsername());
			}
			
			result = userLoginDBmanager.getUserIdByUserPhone(userLoginInfo,magicKey);
		}
		
		return result;

	}

	/**
	 * 根据用户名、邮件、电话号码检索出密文
	 * 
	 * @param userLoginInfo
	 * @return
	 */
	public String getEncryptPassword(UserLoginInfo userLoginInfo,String magicKey)
	{

		/*
		 * 登录信息统一放在UserLoginInfo里面,登陆同时支持用户名，邮箱和电话，在接受参数时并不做区分，统一在 username里面
		 */

		String passwordEncrypt = null;
		if (null != userLoginInfo.getUsername())
		{
			passwordEncrypt = userLoginDBmanager.searchPasswordByUsername(userLoginInfo
					.getUsername(),magicKey);
		}
		if (null == passwordEncrypt)
		{
			passwordEncrypt = userLoginDBmanager.searchPasswordByEmail(userLoginInfo
					.getUsername(),magicKey);
		}
		if (null == passwordEncrypt)
		{
			passwordEncrypt = userLoginDBmanager.searchPasswordByPhone(userLoginInfo
					.getUsername(),magicKey);
		}

		return passwordEncrypt;
	}

	/**
	 * 检测用户是否存在，可以在注册检验时使用
	 * 
	 * @param userLoginInfo
	 * @return
	 */
	public int checkUserExist(UserLoginInfo userLoginInfo,String magicKey)
	{
		int result = -1;
		if ((null != userLoginInfo.getUsername()) && ("" != userLoginInfo.getUsername()))
		{
			result = checkUserInfoExist(userLoginInfo.getUsername(),magicKey);
			if (MAGICCODE.USER_NOT_EXIST != result)
			{
				return MAGICCODE.USER_NAME_EXIST;
			}
		}
		
		
		if (null != userLoginInfo.getEmail() && ("" != userLoginInfo.getEmail()))
		{
			result = checkUserInfoExist(userLoginInfo.getEmail(),magicKey);
			if (MAGICCODE.USER_NOT_EXIST != result)
			{
				return MAGICCODE.USER_EMAIL_EXIST;
			}
		}
		if (null != userLoginInfo.getPhone() && ("" != userLoginInfo.getPhone()))
		{
			result = checkUserInfoExist(userLoginInfo.getPhone(),magicKey);
			if (MAGICCODE.USER_NOT_EXIST != result)
			{
				return MAGICCODE.USER_PHONE_EXIST;
			}
		}

		return result;
	}

	public int checkUserInfoExist(String user,String magicKey)
	{
		int result = -1;

		result = checkUserNameExist(user,magicKey);
		if (MAGICCODE.USER_NOT_EXIST != result)
		{
			return result;
		}

		result = checkEmailExist(user,magicKey);
		if (MAGICCODE.USER_NOT_EXIST != result)
		{
			return result;
		}

		result = checkPhoneExist(user,magicKey);
		return result;
	}

	/**
	 * 检验用户名是否存在
	 * 
	 * @param username
	 * @return
	 */
	public int checkUserNameExist(String username,String magicKey)
	{
		return userLoginDBmanager.checkUserNameExist(username,magicKey);
	}

	/**
	 * 检验email是否存在
	 * 
	 * @param email
	 * @return
	 */
	public int checkEmailExist(String email,String magicKey)
	{
		return userLoginDBmanager.checkEmailExist(email,magicKey);
	}

	/**
	 * 检验电话是否存在
	 * 
	 * @param phone
	 * @return
	 */
	public int checkPhoneExist(String phone,String magicKey)
	{
		return userLoginDBmanager.checkPhoneExist(phone,magicKey);
	}

	public int checkThirdUserExist(String openid, String magicKey)
	{
		return MAGICCODE.OK;
	}

    @Override
    public int userLogout(UserLoginInfo userLoginInfo, String magicKey)
    {
        return 0;
    }
}
