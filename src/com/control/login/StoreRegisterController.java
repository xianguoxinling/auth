package com.control.login;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.store.StoreLogin;
import com.model.user.UserLoginInfo;
import com.platform.check.CheckParameter;
import com.service.auth.TokenService;
import com.service.interfaces.LoginServiceInterface;
import com.service.interfaces.TokenServiceInterface;
import com.service.login.LoginService;
import com.service.store.StoreService;
import com.until.errorcode.MAGICCODE;

public class StoreRegisterController implements Controller
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        int result = CheckParameter.checkParameter(phone, password);
        if (MAGICCODE.OK != result)
        {
            map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }

        String keyID = UUID.randomUUID().toString().replaceAll("-", "");
        
        StoreService storeService = new StoreService();
//        System.out.println("PHONE:"+phone+" password:"+password+" keyID:"+keyID);
        result = storeService.registerLogin(phone, password, keyID);
//        System.out.println("STORE REGISTER RESULT:"+result);
        if (MAGICCODE.OK == result)
        {
            StoreLogin storeLogin = storeService.login(phone, password);
            if(null != storeLogin)
            {
//                System.out.println("ID:"+storeLogin.getId());
                TokenServiceInterface tokenService = new TokenService();
                String token = tokenService.createToken(storeLogin.getId(), keyID);
                if (null != token)
                {
                    map.put("token", token);
                    map.put("keyID", keyID);
                    map.put("code", MAGICCODE.MAGIC_OK);
                } else
                {
//                    System.out.println("CREATE TOKERN FAILED!");
                    map.put("code", MAGICCODE.MAGIC_ERROR);
                }
            }else
            {
//                System.out.println("LOGIN FAILED!");
                map.put("code", MAGICCODE.MAGIC_ERROR);
            }

        } else
        {
            map.put("code", MAGICCODE.MAGIC_ERROR);
        }

        json = JSONObject.fromObject(map);
        stream.write(json.toString().getBytes("UTF-8"));
        return null;
    }

}
