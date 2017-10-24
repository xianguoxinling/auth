package com.control.store;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.store.WxInfo;
import com.service.interfaces.StoreServiceInterface;
import com.service.store.StoreService;
import com.until.errorcode.MAGICCODE;

public class CreateWxInfoController implements Controller
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String app_id = request.getParameter("app_id");
        String app_secret = request.getParameter("app_secret");
        String keyID = request.getParameter("keyID");

        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();

        WxInfo wxInfo = new WxInfo();
        wxInfo.setAppID(app_id);
        wxInfo.setAppSecret(app_secret);
        wxInfo.setMagicKey(keyID);
        StoreServiceInterface service = new StoreService();
        
        int result = service.createWxInfo(wxInfo);
//        System.out.println("AUTH CREATE WXINFO:"+result);
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