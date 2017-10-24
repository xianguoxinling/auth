package com.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.model.store.StoreLogin;
import com.model.store.WxInfo;
import com.until.errorcode.MAGICCODE;

public class StoreDBManager
{
    protected DBManager dbManager = null;

    public int storeRegister(String phone, String password, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "INSERT INTO store_login (phone,password,magic_key) VALUES (?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phone);
            statement.setString(2, password);
            statement.setString(3, magicKey);
            statement.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
    
    public int createAccount(String phone,String password,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "INSERT INTO store_login (phone,password,magic_key,account_type) VALUES (?,?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phone);
            statement.setString(2, password);
            statement.setString(3, magicKey);
            statement.setString(4, "subaccount");
            statement.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
    
    public StoreLogin login(String phone, String password)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT * FROM store_login WHERE phone = ? and password = ?";
        PreparedStatement statement;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, phone);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
//                System.out.println("#####");
                StoreLogin storeLogin = new StoreLogin();
                storeLogin.setId(resultSet.getString("id"));
                storeLogin.setPhone(resultSet.getString("phone"));
                storeLogin.setMagicKey(resultSet.getString("magic_key"));
                storeLogin.setPassword(resultSet.getString("password"));
                return storeLogin;
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public int queryAccount(List<String> accountList,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT phone FROM store_login WHERE magic_key = ? and account_type=?";
        PreparedStatement statement;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, magicKey);
            statement.setString(2, "subaccount");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                String account = resultSet.getString("phone");
                accountList.add(account);
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
    
    public int checkPhoneRegister(String phone)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT * FROM store_login WHERE phone = ?";
        PreparedStatement statement;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, phone);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                return MAGICCODE.PHONE_REGISTER;
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.PHONE_NOT_REGISTER;
    }
    
    public int createWxInfo(WxInfo wxInfo)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "INSERT INTO wx_info (app_id,app_secret,magic_key) VALUES (?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, wxInfo.getAppID());
            statement.setString(2, wxInfo.getAppSecret());
            statement.setString(3, wxInfo.getMagicKey());
            statement.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
    
    public WxInfo queryWxInfo(String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT * FROM wx_info WHERE magic_key = ?";
        PreparedStatement statement;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                WxInfo wxInfo = new WxInfo();
                wxInfo.setMagicKey(magicKey);
                wxInfo.setAppID(resultSet.getString("app_id"));
                wxInfo.setAppSecret(resultSet.getString("app_secret"));
                wxInfo.setId(resultSet.getString("id"));
                return wxInfo;
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public int updateWxInfo(WxInfo wxInfo)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "update wx_info set app_id = ?, app_secret = ? where magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, wxInfo.getAppID());
            statement.setString(2, wxInfo.getAppSecret());
            statement.setString(3, wxInfo.getMagicKey());
            statement.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
    
    public int checkPassword(String id, String password)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT * FROM store_login WHERE id = ? and password = ?";
        PreparedStatement statement;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setLong(1, Long.parseLong(id));
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                return MAGICCODE.OK;
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.USER_PASSWORD_ERROR;
    }
    
    public int updatePassword(String id,String password)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "update store_login set password = ? where id = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            statement.setLong(2, Long.parseLong(id));
            statement.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
}
