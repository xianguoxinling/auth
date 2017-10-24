package com.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.model.auth.Authority;
import com.model.auth.Role;
import com.until.errorcode.MAGICCODE;

public class AuthDBManager
{
    protected DBManager dbManager = null;

    public int createAuth(Authority authority, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "INSERT INTO authority (name,explains,target,magic_key) VALUES (?,?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, authority.getName());
            statement.setString(2, authority.getExplain());
            statement.setString(3, authority.getTarget());
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

    public int checkAuthNameExist(String name,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT name FROM authority WHERE name = ? and magic_key = ?";
        PreparedStatement statement;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, name);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                    return MAGICCODE.AUTH_NAME_EXIST;
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
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
        return MAGICCODE.AUTH_NAME_NOT_EXIST;
    }
    
    public int checkRoleNameExist(String name,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT name FROM role WHERE name = ? and magic_key = ?";
        PreparedStatement statement;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, name);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                    return MAGICCODE.ROLE_NAME_EXIST;
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
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
        return MAGICCODE.ROLE_NAME_NOT_EXIST;
    }
    
    public Authority queryAuthorityByName(String name,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT * FROM authority WHERE name = ? and magic_key = ?";
        PreparedStatement statement;
        Authority authority = null;
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, name);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                authority = dbUntil.getAuthorityFromResultSet(resultSet);
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
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
        return authority;
    }
    
    public Role queryRoleByName(String name,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT * FROM role WHERE name = ? and magic_key = ?";
        PreparedStatement statement;
        Role role = null;
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, name);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                role = dbUntil.getRoleFromResultSet(resultSet);
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
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
        return role;
    }
    
    public int createRole(Role role, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO role (name,explains,target,magic_key) VALUES (?,?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role.getName());
            statement.setString(2, role.getExplain());
            statement.setString(3, role.getTarget());
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

    public int addAuthToRole(String authID, String roleID, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO auth_role (auth_id,role_id,magic_key) VALUES (?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, Long.parseLong(authID));
            statement.setLong(2, Long.parseLong(roleID));
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

    public int delAuthFromRole(String authID,String roleID,String keyID)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
//        String sql = "INSERT INTO auth_role (auth_id,role_id,magic_key) VALUES (?,?,?)";
        String sql = "delete from auth_role where auth_id = ? and role_id = ? and magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, Long.parseLong(authID));
            statement.setLong(2, Long.parseLong(roleID));
            statement.setString(3, keyID);
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
    
    public int addAuthToPerson(String authID, String userID, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO auth_person (auth_id,user_id,magic_key) VALUES (?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, Long.parseLong(authID));
            statement.setString(2, userID);
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

    public int addShopAuth(String authID, String shopUUID, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO auth_shop (auth_id,shop_uuid,magic_key) VALUES (?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, Long.parseLong(authID));
            statement.setString(2, shopUUID);
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

    public int addShopRole(String roleID, String shopUUID, String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO role_shop (role_id,shop_uuid,magic_key) VALUES (?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, Long.parseLong(roleID));
            statement.setString(2, shopUUID);
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

    public int checkAuth(String userID, String target, String authName, String magicKey)
    {

        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select a.id from authority a left join auth_person b on a.id = b.auth_id where a.name = ? and a.magic_key = ? and b.magic_key = ? and a.target = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, authName);
            statement.setString(2, magicKey);
            statement.setString(3, magicKey);
            statement.setString(4, target);
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
        return MAGICCODE.USER_NOT_AUTH;
    }
    
    public int queryAuthorityByRole(String roleID,String magicKey,List<Authority> authList)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT * FROM authority a left join auth_role b on a.id = b.auth_id WHERE b.role_id = ? and a.magic_key = ? and b.magic_key = ?";
        PreparedStatement statement;
        Authority authority = null;
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setLong(1, Long.parseLong(roleID));
            statement.setString(2, magicKey);
            statement.setString(3, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                authority = dbUntil.getAuthorityFromResultSet(resultSet);
                authority.setId(resultSet.getString("auth_id"));
                authList.add(authority);
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
    
    public int queryAuthorityByPerson(String userID,String keyID,List<Authority> authList)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT * FROM authority a left join auth_person b on a.id = b.auth_id WHERE b.user_id = ? and a.magic_key = ? and b.magic_key = ?";
        PreparedStatement statement;
        Authority authority = null;
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, userID);
            statement.setString(2, keyID);
            statement.setString(3, keyID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                authority = dbUntil.getAuthorityFromResultSet(resultSet);
                authority.setId(resultSet.getString("auth_id"));
                authList.add(authority);
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
    
    public int addRoleToPerson(String roleID,String userID,String keyID)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO role_person (role_id,user_id,magic_key) VALUES (?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, Long.parseLong(roleID));
            statement.setString(2, userID);
            statement.setString(3, keyID);
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
    
    public int delAuthFromPerson(String authID, String personID, String keyID)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "delete from  auth_person where auth_id = ? and user_id = ? and magic_key = ?";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, Long.parseLong(authID));
            statement.setString(2, personID);
            statement.setString(3, keyID);
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
    
    public int delRoleFromPerson(String roleID, String personID, String keyID)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "delete from  role_person where role_id = ? and user_id = ? and magic_key = ?";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, Long.parseLong(roleID));
            statement.setString(2, personID);
            statement.setString(3, keyID);
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
    
    public int queryRoleByPerson(String userID,String keyID,List<Role> roleList)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql1 = "SELECT * FROM role a left join role_person b on a.id = b.role_id WHERE b.user_id = ? and a.magic_key = ? and b.magic_key = ?";
        PreparedStatement statement;
        Role role = null;
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(sql1);
            statement.setString(1, userID);
            statement.setString(2, keyID);
            statement.setString(3, keyID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                role = dbUntil.getRoleFromResultSet(resultSet);
                role.setId(resultSet.getString("role_id"));
                roleList.add(role);
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
        
//    public int checkAuthFromRole(String userID, String shopUUID, String authName, String magicKey)
//    {
//        dbManager = DBManager.getInstance();
//        Connection connection = null;
////        String sql = "select a.id from authority a left join auth_person b on a.id = b.auth_id where a.name = ? and a.magic_key = ? and b.magic_key = ?";
//        String sql = "select authority.id from authority,role_person,auth_role where authority.name = ? and role_person.user_id = ? and auth_role.auth_id = authority.id and role_person.role_id = auth_role.role_id";
//        try
//        {
//            connection = dbManager.getConection();
//            connection.setAutoCommit(true);
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, authName);
//            statement.setString(2, magicKey);
//            statement.setString(3, magicKey);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next())
//            {
//                return MAGICCODE.OK;
//            }
//
//        } catch (SQLException e)
//        {
//            e.printStackTrace();
//            return MAGICCODE.DB_ERROR;
//        } finally
//        {
//            try
//            {
//                if (null != connection)
//                {
//                    connection.close();
//                }
//
//            } catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return MAGICCODE.USER_NOT_AUTH;
//    }
    public int checkAuthFromRole(String authName,String target,String roleID,String keyID)
    {
      dbManager = DBManager.getInstance();
      Connection connection = null;
      String sql = "select a.id from authority a left join auth_role b on a.id = b.auth_id where a.name = ? and a.magic_key = ? and a.target = ? and b.role_id = ?";
      try
      {
          connection = dbManager.getConection();
          connection.setAutoCommit(true);
          PreparedStatement statement = connection.prepareStatement(sql);
          statement.setString(1, authName);
          statement.setString(2, keyID);
          statement.setString(3, target);
          statement.setLong(4, Long.parseLong(roleID));
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
      return MAGICCODE.USER_NOT_AUTH;
    }
    
}
