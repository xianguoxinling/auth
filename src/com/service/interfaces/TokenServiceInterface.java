package com.service.interfaces;

public interface TokenServiceInterface
{
    public String createToken(String userID,String keyID);
    public String queryToken(String userID,String keyID);
    public String queryUserIDByToken(String token,String keyID);
    public String queryOpenIDByToken(String token,String keyID);
    public int checkTokenValidity(String token);
    public String updateToken(String userID,String keyID);
}
