package com.until.errorcode;

public class MAGICCODE
{
	// 平台错误码
	public static int PLATFORM_SUCCESS = 0;
	public static int PLATFORM_FAILED = 1;
	public static int PLATFORM_OK = 0;
	public static int OK = 0;
	public static int NOT_OK = 1;
	public static int ERROR = 1;
	public static int PLATFORM_ERROR = 1;
	public static int DB_ERROR = 2;
    public static int PLATFORM_EMAIL_NULL = 3;
    public static int PLATFORM_EMAIL_EXCETPION = 4;
//    public static int PARAMETER_ERROR = 5;
    
    // project , 10000-19999
    public static int PROJECT_NULL = 10000;
    public static int PROJECT_UUID_NULL = 10001;
	
    
    //auth ,20000-29999
    public static int AUTH_NAME_EXIST = 20001;
    public static int AUTH_NAME_NOT_EXIST = 20002;
    public static int ROLE_NAME_EXIST = 20003;
    public static int ROLE_NAME_NOT_EXIST = 20004;
    public static int TOKEN_NOT_VALIDITY = 20005;
    public static int PARAMETER_ERROR = 20006;
    
	//person login, 30000-39999
	public static int USER_NOT_EXIST = 30000;
	public static int USER_NAME_EXIST = 30001;
	public static int USER_EMAIL_EXIST = 30002;
	public static int USER_PHONE_EXIST = 30003;
	public static int USER_EXIST = 30004;
	public static int USER_CHANGE_VERITIFYCODE_ERROR = 30005;
	public static int USER_PASSWORD_NULL = 30006;
	public static int USER_PASSWORD_ERROR = 30007;
	public static int USER_USERID_NULL = 30008;
	public static int USER_USERNAME_NULL = 30010;
	public static int USER_HEADPIC_NULL = 30011;
	public static int USER_NOT_AUTH = 30012;
	
	//xcx 40000-49999
	public static String XCX_OK = "40000";
	public static String XCX_ERROR = "40001";
	public static String XCX_NOT_LOGIN = "40002";
	
	//shop 50000-59999
	public static int SHOP_NAME_NOT_EXIST = 50000;
	public static int SHOP_NAME_EXIST = 50001;
	
	public static String MAGIC_OK = "10000";
	public static String MAGIC_ERROR = "10001";
	public static String MAGIC_PARAMETER_ERROR = "10002";
	public static String MAGIC_NOT_LOGIN = "10003";
	public static String MAGIC_KEY_NULL = "10004";
	//auth 外部错误吗 , 60000-69999
//	public static String MAGIC_OK = "60000";
//	public static String MAGIC_ERROR = "60001";
//	public static String MAGIC_PARAMETER_ERROR = "60002";
//	public static String MAGIC_NOT_LOGIN = "60003";
	public static String MAGIC_USER_EXIST = "60004";
	public static String MAGIC_USER_NOT_EXIST = "60005";
	public static String MAGIC_PASSWORD_ERROR = "60006";
	
	public static String MAGIC_WXINFO_NULL = "70001";
	//storage , 70000-79999
	public static int STORAGE_FULL = 70001;
	
	//store 80000-89999
	public static int PHONE_NOT_REGISTER = 80000;
	public static int PHONE_REGISTER = 80001;
	public static int CAR_VIN_EXIST = 80002;
	public static int CAR_VIN_NOT_EXIST = 80003;
	
}