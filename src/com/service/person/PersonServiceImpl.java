package com.service.person;

import java.util.List;

import com.db.manager.PersonDBManager;
import com.model.user.HeadPic;
import com.model.user.User;
import com.service.interfaces.PersonServiceInterface;
import com.until.encrypt.Encrypt;
import com.until.encrypt.EncryptSHA;
import com.until.errorcode.MAGICCODE;

public class PersonServiceImpl implements PersonServiceInterface
{

    private PersonDBManager dbManager = null;

    public PersonServiceImpl()
    {
        dbManager = new PersonDBManager();
    }

    public int changeNickName(String userName, String nickName, String magicKey)
    {
        int result = dbManager.changeNickName(userName, nickName, magicKey);

        if (MAGICCODE.OK != result)
        {

        }

        return result;
    }

    public int changeIntroduce(String userName, String introduce, String magicKey)
    {
        int result = 0;
        result = dbManager.changeIntroduce(userName, introduce, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }

    @Override
    public User queryUserInfoByUserName(String userName, String magicKey)
    {
        User user = null;
        user = dbManager.searchUserInfoByUserName(userName, magicKey);

        return user;
    }

    @Override
    public User queryUserInfoByUserID(String userID, String magicKey)
    {
        int result = MAGICCODE.OK;
        User user = null;
        user = dbManager.searchUserInfoByUserID(userID, magicKey);
        if (MAGICCODE.OK != result)
        {
            return null;
        }

        return user;
    }

    @Override
    public int leaveMessage(String userID, String messageUserID, String message, String magicKey)
    {
        return 0;
    }

    @Override
    public String queryEmailByUserID(String userID, String magicKey)
    {
        if (null == userID || "".equals(userID))
        {
            return null;
        }

        return dbManager.queryEmailByUserID(userID, magicKey);
    }

    public int checkUserExist(String userID, String magicKey)
    {
        if (null == userID || "".equals(userID))
        {
            return MAGICCODE.USER_USERID_NULL;
        }
        return dbManager.checkUserExist(userID, magicKey);
    }

    @Override
    public int modifyUserInfo(User user, String magicKey)
    {
        int result = MAGICCODE.OK;
        if (null == user)
        {
            return MAGICCODE.ERROR;
        }

        result = dbManager.updateUserInfo(user, magicKey);

        return result;
    }

    @Override
    public List<User> queryUserByNickName(String nickName, String magicKey)
    {
        return null;
    }

    @Override
    public int addHeadPic(String userID, String picPath, String magicKey)
    {
        if (null == userID || "".equals(userID))
        {
            return MAGICCODE.USER_USERID_NULL;
        }

        if (null == picPath || "".equals(picPath))
        {
            return MAGICCODE.USER_HEADPIC_NULL;
        }

        int result = dbManager.addHeadPic(userID, picPath, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }

    @Override
    public int modifyHeadPic(String userID, String picPath, String magicKey)
    {
        if (null == userID || "".equals(userID))
        {
            return MAGICCODE.USER_USERID_NULL;
        }

        if (null == picPath || "".equals(picPath))
        {
            return MAGICCODE.USER_HEADPIC_NULL;
        }

        int result = dbManager.modifyHeadPic(userID, picPath, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }

    @Override
    public int delHeadPic(String userID, String magicKey)
    {
        if (null == userID || "".equals(userID))
        {
            return MAGICCODE.USER_USERID_NULL;
        }

        int result = dbManager.delHeadPic(userID, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }

    @Override
    public HeadPic queryHeadPic(String userID, String magicKey)
    {
        if (null == userID || "".equals(userID))
        {
            return null;
        }

        return dbManager.queryHeadPic(userID, magicKey);
    }

    public String queryUserIDByOpenID(String openID, String magicKey)
    {
        return dbManager.queryUserIDByOpenID(openID, magicKey);
    }

    public String queryOpenIDByUserID(String userID, String magicKey)
    {
        return dbManager.queryOpenIDByUserID(userID, magicKey);
    }

    public User queryUserInfoByEmail(String email, String magicKey)
    {
        if (null == email || "".equals(email))
        {
            return null;
        }
        User user = dbManager.queryUserInfoByEmail(email, magicKey);
        if (null == user)
        {

        }
        return user;
    }

    public User queryUserInfoByPhone(String phone, String magicKey)
    {
        if (null == phone || "".equals(phone))
        {
            return null;
        }
        User user = dbManager.queryUserInfoByPhone(phone, magicKey);
        if (null == user)
        {

        }
        return user;

    }

    @Override
    public int changePassword(String userID, String password, String magicKey)
    {
        int result = MAGICCODE.OK;
        Encrypt encrypt = EncryptSHA.getInstances();
        String newpassword = encrypt.encrypt(password);
        result = dbManager.changePassword(userID, newpassword, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }
}
