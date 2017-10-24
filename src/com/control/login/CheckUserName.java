package com.control.login;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.service.login.LoginService;
import com.service.login.LoginServiceByEmail;
import com.until.errorcode.MAGICCODE;

import net.sf.json.JSONObject;

public class CheckUserName implements Controller
{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

		Map<String, String> map = new HashMap<String, String>();
		JSONObject json = null;
		OutputStream stream = response.getOutputStream();

		String userName = request.getParameter("username");
		String email = request.getParameter("email");
		String keyID = request.getParameter("keyID");
		String phone = request.getParameter("phone");
		
		LoginService loginService = new LoginServiceByEmail();

		int result = MAGICCODE.OK;
		if ((null != userName) && (!"".equals(userName)))
		{
			result = loginService.checkUserInfoExist(userName,keyID);
		}
		else if ((null != email) && (!"".equals(email)))
		{
			result = loginService.checkUserInfoExist(email,keyID);
		}
		else if(null != phone && "".equals(phone))
		{
		    result = loginService.checkUserInfoExist(phone, keyID);
		}else
		{
		    map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
		}
		
		if (MAGICCODE.USER_NOT_EXIST != result)
		{
			map.put("code", MAGICCODE.MAGIC_USER_EXIST);
		} else
		{
			map.put("code", MAGICCODE.MAGIC_USER_EXIST);
		}
		json = JSONObject.fromObject(map);
		stream.write(json.toString().getBytes("UTF-8"));

		return null;
	}

}
