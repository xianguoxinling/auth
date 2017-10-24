package com.control.auth;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.service.auth.AuthorityService;
import com.service.interfaces.AuthorityServiceInterface;
import com.until.errorcode.MAGICCODE;

public class CheckAuthController implements Controller
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String token = request.getParameter("token");
        String keyID = request.getParameter("keyID");
        String target = request.getParameter("target");
        String authName = request.getParameter("authName");

        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();

        AuthorityServiceInterface authorityService = new AuthorityService();
        int result = authorityService.checkAuth(token, target, authName, keyID);
        
        if (MAGICCODE.OK == result)
        {
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
