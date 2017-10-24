package com.model.token;

public class Token
{
    private String id = null;
    private String token = null;
    private String userID = null;
    private String keyID = null;
    private String createTime = null;
    //token 过期时间
    private int active_time = 1;
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getToken()
    {
        return token;
    }
    public void setToken(String token)
    {
        this.token = token;
    }
    public String getUserID()
    {
        return userID;
    }
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
    public String getKeyID()
    {
        return keyID;
    }
    public void setKeyID(String keyID)
    {
        this.keyID = keyID;
    }
    public String getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    public int getActive_time()
    {
        return active_time;
    }
    public void setActive_time(int active_time)
    {
        this.active_time = active_time;
    }
}
