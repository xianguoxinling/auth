package com.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.user.UserLoginInfo;
import com.until.errorcode.MAGICCODE;

public class UserLoginDBManager
{
    protected DBManager dbManager = null;

    public int registerUserByEmail(String userName, String password, String email, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO login (username, password, email, magic_key) VALUES (?,?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, magicKey);
            statement.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }

    public int registerUserByPhone(String userName, String password, String phone, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO login (username, password, phone, magic_key) VALUES (?,?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.setString(3, phone);
            statement.setString(4, magicKey);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return RollBackManager.dealRollback(connection);
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }

    /**
     * 根据用户名检索出密码的密文
     * 
     * @param username
     * @return
     */
    public String searchPasswordByUsername(String userName,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select password from login where username = ? and magic_key = ?";
        String password = null;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, magicKey);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                password = resultSet.getString("password");
            }

        } catch (SQLException e)
        {
            return null;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return password;
    }

    /**
     * 根据Email检索出密码的密文
     * 
     * @param username
     * @return
     */
    public String searchPasswordByEmail(String email,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select password from login where email = ? and magic_key = ?";
        String password = null;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                password = resultSet.getString("password");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return password;
    }

    /**
     * 根据Phone检索出密码的密文
     * 
     * @param username
     * @return
     */
    public String searchPasswordByPhone(String phone,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select password from login where phone = ? and magic_key = ?";
        String password = null;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phone);
            statement.setString(2, magicKey);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                password = resultSet.getString("password");
            }
        } catch (SQLException e)
        {
            RollBackManager.dealRollback(connection);
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return password;
    }

    public int checkUserNameExist(String userName,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select id from login where username = ? and magic_key = ?";
        // try (Connection connection = dbManager.getConection())
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, magicKey);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                return MAGICCODE.USER_EMAIL_EXIST;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return MAGICCODE.USER_NOT_EXIST;
    }

    public int checkEmailExist(String email,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select email from login where email = ? and magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, magicKey);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                return MAGICCODE.USER_EMAIL_EXIST;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return MAGICCODE.USER_NOT_EXIST;
    }

    public int checkPhoneExist(String phone,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select phone from login where phone = ? and magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phone);
            statement.setString(2, magicKey);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                return MAGICCODE.USER_PHONE_EXIST;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.USER_NOT_EXIST;
    }

    public String searchUsernameByPhone(String phone,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select username from login where phone = ? and magic_key = ?";
        String username = null;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phone);
            statement.setString(2, magicKey);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                username = resultSet.getString("username");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return username;
    }

    public String searchUsernameByEmail(String email,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select username from login where email = ? and magic_key = ?";
        String username = null;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, magicKey);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                username = resultSet.getString("username");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return username;
    }

    public int getUserIdByUserName(UserLoginInfo userLoginInfo,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select id from login where username = ? and magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userLoginInfo.getUsername());
            statement.setString(2, magicKey);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                userLoginInfo.setId(resultSet.getString("id"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }

    public int getUserIdByUserEmail(UserLoginInfo userLoginInfo,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select id from login where email = ? and magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userLoginInfo.getEmail());
            statement.setString(2, magicKey);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                userLoginInfo.setId(resultSet.getString("id"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }

    public int getUserIdByUserPhone(UserLoginInfo userLoginInfo,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select id from login where phone = ? and magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userLoginInfo.getPhone());
            statement.setString(2, magicKey);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                userLoginInfo.setId(resultSet.getString("id"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }

    public int writeUserInfo(UserLoginInfo userLoginInfo,String magicKey)
    {

        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO user (id, memberlevel, isartist, username,phone, email,magic_key, registertime) VALUES (?,?,?,?,?,?,?,?)";
        // String id ;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, Long.parseLong(userLoginInfo.getId()));
            String phone = userLoginInfo.getPhone();
            // 电话注册到这一步，已经经过了短信验证，因此直接置为一
            if ("".equals(phone) || null == phone)
            {
                statement.setInt(2, 0);
            } else
            {
                statement.setInt(2, 1);
            }

            statement.setInt(3, 0);
            statement.setString(4, userLoginInfo.getUsername());
            statement.setString(5, userLoginInfo.getPhone());
            statement.setString(6, userLoginInfo.getEmail());
            statement.setString(7, magicKey);
            statement.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return RollBackManager.dealRollback(connection);
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
    
    public int checkThirdUserExist(String openid,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select openid from login where openid = ? and magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, openid);
            statement.setString(2, magicKey);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                return MAGICCODE.USER_EXIST;
            }
            connection.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.USER_NOT_EXIST;
    }

    public String queryUserIdByOpenId(String openid,String magicKey)
    {
        String userID = null;
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select id from login where openid = ? and magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, openid);
            statement.setString(2, magicKey);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                userID = resultSet.getString("id");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            } catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            return null;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return userID;

    }

    public int thirdPartRegister(String openid,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO login (openid,username,magic_key) VALUES (?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, openid);
            statement.setString(2, openid);
            statement.setString(3, magicKey);
            statement.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            } catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }

}
