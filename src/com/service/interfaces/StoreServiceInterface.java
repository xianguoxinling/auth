package com.service.interfaces;

import java.util.List;

import com.model.store.StoreLogin;
import com.model.store.WxInfo;

public interface StoreServiceInterface
{
    public int registerLogin(String phone,String password,String keyID);
    public StoreLogin login(String phone,String password);
    public int createWxInfo(WxInfo wxInfo);
    public int updateWxInfo(WxInfo wxInfo);
    public WxInfo queryWxInfo(String keyID);
    
    public int changePassowrd(String token,String oldPassword,String newPassword,String keyID);
    public int checkPhone(String phone);
    
    public int createSubAccount(String token,String phone,String password,String keyID);
    public List<String> queryAccount(String token,String keyID);
    
}
