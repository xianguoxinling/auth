package com.control.store;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.platform.base.UserCookieManager;
import com.service.interfaces.StoreServiceInterface;
import com.service.store.StoreService;
import com.until.errorcode.MAGICCODE;

import net.sf.json.JSONObject;

public class CheckPhone implements Controller
{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

        HttpSession session = request.getSession(); 
        String keyID = (String)session.getAttribute("keyID");
        if (null == keyID)
        {
            keyID = UserCookieManager.getCookieValueByName(request, "keyID");
            if(null == keyID)
            {
                return new ModelAndView("/store/storelogin.jsp");
            }
        }
	    
		Map<String, String> map = new HashMap<String, String>();
		JSONObject json = null;
		OutputStream stream = response.getOutputStream();

		String userName = request.getParameter("username");
		StoreServiceInterface service = new StoreService();

		int result = MAGICCODE.OK;
		if ((null != userName) && (!"".equals(userName)))
		{
			result = service.checkPhone(userName);
		}
		
		if (MAGICCODE.PHONE_NOT_REGISTER != result)
		{
		    map.put("valid", "false");
		} else
		{
		    map.put("valid", "true");
		}
		json = JSONObject.fromObject(map);
		stream.write(json.toString().getBytes("UTF-8"));

		return null;
	}

}
