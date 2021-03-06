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

public class QueryTokenController implements Controller
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String userID = request.getParameter("userID");
        String keyID = request.getParameter("keyID");

        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();
        TokenServiceInterface tokenService = new TokenService();
        String token = tokenService.queryToken(userID, keyID);
        if (null != token)
        {
            map.put("token", token);
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
