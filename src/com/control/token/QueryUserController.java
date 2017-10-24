package com.control.token;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.service.auth.TokenService;
import com.service.interfaces.TokenServiceInterface;
import com.until.errorcode.MAGICCODE;

public class QueryUserController implements Controller
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String token = request.getParameter("token");
        String keyID = request.getParameter("keyID");

        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();
        TokenServiceInterface tokenService = new TokenService();
        String userID = tokenService.queryUserIDByToken(token, keyID);
        if (null != userID)
        {
            map.put("userID", userID);
            map.put("code", MAGICCODE.MAGIC_OK);
        } else
        {
            map.put("code", MAGICCODE.MAGIC_ERROR);
        }
        
        json = JSONObject.fromObject(map);
        stream.write(json.toString().getBytes("UTF-8"));

        return null;
    }

}
