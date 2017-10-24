package com.service.interfaces;

import java.util.List;

import com.model.auth.Authority;
import com.model.auth.Role;

public interface AuthorityServiceInterface
{
	public int createAuth(Authority authority,String keyID);
	public int createRole(Role role,String keyID);
	public int addAuthToRole(String authID,String roleID,String keyID);
	public int delAuthFromRole(String authID,String roleID,String keyID);
	public int addAuthToPerson(String authID,String userID,String keyID);
	public int checkAuth(String token,String target,String authName,String keyID);
	public Authority queryAuthorityByName(String name,String keyID);
	public Role queryRoleByName(String name,String keyID);
	public int addRoleToPerson(String roleID,String personID,String keyID);
	public int delAuthFromPerson(String authID,String personID,String keyID);
	public int delRoleFromPerson(String roleID,String personID,String keyID);
	
	public List<Authority> queryAuthByPerson(String userID,String keyID);
	public List<Authority> queryAuthByRole(String roleID,String keyID);
	
	public List<Role> queryRoleByPerson(String userID,String keyID);
	public int checkAuthFromRole(String authName,String target,String roleID,String keyID);
}
