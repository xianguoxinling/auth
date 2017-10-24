package com.model.store;

public class WxInfo
{
    private String id = null;
    private String magicKey = null;
    private String appID = null;
    private String appSecret = null;
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getMagicKey()
    {
        return magicKey;
    }
    public void setMagicKey(String magicKey)
    {
        this.magicKey = magicKey;
    }
    public String getAppID()
    {
        return appID;
    }
    public void setAppID(String appID)
    {
        this.appID = appID;
    }
    public String getAppSecret()
    {
        return appSecret;
    }
    public void setAppSecret(String appSecret)
    {
        this.appSecret = appSecret;
    }
    
}
