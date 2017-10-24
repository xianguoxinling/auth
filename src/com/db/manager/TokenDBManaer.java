package com.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.token.Token;
import com.until.errorcode.MAGICCODE;

public class TokenDBManaer
{
    protected DBManager dbManager = null;

    public int createToken(String userID, String token, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "INSERT INTO token (token,userID,magic_key,token_time) VALUES (?,?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            statement.setString(2, userID);
            statement.setString(3, magicKey);
            statement.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
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
    
    public int updateToken(String userID, String token, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "update token set token = ?, token_time = ? where userID = ? and magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            statement.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
            statement.setString(3, userID);
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
    
    public Token queryToken(String userID,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT * from token WHERE userID = ? and magic_key = ?";
        PreparedStatement statement;
//        Token token = null;
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, userID);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                    return dbUntil.getTokenFromResultSet(resultSet);
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
    
    public String queryUserIDByToken(String token,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT userID from token WHERE token = ? and magic_key = ?";
        PreparedStatement statement;
        String userID = null;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, token);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                userID = resultSet.getString("userID");
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
         return userID;
    }
    
    public String queryTokenTime(String token)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT token_time from token WHERE token = ?";
        PreparedStatement statement;
        String crateTime = null;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                crateTime = resultSet.getString("token_time");
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
         return crateTime;
    }
    
    public int delToken(String token)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "delete from token where token = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
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
