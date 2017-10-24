package com.control.store;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.platform.check.CheckParameter;
import com.service.interfaces.StoreServiceInterface;
import com.service.store.StoreService;
import com.until.errorcode.MAGICCODE;

public class ChangerPasswordController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {

        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();
        //平台token,非用户token
        String token = request.getParameter("token");
        String oldPassword = request.getParameter("oldpassword");
        String newPassword = request.getParameter("newpassword");
        String keyID = request.getParameter("keyID");
        
//        System.out.println("AUTH TOKEN:"+token);
//        System.out.println("AUTH KEYID:"+keyID);
//        System.out.println("AUTH OLDPASSWORD:"+oldPassword);
//        System.out.println("AUTH NEWPASSWORD:"+newPassword);
        
        
        int result = CheckParameter.checkParameter(token, keyID, oldPassword,newPassword);
        if (MAGICCODE.OK != result)
        {
            map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        
        StoreServiceInterface service = new StoreService();
        result = service.changePassowrd(token, oldPassword, newPassword, keyID);
        if(MAGICCODE.OK != result)
        {
            map.put("code", MAGICCODE.MAGIC_ERROR);
        }else
        {
            map.put("code", MAGICCODE.MAGIC_OK);
        }
        json = JSONObject.fromObject(map);
        stream.write(json.toString().getBytes("UTF-8"));
        return null;
    }

}
