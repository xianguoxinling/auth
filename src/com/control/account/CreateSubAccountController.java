package com.control.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.platform.base.UserCookieManager;
import com.service.store.StoreService;
import com.until.errorcode.MAGICCODE;

public class CreateSubAccountController implements Controller
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        HttpSession session = request.getSession(); 
        String token = (String)session.getAttribute("token");
        if (null == token)
        {
            token =  UserCookieManager.getCookieValueByName(request, "token");
            if (null == token)
            {
                return new ModelAndView("/store/storelogin.jsp");
            }
        }
        
        String keyID = (String)session.getAttribute("keyID");
        if (null == keyID)
        {
            keyID = UserCookieManager.getCookieValueByName(request, "keyID");
            if(null == keyID)
            {
                return new ModelAndView("/store/storelogin.jsp");
            }
        }
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        int result = MAGICCODE.OK;
        StoreService storeService = new StoreService();
        result = storeService.createSubAccount(token, phone, password, keyID);
        if (MAGICCODE.OK != result)
        {
            return new ModelAndView("/store/error.html");
        } 
        
        List<String> accountList = storeService.queryAccount(token, keyID);
        
        return new ModelAndView("/subaccount/accountlist.jsp","accountList",accountList);
    }

}
