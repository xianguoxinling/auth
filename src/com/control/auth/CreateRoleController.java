package com.control.auth;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.auth.Role;
import com.service.auth.AuthorityService;
import com.service.interfaces.AuthorityServiceInterface;
import com.until.errorcode.MAGICCODE;

public class CreateRoleController implements Controller
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String name = request.getParameter("name");
        String explain = request.getParameter("explain");
        String keyID = request.getParameter("keyID");
        String target = request.getParameter("target");

        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();

        AuthorityServiceInterface authorityService = new AuthorityService();
        Role role = new Role();
        role.setName(name);
        role.setExplain(explain);
        role.setTarget(target);
        int result = authorityService.createRole(role, keyID);

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
