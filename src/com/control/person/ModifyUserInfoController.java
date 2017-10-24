package com.control.person;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.user.HeadPic;
import com.model.user.User;
import com.service.auth.TokenService;
import com.service.interfaces.PersonServiceInterface;
import com.service.interfaces.TokenServiceInterface;
import com.service.person.PersonServiceImpl;
import com.until.errorcode.MAGICCODE;

public class ModifyUserInfoController implements Controller
{
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
        String keyID = request.getParameter("keyID");
        String userID = request.getParameter("userID");
        String token = request.getParameter("token");
        OutputStream stream = response.getOutputStream();
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        
        PersonServiceInterface service = new PersonServiceImpl();
        if (null == userID)
        {
            if(null != token || !"".equals(token))
            {
                TokenServiceInterface tokenService = new TokenService();
                userID = tokenService.queryUserIDByToken(token, keyID);
            }
            else
            {
                map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
                json = JSONObject.fromObject(map);
                stream.write(json.toString().getBytes("UTF-8"));
                return null;
            }
        }
        
        String introduce = request.getParameter("description");
        String nickname  = request.getParameter("nickname");
        String sex = request.getParameter("sex");
        String percardnum = request.getParameter("idcard");
        String realName = request.getParameter("name");
        String address = request.getParameter("address");
        
        User user = new User();
        user.setIntroduce(introduce);
        user.setNickName(nickname);
        user.setSex(sex);
        user.setPercardNum(percardnum);
        user.setId(userID);
        user.setRealName(realName);
        user.setAddress(address);
        
        int result = service.modifyUserInfo(user,keyID);

        String headFile = request.getParameter("headpic");
        if(null == headFile || "".equals(headFile))
        {
            
        }
        
        HeadPic headPic2 = service.queryHeadPic(userID,keyID);
        if(null == headPic2)
        {
            result = service.addHeadPic(userID, headFile,keyID);
        }
        else
        {
            result = service.modifyHeadPic(userID, headFile,keyID);
        }
        
        if (MAGICCODE.OK == result)
        {
            map.put("code", MAGICCODE.XCX_OK);
        }
        else
        {
            map.put("code", MAGICCODE.XCX_ERROR);
        }
        json = JSONObject.fromObject(map);
        stream.write(json.toString().getBytes("UTF-8"));    
        
		return null;
	}
}