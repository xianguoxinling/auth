package com.service.interfaces;

import java.util.List;

import com.model.user.HeadPic;
import com.model.user.User;

public interface PersonServiceInterface
{
	public int changePassword(String userID,String password,String magicKey);
	public User queryUserInfoByEmail(String email,String magicKey);
    public User queryUserInfoByPhone(String phone,String magicKey);
	public int changeNickName(String userName, String nickName,String magicKey);
	public int changeIntroduce(String userName, String introduce,String magicKey);
	public int modifyUserInfo(User user,String magicKey);
	public User queryUserInfoByUserName(String userName,String magicKey);
	public User queryUserInfoByUserID(String userID,String magicKey);
	public List<User> queryUserByNickName(String nickName,String magicKey);
	public int leaveMessage(String userID, String messageUserID, String message,String magicKey);
	public String queryEmailByUserID(String userID,String magicKey);
	public int checkUserExist(String userID,String magicKey);
    public int addHeadPic(String userID,String picPath,String magicKey);
    public int modifyHeadPic(String userID,String picPath,String magicKey);
    public int delHeadPic(String userID,String magicKey);
    public HeadPic queryHeadPic(String userID,String magicKey);
    public String queryUserIDByOpenID(String openID,String magicKey);
    public String queryOpenIDByUserID(String userID, String magicKey);
	
}