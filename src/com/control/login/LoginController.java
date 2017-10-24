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
import com.service.auth.TokenService;
import com.service.interfaces.LoginServiceInterface;
import com.service.interfaces.TokenServiceInterface;
import com.service.login.LoginService;
import com.until.errorcode.MAGICCODE;

public class LoginController implements Controller
{
    LoginServiceInterface loginservice;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();
        String username = request.getParameter("username");
        String keyID = request.getParameter("keyID");
        String password = request.getParameter("password");
        int result = CheckParameter.checkParameter(username, keyID, password);
        if (MAGICCODE.OK != result)
        {
            map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }

        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUsername(username);
        userLoginInfo.setPassword(password);
        
        loginservice = new LoginService();
        result = loginservice.userLogin(userLoginInfo, keyID);
        if (MAGICCODE.OK == result)
        {
            TokenServiceInterface tokenService = new TokenService();
            String token = tokenService.createToken(userLoginInfo.getId(), keyID);
            if (null != token)
            {
                map.put("token", token);
                map.put("code", MAGICCODE.MAGIC_OK);
            } else
            {
                map.put("code", MAGICCODE.MAGIC_ERROR);
            }
        } else if (MAGICCODE.USER_NOT_EXIST == result)
        {
            map.put("code", MAGICCODE.MAGIC_USER_NOT_EXIST);
        } else
        {
            map.put("code", MAGICCODE.MAGIC_PASSWORD_ERROR);
        }

        json = JSONObject.fromObject(map);
        stream.write(json.toString().getBytes("UTF-8"));
        return null;
    }

}
