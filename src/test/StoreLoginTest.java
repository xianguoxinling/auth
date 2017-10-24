package test;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.model.store.StoreLogin;
import com.service.store.StoreService;
import com.until.errorcode.MAGICCODE;

public class StoreLoginTest
{
    public StoreService storeService = null;
    
    @Before
    public void setUp() throws Exception
    {
        storeService = new StoreService();
    }

    @Test
    public void testRegisterLogin()
    {
        String phone = "12456789";
        String password = "124";
        String keyID = UUID.randomUUID().toString().replaceAll("-", "");
        int result = storeService.registerLogin(phone, password, keyID);
        assertEquals(MAGICCODE.OK,result);
    }

    @Test
    public void testLogin()
    {
        
        String phone = "12456789";
        String password = "124";
        StoreLogin storeLogin  = storeService.login(phone, password);
        System.out.println(storeLogin.getPassword());
//        assertEquals(MAGICCODE.OK,result);
        
    }

}
