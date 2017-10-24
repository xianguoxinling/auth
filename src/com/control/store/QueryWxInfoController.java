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

public class QueryWxInfoController implements Controller
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();
        
        String keyID = request.getParameter("keyID");
        if(null == keyID || "".equals(keyID))
        {
            map.put("code", MAGICCODE.MAGIC_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        
        StoreServiceInterface service = new StoreService();
        WxInfo wxInfo = service.queryWxInfo(keyID);
        if(null == wxInfo)
        {
            map.put("code", MAGICCODE.MAGIC_WXINFO_NULL);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
        }else
        {
            json = JSONObject.fromObject(wxInfo);
            stream.write(json.toString().getBytes("UTF-8"));
        }
        
        return null;
    }

}
