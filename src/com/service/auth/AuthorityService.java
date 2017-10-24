package com.service.auth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.db.manager.AuthDBManager;
import com.model.auth.Authority;
import com.model.auth.Role;
import com.platform.check.CheckParameter;
import com.service.interfaces.AuthorityServiceInterface;
import com.service.interfaces.TokenServiceInterface;
import com.until.errorcode.MAGICCODE;
import com.until.num.UntilNum;

public class AuthorityService implements AuthorityServiceInterface
{

	private AuthDBManager dbManager = null;
	private TokenServiceInterface tokenService = null;

	public AuthorityService()
	{
		dbManager = new AuthDBManager();
		tokenService = new TokenService();
	}

	@Override
	public int createAuth(Authority authority,String keyID)
	{
		if (null == authority)
		{
			return MAGICCODE.OK;
		}
		
		int result = dbManager.checkAuthNameExist(authority.getName(), keyID);
		if(MAGICCODE.AUTH_NAME_EXIST == result)
		{
		    return result;
		}
		
		result = dbManager.createAuth(authority,keyID);
		if (MAGICCODE.OK != result)
		{

		}
		return result;
	}

	@Override
	public int createRole(Role role,String keyID)
	{
		if (null == role)
		{
			return MAGICCODE.ERROR;
		}
		int result = dbManager.checkRoleNameExist(role.getName(), keyID);
		if(MAGICCODE.ROLE_NAME_EXIST == result)
		{
		    return result;
		}
		
		result = dbManager.createRole(role,keyID);
		if (MAGICCODE.OK != result)
		{

		}
		return result;
	}

	@Override
	public int addAuthToRole(String authID, String roleID,String keyID)
	{
		if (null == authID || null == roleID || !(UntilNum.isNumber(authID)) || !UntilNum.isNumber(roleID))
		{
			return MAGICCODE.PARAMETER_ERROR;
		}

		int result = dbManager.addAuthToRole(authID, roleID,keyID);
		if (MAGICCODE.OK != result)
		{

		}
		return result;
	}

	public int delAuthFromRole(String authID,String roleID,String keyID)
	{
	    int result = CheckParameter.checkParameter(authID,roleID,keyID);
	    if(MAGICCODE.OK != result)
	    {
	        return result;
	    }
	    if(!(UntilNum.isNumber(authID)) || !(UntilNum.isNumber(roleID)))
	    {
	        return MAGICCODE.PARAMETER_ERROR;
	    }
	    
	    result = dbManager.delAuthFromRole(authID, roleID, keyID);
	    if(MAGICCODE.OK != result)
	    {
	        
	    }
	    return result;
	}
	
	@Override
	public int addAuthToPerson(String authID, String userID,String keyID)
	{
		if (null == authID || null == userID || !(UntilNum.isNumber(authID)))
		{
			return MAGICCODE.PARAMETER_ERROR;
		}
		int result = dbManager.addAuthToPerson(authID, userID,keyID);
		if (MAGICCODE.OK != result)
		{

		}
		return result;
	}

//	@Override
//	public int addShopAuth(String authID, String shopUUID,String keyID)
//	{
//		if (null == authID || null == shopUUID || !(UntilNum.isNumber(authID)))
//		{
//			return MAGICCODE.PARAMETER_ERROR;
//		}
//		int result = dbManager.addShopAuth(authID, shopUUID,keyID);
//		if (MAGICCODE.OK != result)
//		{
//
//		}
//		return result;
//	}
//
//	@Override
//	public int addShopRole(String roleID, String shopUUID,String keyID)
//	{
//		if (null == roleID || null == shopUUID || !(UntilNum.isNumber(roleID)))
//		{
//			return MAGICCODE.PARAMETER_ERROR;
//		}
//		int result = dbManager.addShopRole(roleID, shopUUID,keyID);
//		if (MAGICCODE.OK != result)
//		{
//
//		}
//		return result;
//	}

	@Override
	public int checkAuth(String token, String target, String authName,String keyID)
	{
		if (null == token || null == authName)
		{
			return MAGICCODE.PARAMETER_ERROR;
		}
		int result = tokenService.checkTokenValidity(token);
		if(MAGICCODE.OK != result)
		{
		    return result;
		}
		String userID = tokenService.queryUserIDByToken(token, keyID);
		List<Role> roleList = queryRoleByPerson(userID,keyID);
		Iterator<Role> it = roleList.iterator();
		while(it.hasNext())
		{
		    Role role = it.next();
		    result = checkAuthFromRole(authName,target,role.getId(),keyID);
		    if(MAGICCODE.OK == result)
		    {
		        return result;
		    }
		}
		
		result = dbManager.checkAuth(userID, target, authName,keyID);
		if(MAGICCODE.DB_ERROR == result)
		{
			
		}
		return result;
	}

    @Override
    public Authority queryAuthorityByName(String name, String keyID)
    {
        Authority authority = dbManager.queryAuthorityByName(name, keyID);
        return authority;
    }

    @Override
    public Role queryRoleByName(String name, String keyID)
    {
        Role role = dbManager.queryRoleByName(name, keyID);
        return role;
    }

    public List<Authority> queryAuthByRole(String roleID,String keyID)
    {
        List<Authority> authList = new ArrayList<Authority>();
        int result = dbManager.queryAuthorityByRole(roleID, keyID, authList);
        if(MAGICCODE.OK != result)
        {
            return null;
        }
        return authList;
    }
    
    @Override
    public int addRoleToPerson(String roleID, String personID, String keyID)
    {
        int result = MAGICCODE.OK;
//        List<Authority> authList = new ArrayList<Authority>();
//        int result = dbManager.queryAuthorityByRole(roleID, keyID, authList);
//        if(MAGICCODE.OK != result)
//        {
//            return MAGICCODE.ERROR;
//        }
        
        result = dbManager.addRoleToPerson(roleID, personID, keyID);
        if(MAGICCODE.OK != result)
        {
            return MAGICCODE.ERROR;
        }
        
//        Iterator<Authority> it = authList.iterator();
//        while(it.hasNext())
//        {
//            Authority authority = it.next();
//            result = addAuthToPerson(authority.getId(),personID,keyID);
//            if(MAGICCODE.DB_ERROR == result)
//            {
//                result = addAuthToPerson(authority.getId(),personID,keyID);
//            }
//        }
        
        return MAGICCODE.OK;
    }

    @Override
    public int delAuthFromPerson(String authID, String personID, String keyID)
    {
        int result = CheckParameter.checkParameter(authID, personID, keyID);
        if(MAGICCODE.OK != result)
        {
            return result;
        }
        
        result = dbManager.delAuthFromPerson(authID, personID, keyID);
        if(MAGICCODE.OK != result)
        {
            
        }
        return result;
    }

    @Override
    public int delRoleFromPerson(String roleID, String personID, String keyID)
    {
        int result = CheckParameter.checkParameter(roleID, personID, keyID);
        if(MAGICCODE.OK != result)
        {
            return result;
        }
        
//        List<Authority> authList = new ArrayList<Authority>();
//        result = dbManager.queryAuthorityByRole(roleID, keyID, authList);
//        if(MAGICCODE.OK != result)
//        {
//            return MAGICCODE.ERROR;
//        }
//        
//        Iterator<Authority> it = authList.iterator();
//        while(it.hasNext())
//        {
//            Authority authority = it.next();
//            result = delAuthFromPerson(authority.getId(),personID,keyID);
//            if(MAGICCODE.DB_ERROR == result)
//            {
//                result = delAuthFromPerson(authority.getId(),personID,keyID);
//                if(MAGICCODE.OK != result)
//                {
//                    
//                }
//            }
//        }
        
        result = dbManager.delRoleFromPerson(roleID, personID, keyID);
        if(MAGICCODE.OK != result)
        {
            
        }
        return result;
    }

    @Override
    public List<Authority> queryAuthByPerson(String userID, String keyID)
    {
        List<Authority> authList = new ArrayList<Authority>();
        int result = dbManager.queryAuthorityByPerson(userID, keyID, authList);
        if(MAGICCODE.OK != result)
        {
            return null;
        }
        
        List<Role> roleList = queryRoleByPerson(userID,keyID);
        for(Role role : roleList)
        {
            List<Authority> newAuthority = queryAuthByRole(role.getId(),keyID);
            for(Authority authority : newAuthority)
            {
                authList.add(authority);
            }
        }
        return authList;
    }

    public List<Role> queryRoleByPerson(String userID,String keyID)
    {
        List<Role> roleList = new ArrayList<Role>();
        int result = dbManager.queryRoleByPerson(userID, keyID, roleList);
        if(MAGICCODE.OK != result)
        {
            
        }
        return roleList;
    }
    
    public int checkAuthFromRole(String authName,String target,String roleID,String keyID)
    {
        int result = MAGICCODE.OK;
        result = dbManager.checkAuthFromRole(authName, target, roleID, keyID);
        return result;
    }
}
