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
import com.platform.check.CheckParameter;
import com.service.auth.TokenService;
import com.service.interfaces.TokenServiceInterface;
import com.service.store.StoreService;
import com.until.errorcode.MAGICCODE;

public class StoreLoginController implements Controller
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

//        String keyID = UUID.randomUUID().toString().replaceAll("-", "");

        StoreService storeService = new StoreService();
        StoreLogin storeLogin = storeService.login(phone, password);
        if(null == storeLogin)
        {
            map.put("code", MAGICCODE.MAGIC_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        TokenServiceInterface tokenService = new TokenService();
        String token = tokenService.createToken(storeLogin.getId(), storeLogin.getMagicKey());
        if (null != token)
        {
//            System.out.println("AUTH:"+token+"***"+storeLogin.getMagicKey());
            map.put("token", token);
            map.put("keyID", storeLogin.getMagicKey());
            map.put("code", MAGICCODE.MAGIC_OK);
        } else
        {
            map.put("code", MAGICCODE.MAGIC_ERROR);
//            System.out.println("ERROR");
        }

        json = JSONObject.fromObject(map);
        stream.write(json.toString().getBytes("UTF-8"));
        return null;
    }

}
