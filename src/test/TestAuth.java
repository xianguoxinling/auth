package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.model.auth.Authority;
import com.model.auth.Role;
import com.service.auth.AuthorityService;
import com.service.auth.TokenService;
import com.service.interfaces.AuthorityServiceInterface;
import com.service.interfaces.TokenServiceInterface;
import com.until.errorcode.MAGICCODE;
import com.until.random.RandomString;

public class TestAuth
{
    private AuthorityServiceInterface authService = null;
    private TokenServiceInterface tokenService = null;
    private String keyID = null;
    
    @Before
    public void setUp() throws Exception
    {
        authService = new AuthorityService();
        tokenService = new TokenService();
        keyID = UUID.randomUUID().toString();
    }
    
    @Test
    public void testCreateAuth()
    {
        Authority authority = new Authority();
        String authName = RandomString.getRandomString(15);
        String authExplain = RandomString.getRandomString(100);
        String target = RandomString.getRandomString(16);
        authority.setTarget(target);
        authority.setName(authName);
        authority.setExplain(authExplain);
        
        
        int result = authService.createAuth(authority, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        result = authService.createAuth(authority, keyID);
        assertEquals(MAGICCODE.AUTH_NAME_EXIST,result);
    }
    
    @Test
    public void testCreateRole()
    {
        Role role = new Role();
        String name = RandomString.getRandomString(15);
        String explain = RandomString.getRandomString(150);
        String target = RandomString.getRandomString(16);
        role.setTarget(target);
        role.setName(name);
        role.setExplain(explain);
        int result = authService.createRole(role, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        result  = authService.createRole(role, keyID);
        assertEquals(MAGICCODE.ROLE_NAME_EXIST,result);
        
        Role newRole = authService.queryRoleByName(name, keyID);
        assertEquals(newRole.getName(),name);
    }
    
    @Test
    public void testAddAuthToRole()
    {
//        System.out.println(keyID);
        Authority authority = new Authority();
        String authName = RandomString.getRandomString(15);
        String authExplain = RandomString.getRandomString(100);
        String target = RandomString.getRandomString(16);
        authority.setTarget(target);
        authority.setName(authName);
        authority.setExplain(authExplain);
        
        int result = authService.createAuth(authority, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Role role = new Role();
        String name = RandomString.getRandomString(15);
        String explain = RandomString.getRandomString(150);
        role.setTarget(target);
        role.setName(name);
        role.setExplain(explain);
        result = authService.createRole(role, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority newAuth = authService.queryAuthorityByName(authName, keyID);
        assertEquals(authName,newAuth.getName());
        
        Role newRole = authService.queryRoleByName(name, keyID);
        assertEquals(name,newRole.getName());
        
        result = authService.addAuthToRole(newAuth.getId(), newRole.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        
        List<Authority> authList = authService.queryAuthByRole(newRole.getId(), keyID);
        assertEquals(1,authList.size());
    }
    
    @Test
    public void testAddAuthToPerson()
    {
        Authority authority = new Authority();
        String authName = RandomString.getRandomString(15);
        String authExplain = RandomString.getRandomString(100);
        String target = RandomString.getRandomString(16);
        authority.setTarget(target);
        authority.setName(authName);
        authority.setExplain(authExplain);
        int result = authService.createAuth(authority, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority newAuth = authService.queryAuthorityByName(authName, keyID);
        assertEquals(authName,newAuth.getName());
        result = authService.addAuthToPerson(newAuth.getId(), "1", keyID);
        assertEquals(MAGICCODE.OK,result);
    }
    
    @Test
    public void testAddRoleToPerson()
    {
        Authority authority = new Authority();
        String authName = RandomString.getRandomString(15);
        String authExplain = RandomString.getRandomString(100);
        String target = RandomString.getRandomString(16);
        authority.setTarget(target);
        authority.setName(authName);
        authority.setExplain(authExplain);
        int result = authService.createAuth(authority, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority authority2 = new Authority();
        String authName2 = RandomString.getRandomString(15);
        String authExplain2 = RandomString.getRandomString(100);
        authority2.setTarget(target);
        authority2.setName(authName2);
        authority2.setExplain(authExplain2);
        result = authService.createAuth(authority2, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Role role = new Role();
        String name = RandomString.getRandomString(15);
        String explain = RandomString.getRandomString(150);
        role.setTarget(target);
        role.setName(name);
        role.setExplain(explain);
        result = authService.createRole(role, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority a1 = authService.queryAuthorityByName(authName, keyID);
        assertEquals(a1.getName(),authName);
        Authority a2 = authService.queryAuthorityByName(authName2, keyID);
        assertEquals(a2.getName(),authName2);
        Role r1 = authService.queryRoleByName(name, keyID);
        assertEquals(r1.getName(),name);
        result = authService.addAuthToRole(a1.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        result = authService.addAuthToRole(a2.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        String userID = RandomString.getRandomString(10);
        result = authService.addRoleToPerson(r1.getId(), userID, keyID);
        
        List<Authority> authList = authService.queryAuthByPerson(userID, keyID);
        assertEquals(2,authList.size());
        
    }
    
    @Test
    public void testCheckAuth()
    {
        Authority authority = new Authority();
        String authName = RandomString.getRandomString(15);
        String authExplain = RandomString.getRandomString(100);
        String target = RandomString.getRandomString(16);
        authority.setTarget(target);
        authority.setName(authName);
        authority.setExplain(authExplain);
        int result = authService.createAuth(authority, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority authority2 = new Authority();
        String authName2 = RandomString.getRandomString(15);
        String authExplain2 = RandomString.getRandomString(100);
        authority2.setTarget(target);
        authority2.setName(authName2);
        authority2.setExplain(authExplain2);
        result = authService.createAuth(authority2, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Role role = new Role();
        String name = RandomString.getRandomString(15);
        String explain = RandomString.getRandomString(150);
        role.setTarget(target);
        role.setName(name);
        role.setExplain(explain);
        result = authService.createRole(role, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority a1 = authService.queryAuthorityByName(authName, keyID);
        assertEquals(a1.getName(),authName);
        Authority a2 = authService.queryAuthorityByName(authName2, keyID);
        assertEquals(a2.getName(),authName2);
        Role r1 = authService.queryRoleByName(name, keyID);
        assertEquals(r1.getName(),name);
        result = authService.addAuthToRole(a1.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        result = authService.addAuthToRole(a2.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        String userID = RandomString.getRandomString(10);
        result = authService.addRoleToPerson(r1.getId(), userID, keyID);
        
        List<Authority> authList = authService.queryAuthByPerson(userID, keyID);
        assertEquals(2,authList.size());
        
        String token = tokenService.createToken(userID, keyID);
        
        result = authService.checkAuth(token, target, authName, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        result = authService.checkAuth(token, target, "xxx", keyID);
        assertEquals(MAGICCODE.USER_NOT_AUTH,result);
    }
    
    @Test
    public void testDelAuthFromPerson()
    {
        Authority authority = new Authority();
        String authName = RandomString.getRandomString(15);
        String authExplain = RandomString.getRandomString(100);
        String target = RandomString.getRandomString(16);
        authority.setTarget(target);
        authority.setName(authName);
        authority.setExplain(authExplain);
        int result = authService.createAuth(authority, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority authority2 = new Authority();
        String authName2 = RandomString.getRandomString(15);
        String authExplain2 = RandomString.getRandomString(100);
        authority2.setTarget(target);
        authority2.setName(authName2);
        authority2.setExplain(authExplain2);
        result = authService.createAuth(authority2, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority a1 = authService.queryAuthorityByName(authName, keyID);
        assertEquals(a1.getName(),authName);
        Authority a2 = authService.queryAuthorityByName(authName2, keyID);
        assertEquals(a2.getName(),authName2);
        String userID = RandomString.getRandomString(10);
        result = authService.addAuthToPerson(a1.getId(), userID, keyID);
        assertEquals(MAGICCODE.OK,result);
        result = authService.addAuthToPerson(a2.getId(), userID, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        List<Authority> authList = authService.queryAuthByPerson(userID, keyID);
        assertEquals(2,authList.size());
        
        String token = tokenService.createToken(userID, keyID);
        
        result = authService.checkAuth(token, target, authName, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        result = authService.delAuthFromPerson(a1.getId(), userID, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        result = authService.checkAuth(token, target, authName, keyID);
        assertEquals(MAGICCODE.USER_NOT_AUTH,result);
    }
    
    @Test
    public void testDelRoleFromPerson()
    {
        Authority authority = new Authority();
        String authName = RandomString.getRandomString(15);
        String authExplain = RandomString.getRandomString(100);
        String target = RandomString.getRandomString(16);
        authority.setTarget(target);
        authority.setName(authName);
        authority.setExplain(authExplain);
        int result = authService.createAuth(authority, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority authority2 = new Authority();
        String authName2 = RandomString.getRandomString(15);
        String authExplain2 = RandomString.getRandomString(100);
        authority2.setTarget(target);
        authority2.setName(authName2);
        authority2.setExplain(authExplain2);
        result = authService.createAuth(authority2, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Role role = new Role();
        String name = RandomString.getRandomString(15);
        String explain = RandomString.getRandomString(150);
        role.setTarget(target);
        role.setName(name);
        role.setExplain(explain);
        result = authService.createRole(role, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority a1 = authService.queryAuthorityByName(authName, keyID);
        assertEquals(a1.getName(),authName);
        Authority a2 = authService.queryAuthorityByName(authName2, keyID);
        assertEquals(a2.getName(),authName2);
        Role r1 = authService.queryRoleByName(name, keyID);
        assertEquals(r1.getName(),name);
        result = authService.addAuthToRole(a1.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        result = authService.addAuthToRole(a2.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        String userID = RandomString.getRandomString(10);
        result = authService.addRoleToPerson(r1.getId(), userID, keyID);
        
        List<Authority> authList = authService.queryAuthByPerson(userID, keyID);
        assertEquals(2,authList.size());
        
        String token = tokenService.createToken(userID, keyID);
        
        result = authService.checkAuth(token, target, authName, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        result = authService.delRoleFromPerson(r1.getId(), userID, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        result = authService.checkAuth(token, target, authName, keyID);
        assertEquals(MAGICCODE.USER_NOT_AUTH,result);
    }
    
    @Test
    public void queryRoleByPerson()
    {
        Authority authority = new Authority();
        String authName = RandomString.getRandomString(15);
        String authExplain = RandomString.getRandomString(100);
        String target = RandomString.getRandomString(16);
        authority.setTarget(target);
        authority.setName(authName);
        authority.setExplain(authExplain);
        int result = authService.createAuth(authority, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority authority2 = new Authority();
        String authName2 = RandomString.getRandomString(15);
        String authExplain2 = RandomString.getRandomString(100);
        authority2.setTarget(target);
        authority2.setName(authName2);
        authority2.setExplain(authExplain2);
        result = authService.createAuth(authority2, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Role role = new Role();
        String name = RandomString.getRandomString(15);
        String explain = RandomString.getRandomString(150);
        role.setTarget(target);
        role.setName(name);
        role.setExplain(explain);
        result = authService.createRole(role, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Role role2 = new Role();
        String name2 = RandomString.getRandomString(15);
        String explain2 = RandomString.getRandomString(150);
        role2.setTarget(target);
        role2.setName(name2);
        role2.setExplain(explain2);
        result = authService.createRole(role2, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority a1 = authService.queryAuthorityByName(authName, keyID);
        assertEquals(a1.getName(),authName);
        Authority a2 = authService.queryAuthorityByName(authName2, keyID);
        assertEquals(a2.getName(),authName2);
        Role r1 = authService.queryRoleByName(name, keyID);
        assertEquals(r1.getName(),name);
        result = authService.addAuthToRole(a1.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        result = authService.addAuthToRole(a2.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        String userID = RandomString.getRandomString(10);
        result = authService.addRoleToPerson(r1.getId(), userID, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Role r2 = authService.queryRoleByName(name2, keyID);
        assertEquals(r2.getName(),name2);
        result = authService.addRoleToPerson(r2.getId(), userID, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        List<Role> rolelist = authService.queryRoleByPerson(userID, keyID);
        assertEquals(2,rolelist.size());
        
    }
    
    @Test
    public void testDelAuthFromRole()
    {
        Authority authority = new Authority();
        String authName = RandomString.getRandomString(15);
        String authExplain = RandomString.getRandomString(100);
        String target = RandomString.getRandomString(16);
        authority.setTarget(target);
        authority.setName(authName);
        authority.setExplain(authExplain);
        int result = authService.createAuth(authority, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority authority2 = new Authority();
        String authName2 = RandomString.getRandomString(15);
        String authExplain2 = RandomString.getRandomString(100);
        authority2.setTarget(target);
        authority2.setName(authName2);
        authority2.setExplain(authExplain2);
        result = authService.createAuth(authority2, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Role role = new Role();
        String name = RandomString.getRandomString(15);
        String explain = RandomString.getRandomString(150);
        role.setTarget(target);
        role.setName(name);
        role.setExplain(explain);
        result = authService.createRole(role, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Authority a1 = authService.queryAuthorityByName(authName, keyID);
        assertEquals(a1.getName(),authName);
        Authority a2 = authService.queryAuthorityByName(authName2, keyID);
        assertEquals(a2.getName(),authName2);
        Role r1 = authService.queryRoleByName(name, keyID);
        assertEquals(r1.getName(),name);
        result = authService.addAuthToRole(a1.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        result = authService.addAuthToRole(a2.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        String userID = RandomString.getRandomString(10);
        result = authService.addRoleToPerson(r1.getId(), userID, keyID);
        
        List<Authority> authList = authService.queryAuthByPerson(userID, keyID);
        assertEquals(2,authList.size());
        
        String token = tokenService.createToken(userID, keyID);
        
        result = authService.checkAuth(token, target, authName, keyID);
        assertEquals(MAGICCODE.OK,result);
        
        result = authService.delAuthFromRole(a1.getId(), r1.getId(), keyID);
        assertEquals(MAGICCODE.OK,result);
        
        result = authService.checkAuth(token, target, authName, keyID);
        assertEquals(MAGICCODE.USER_NOT_AUTH,result);
    }
}
