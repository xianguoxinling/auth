package com.control.person.headpic;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.model.user.HeadPic;
import com.service.auth.TokenService;
import com.service.interfaces.PersonServiceInterface;
import com.service.interfaces.TokenServiceInterface;
import com.service.person.PersonServiceImpl;
import com.until.errorcode.MAGICCODE;

public class AddHeadPicController implements Controller
{
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
        String keyID = request.getParameter("keyID");
        String userID = request.getParameter("userID");
        String token = request.getParameter("token");
        String filePath = request.getParameter("filePath");
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
        
		int result = service.addHeadPic(userID, filePath,keyID);
		if (result != MAGICCODE.OK)
		{

		}
		HeadPic headPic = service.queryHeadPic(userID,keyID);
		JSONObject head = JSONObject.fromObject(headPic);
		stream.write(head.toString().getBytes("UTF-8"));
		
		return null;
	}
}
