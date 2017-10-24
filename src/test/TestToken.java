package test;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.service.auth.TokenService;
import com.service.interfaces.TokenServiceInterface;
import com.until.errorcode.MAGICCODE;
import com.until.random.RandomString;

public class TestToken
{
    private String keyID = null;
    private TokenServiceInterface service = null;
    
    @Before
    public void setUp() throws Exception
    {
        keyID = UUID.randomUUID().toString();
        service = new TokenService();
    }
    
    @Test
    public void testCreateToken()
    {
        String userID = RandomString.getRandomString(10);
        String token = service.createToken(userID, keyID);
        assertNotEquals(null,token);
        
        String newUserID = service.queryUserIDByToken(token, keyID);
        assertEquals(userID ,newUserID);
    }
    
    @Test
    public void testCheckTokenValidity()
    {
        String userID = RandomString.getRandomString(10);
        String token = service.createToken(userID, keyID);
        assertNotEquals(null,token);
        
        int result = service.checkTokenValidity(token);
        assertEquals(result ,MAGICCODE.OK);
        
    }
    
    @Test
    public void testCheckTokenNotValidity()
    {
//        String userID = RandomString.getRandomString(10);
//        String token = service.createToken(userID, keyID);
//        assertNotEquals(null,token);
        String token = "1jkg9mf2embhdqsmgdx7dd3ovbeq1gfqlmdu5g3rpo";
        int result = service.checkTokenValidity(token);
        assertEquals(result ,MAGICCODE.TOKEN_NOT_VALIDITY);
        
        
    }
}
