package com.control.login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;




import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.store.WxInfo;
import com.model.user.UserLoginInfo;
import com.service.auth.TokenService;
import com.service.interfaces.LoginServiceInterface;
import com.service.interfaces.StoreServiceInterface;
import com.service.interfaces.TokenServiceInterface;
import com.service.interfaces.PersonServiceInterface;
import com.service.login.XCXLoginService;
import com.service.person.PersonServiceImpl;
import com.service.store.StoreService;
import com.until.errorcode.MAGICCODE;

public class xcxLoginController implements Controller
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String code = request.getParameter("code");
        String keyID = request.getParameter("keyID");
        OutputStream stream = response.getOutputStream();
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        
        try
        {
            StoreServiceInterface storeService = new StoreService();
            WxInfo wxInfo = storeService.queryWxInfo(keyID);
            if(null == wxInfo)
            {
                map.put("code", MAGICCODE.XCX_ERROR);
                json = JSONObject.fromObject(map);
                stream.write(json.toString().getBytes("UTF-8"));
                return null;
                
            }
            
//            String strURL = "https://api.weixin.qq.com/sns/jscode2session?appid=wxb958b2bfa0406484&secret=5a87e7b638536ba3be40cf530d32203f&js_code=" + code
//                    + "&grant_type=authorization_code";
            String strURL = "https://api.weixin.qq.com/sns/jscode2session?appid="+wxInfo.getAppID()+"&secret="+wxInfo.getAppSecret()+"&js_code=" + code
                    + "&grant_type=authorization_code";
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }

            reader.close();
            httpConn.disconnect();

//             System.out.println(buffer.toString());

            JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
            String openid = jsonObject.getString("openid");
            LoginServiceInterface loginservice = new XCXLoginService();
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setOpenid(openid);
            int result = loginservice.userRegister(userLoginInfo, keyID);

            if (MAGICCODE.OK != result)
            {
                map.put("code", MAGICCODE.XCX_ERROR);
            } else
            {
                map.put("code", MAGICCODE.XCX_OK);
            }

            PersonServiceInterface personService = new PersonServiceImpl();

            String userID = personService.queryUserIDByOpenID(openid, keyID);
            if (null != userID)
            {
                TokenServiceInterface tokenService = new TokenService();
                String token = tokenService.createToken(userID, keyID);
                if (null != token)
                {
                    map.put("token", token);
                    map.put("openid",openid);
                    map.put("code", MAGICCODE.MAGIC_OK);
                } else
                {
                    map.put("code", MAGICCODE.MAGIC_ERROR);
                }
            } else
            {
                map.put("code", MAGICCODE.MAGIC_ERROR);
            }

            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String arts[])
    {
        try
        {
            String code = "041JXflw0wzyff1IWtkw0Rddlw0JXfle";
            String strURL = "https://api.weixin.qq.com/sns/jscode2session?appid=wx9b5bacf1b39b7ae5&secret=09e6b167264f8c6a9c1e08ff86f27a28&js_code=" + code
                    + "&grant_type=authorization_code";
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }

            reader.close();
            httpConn.disconnect();
            System.out.println(buffer.toString());

            JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
            String openid = jsonObject.getString("openid");
            System.out.println(openid);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
