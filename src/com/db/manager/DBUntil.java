package com.db.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.auth.Authority;
import com.model.auth.Role;
import com.model.token.Token;
import com.model.user.User;

public class DBUntil
{
    public Authority getAuthorityFromResultSet(ResultSet resultSet)
    {
        Authority authority = new Authority();
        try
        {
            authority.setId(resultSet.getString("id"));
            authority.setName(resultSet.getString("name"));
            authority.setTarget(resultSet.getString("target"));
            authority.setExplain(resultSet.getString("explains"));
            
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return authority;
    }
    
    public Role getRoleFromResultSet(ResultSet resultSet)
    {
        Role role = new Role();
        try
        {
            role.setId(resultSet.getString("id"));
            role.setName(resultSet.getString("name"));
            role.setTarget(resultSet.getString("target"));
            role.setExplain(resultSet.getString("explains"));
            
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return role;
    }
    
    public Token getTokenFromResultSet(ResultSet resultSet)
    {
        Token token = new Token();
        try
        {
            token.setId(resultSet.getString("id"));
            token.setActive_time(resultSet.getInt("active_time"));
            token.setCreateTime(resultSet.getString("token_time"));
            token.setKeyID(resultSet.getString("magic_key"));
            token.setUserID(resultSet.getString("userID"));
            token.setToken(resultSet.getString("token"));
            
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return token;
    }
    
    public User getUserFromResultSet(ResultSet resultSet)
    {
        User user = new User();
        try
        {
            user.setId(resultSet.getString("id"));
            user.setEmail(resultSet.getString("email"));
            user.setIntroduce(resultSet.getString("introduce"));
            user.setNickName(resultSet.getString("nickname"));
            user.setUserName(resultSet.getString("username"));
            user.setMemberLevel(resultSet.getString("memberlevel"));
            user.setPercardNum(resultSet.getString("percardnum"));
            user.setPhone(resultSet.getString("phone"));
            user.setRealName(resultSet.getString("realname"));
            user.setSex(resultSet.getString("sex"));
            user.setAddress(resultSet.getString("address"));
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return user;
    }
}
