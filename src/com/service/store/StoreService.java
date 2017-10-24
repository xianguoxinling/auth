package com.service.store;

import java.util.ArrayList;
import java.util.List;

import com.db.manager.StoreDBManager;
import com.model.store.StoreLogin;
import com.model.store.WxInfo;
import com.service.auth.TokenService;
import com.service.interfaces.StoreServiceInterface;
import com.service.interfaces.TokenServiceInterface;
import com.until.encrypt.Encrypt;
import com.until.encrypt.EncryptSHA;
import com.until.errorcode.MAGICCODE;

public class StoreService implements StoreServiceInterface
{

    private StoreDBManager dbManager;
    public StoreService()
    {
        dbManager =  new StoreDBManager();
    }
    
    @Override
    public int registerLogin(String phone, String password, String keyID)
    {
        EncryptSHA  encrypt = EncryptSHA.getInstances();
        password = encrypt.encrypt(password);
//        System.out.println("phone"+phone);
//        System.out.println("password:"+password);
        int result = dbManager.checkPhoneRegister(phone);
        if(MAGICCODE.PHONE_NOT_REGISTER != result)
        {
            return result;
        }
        
        result = dbManager.storeRegister(phone, password, keyID);
        if(MAGICCODE.OK != result)
        {
            
        }
        
        return result;
    }

    @Override
    public StoreLogin login(String phone, String password)
    {
        EncryptSHA  encrypt = EncryptSHA.getInstances();
        password = encrypt.encrypt(password);
//        System.out.println("phone"+phone);
//        System.out.println("password:"+password);
        StoreLogin storeLogin = dbManager.login(phone, password);
        if(null != storeLogin)
        {
            return storeLogin;
        }
        return null;
    }

    @Override
    public int createWxInfo(WxInfo wxInfo)
    {
        int result = MAGICCODE.OK;
        if(null == wxInfo || null == wxInfo.getMagicKey())
        {
            return MAGICCODE.ERROR;
        }
        WxInfo queryInfo = dbManager.queryWxInfo(wxInfo.getMagicKey());
        if(null != queryInfo)
        {
            
            System.out.println("UPDATE WXINFO:"+wxInfo.getAppSecret());
            
            result = dbManager.updateWxInfo(wxInfo);
        }else
        {
            result = dbManager.createWxInfo(wxInfo);
        }
        if(MAGICCODE.OK != result)
        {
            
        }
        return result;
    }

    @Override
    public int updateWxInfo(WxInfo wxInfo)
    {
        int result = MAGICCODE.OK;
        if(null == wxInfo || null == wxInfo.getMagicKey())
        {
            return MAGICCODE.ERROR;
        }
        WxInfo queryInfo = dbManager.queryWxInfo(wxInfo.getMagicKey());
        if(null != queryInfo)
        {
            result = dbManager.updateWxInfo(wxInfo);
        }else
        {
            result = dbManager.createWxInfo(wxInfo);
        }
        if(MAGICCODE.OK != result)
        {
            
        }
        return result;
    }

    @Override
    public WxInfo queryWxInfo(String keyID)
    {
//        System.out.println("QUERY WXINFO KEYID:"+keyID);
        WxInfo queryInfo = dbManager.queryWxInfo(keyID);
        if(null == queryInfo)
        {
//            System.out.println("WXINFO NULL!!!!");
        }
        if(null != queryInfo)
        {
//            System.out.println("APPID:"+queryInfo.getAppID());
//            System.out.println("SECRETE:"+queryInfo.getAppSecret());
        }
        return queryInfo;
    }

    @Override
    public int changePassowrd(String token, String oldPassword, String newPassword, String keyID)
    {
        int result = MAGICCODE.OK;
        TokenServiceInterface tokenService = new TokenService();
        String userID = tokenService.queryUserIDByToken(token, keyID);
        if(null == userID)
        {
//            System.out.println("CHANGE PASSWORD USERID NULL!");
            return MAGICCODE.USER_NOT_EXIST;
        }
        
        Encrypt encrypt = EncryptSHA.getInstances();
        String oldpassword = encrypt.encrypt(oldPassword);
        result = dbManager.checkPassword(userID, oldpassword);
        if(MAGICCODE.OK != result)
        {
//            System.out.println("CHANGE PASSWORD ERROR!");
            return result;
        }
        String newpassword = encrypt.encrypt(newPassword);
        result = dbManager.updatePassword(userID, newpassword);
        if(MAGICCODE.OK != result)
        {
//            System.out.println("UPDATE PASSWORD ERROER!");
            return result;
        }
             
        return result;
    }

    @Override
    public int checkPhone(String phone)
    {
        return dbManager.checkPhoneRegister(phone);
    }

    @Override
    public int createSubAccount(String token, String phone, String password, String keyID)
    {
        int result = MAGICCODE.OK;
        
        result = checkPhone(phone);
        if(MAGICCODE.PHONE_NOT_REGISTER != result)
        {
            return result;
        }
        
        result = dbManager.createAccount(phone, password, keyID);
        if(MAGICCODE.OK != result)
        {
            
        }
        return result;
    }

    @Override
    public List<String> queryAccount(String token, String keyID)
    {
        List<String> accountList = new ArrayList<String>();
        int result = MAGICCODE.OK;
        result = dbManager.queryAccount(accountList, keyID);
        if(MAGICCODE.OK != result)
        {
            
        }
        
        return accountList;
    }

}
