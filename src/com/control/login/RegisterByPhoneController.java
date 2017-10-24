package com.control.login;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.user.UserLoginInfo;
import com.platform.check.CheckParameter;
import com.service.interfaces.LoginServiceInterface;
import com.service.login.LoginServiceByPhone;
import com.until.errorcode.MAGICCODE;

public class RegisterByPhoneController implements Controller
{

	private LoginServiceInterface loginservice;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String userName = request.getParameter("username");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String keyID = request.getParameter("keyID");
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();
        
		int result = CheckParameter.checkParameter(userName,phone,password,keyID);
		if(MAGICCODE.OK != result)
		{
            map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
		}

		UserLoginInfo userLoginInfo = new UserLoginInfo();
		userLoginInfo.setUsername(userName);
		userLoginInfo.setPhone(phone);
		userLoginInfo.setPassword(password);
		System.out.println("USER REGISTER:" + password);

		loginservice = new LoginServiceByPhone();
		result = loginservice.userRegister(userLoginInfo,keyID);

		if (MAGICCODE.OK != result)
		{
		    map.put("code", MAGICCODE.MAGIC_ERROR);
		}
		else
		{
		    map.put("code", MAGICCODE.MAGIC_OK);
		}
        json = JSONObject.fromObject(map);
        stream.write(json.toString().getBytes("UTF-8"));
        
        return null;
	}

}
