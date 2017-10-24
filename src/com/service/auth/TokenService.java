package com.service.auth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.db.manager.TokenDBManaer;
import com.model.token.Token;
import com.platform.check.CheckParameter;
import com.service.interfaces.PersonServiceInterface;
import com.service.interfaces.TokenServiceInterface;
import com.service.person.PersonServiceImpl;
import com.until.errorcode.MAGICCODE;
import com.until.random.RandomString;

public class TokenService implements TokenServiceInterface
{

    private TokenDBManaer dbManager = null;

    public TokenService()
    {
        dbManager = new TokenDBManaer();
    }

    @Override
    public String createToken(String userID, String keyID)
    {
        int result = CheckParameter.checkParameter(userID, keyID);
        if(MAGICCODE.OK != result)
        {
            return null;
        }
        
        String oldToken = queryToken(userID,keyID);
        if(null != oldToken)
        {
            return updateToken(userID,keyID);
        }
        String token = RandomString.getRandomString(32);
        token += userID;

        result = dbManager.createToken(userID, token, keyID);
        if (MAGICCODE.OK != result)
        {
            return null;
        }

        return token;
    }

    @Override
    public String queryToken(String userID, String keyID)
    {
        int result = CheckParameter.checkParameter(userID, keyID);
        if(MAGICCODE.OK != result)
        {
            return null;
        }
        
        Token token = dbManager.queryToken(userID, keyID);

        // 检查token是否过期，如果过期，则重新写入新的token并且返回。
        if (null == token)
        {
            return null;
        }
        
        return token.getToken();
    }

    public String queryUserIDByToken(String token, String keyID)
    {
        int result = CheckParameter.checkParameter(token, keyID);
        if(MAGICCODE.OK != result)
        {
            return null;
        }
        
        String userID = dbManager.queryUserIDByToken(token, keyID);
        if (null == userID)
        {

        }
        return userID;
    }
    
    public String queryOpenIDByToken(String token,String keyID)
    {
        String userID = queryUserIDByToken(token,keyID);
        if (null == userID)
        {
            return null;
        }
        PersonServiceInterface personService = new PersonServiceImpl();
        String openID = personService.queryOpenIDByUserID(userID, keyID);
        if(null == openID)
        {
            return null;
        }
        return openID;
        
    }

    @Override
    public int checkTokenValidity(String token)
    {
        if(MAGICCODE.OK != CheckParameter.checkParameter(token))
        {
            return MAGICCODE.PARAMETER_ERROR;
        }
        
        String createTime = dbManager.queryTokenTime(token);
        if (null == createTime)
        {
            return MAGICCODE.ERROR;
        }

        DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date nowDate = new Date();
            Date createDate = dataFormat.parse(createTime);
            long diff = nowDate.getTime() - createDate.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days >= 30)
            {
                return MAGICCODE.TOKEN_NOT_VALIDITY;
            }
        } catch (Exception e)
        {
            return MAGICCODE.ERROR;
        }

        return MAGICCODE.OK;
    }

    @Override
    public String updateToken(String userID, String keyID)
    {
        int result = CheckParameter.checkParameter(userID, keyID);
        if(MAGICCODE.OK != result)
        {
            return null;
        }
        String oldToken = queryToken(userID,keyID);
        if(null == oldToken)
        {
            return createToken(userID,keyID);
        }
        
        String token = RandomString.getRandomString(32);
        token += userID;
//        System.out.println("UPDATE　TOKEN TOKEN:"+token);
//        System.out.println("UPDATE TOKEN USERID:" + userID);
//        System.out.println("UPDATE TOKEN keyID:" + keyID);
        result = dbManager.updateToken(userID, token, keyID);
        if(MAGICCODE.OK != result)
        {
//            System.out.println("UPDATE　TOKEN ERROR");
        }
        return token;
    }
    
}
