package com.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.user.HeadPic;
import com.model.user.User;
import com.until.errorcode.MAGICCODE;

public class PersonDBManager
{
	protected DBManager dbManager = null;

	public int changeNickName(String userName, String nickName,String magicKey)
	{
		int result = 0;
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "update user set nickname = ? where username = ? and magic_key = ?";
		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nickName);
			statement.setString(2, userName);
			statement.setString(3, magicKey);
			statement.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
			return MAGICCODE.DB_ERROR;
		}
		finally
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
		return result;
	}

	public int changePassword(String userID,String password,String magicKey)
	{
	    int result = 0;
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "update login set password = ? where id = ? and magic_key = ?";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            statement.setLong(2, Long.parseLong(userID));
            statement.setString(3, magicKey);
            statement.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        }
        finally
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
        return result;
	}
	
	public int changeIntroduce(String userName, String introduce,String magicKey)
	{
		int result = 0;
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "update user set introduce = ? where username = ? and magic_key = ?";

		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, introduce);
			statement.setString(2, userName);
			statement.setString(3, magicKey);
			statement.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
			return MAGICCODE.DB_ERROR;
		}
		finally
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
		return result;
	}

	public User searchUserInfoByUserName(String userName,String magicKey)
	{

		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "select * from user where username = ? and magic_key = ?";
		DBUntil dbUntil = new DBUntil();
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
				return dbUntil.getUserFromResultSet(resultSet);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
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

	public User searchUserInfoByUserID(String userID,String magicKey)
	{
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "select * from user where id = ? and magic_key = ?";
		DBUntil dbUntil = new DBUntil();
		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userID);
			statement.setString(2, magicKey);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				return dbUntil.getUserFromResultSet(resultSet);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
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


	public int updateUserInfo(User user,String magicKey)
	{

		int result = 0;
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "update user set introduce = ?, nickname=?, sex = ? ,percardnum = ? , realname = ? ,address = ? where id = ? and magic_key = ?";

		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getIntroduce());
			statement.setString(2, user.getNickName());
			statement.setString(3, user.getSex());
			statement.setString(4, user.getPercardNum());
			statement.setString(5, user.getRealName());
			statement.setString(6, user.getAddress());
			statement.setLong(7, Long.parseLong(user.getId()));
			statement.setString(8, magicKey);
			
			statement.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
			return MAGICCODE.DB_ERROR;
		}
		finally
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
		return result;

	}

	public String queryEmailByUserID(String userID,String magicKey)
	{
		String email = "";
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "select email from user where id = ? and magic_key = ?";
		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userID);
			statement.setString(2, magicKey);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				email = resultSet.getString("email");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
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
		return email;
	}

	public int checkUserExist(String userID,String magicKey)
	{
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "select * from user where id = ? and magic_key = ?";
		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, Long.parseLong(userID));
			statement.setString(2, magicKey);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				return MAGICCODE.USER_EXIST;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
			return MAGICCODE.DB_ERROR;
		}
		finally
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
		return MAGICCODE.USER_NOT_EXIST;
	}

	public int addHeadPic(String userID, String picPath,String magicKey)
	{
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "INSERT INTO headpic (userid, pic_path,magic_key) VALUES (?,?,?)";

		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, Long.parseLong(userID));
			statement.setString(2, picPath);
			statement.setString(3, magicKey);
			statement.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
			return MAGICCODE.DB_ERROR;
		}
		finally
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

	public int modifyHeadPic(String userID, String picPath,String magicKey)
	{
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "update headpic set pic_path = ? where userid = ? and magic_key = ?";

		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, picPath);
			statement.setLong(2, Long.parseLong(userID));
			statement.setString(3, magicKey);
			statement.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
			return MAGICCODE.DB_ERROR;
		}
		finally
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

	public int delHeadPic(String userID,String magicKey)
	{
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "update headpic set pic_path = ? where userid = ? and magic_key = ?";

		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, "");
			statement.setLong(2, Long.parseLong(userID));
			statement.setString(3, magicKey);
			statement.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
			return MAGICCODE.DB_ERROR;
		}
		finally
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

	public HeadPic queryHeadPic(String userID,String magicKey)
	{
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "select * from headpic where userid = ? and magic_key = ?";
		HeadPic headPic = null;
		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, Long.parseLong(userID));
			statement.setString(2, magicKey);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				headPic = new HeadPic();
				headPic.setId(resultSet.getString("id"));
				headPic.setUserID(resultSet.getString("userid"));
				headPic.setPicPath(resultSet.getString("pic_path"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
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
		return headPic;
	}
	
	public String queryUserIDByOpenID(String openid,String magicKey)
	{
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "select id from login where openid = ? and magic_key = ?";
		String userID = null;
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
		}
		finally
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
	
	public String queryOpenIDByUserID(String userID,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select openid from login where id = ? and magic_key = ?";
        String openID = null;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                openID = resultSet.getString("openid");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
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
        return openID;
    }
	
	public User queryUserInfoByEmail(String email,String magicKey)
	{
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "select * from user where email = ? and magic_key = ?";
		DBUntil dbUntil = new DBUntil();
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
				User user = dbUntil.getUserFromResultSet(resultSet);
				return user;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
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
	
	public User queryUserInfoByPhone(String phone,String magicKey)
	{
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "select * from user where phone = ? and magic_key = ?";
		DBUntil dbUntil = new DBUntil();
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
				User user = dbUntil.getUserFromResultSet(resultSet);
				return user;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
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

	
	   protected void updateUserInfoFromBefore(User before, User after)
	    {
	        if (null == after.getEmail())
	        {
	            after.setEmail(before.getEmail());
	        }
	
	        if (null == after.getMemberLevel())
	        {
	            after.setIsArtist(before.getMemberLevel());
	        }
	
	        if (null == after.getIsArtist())
	        {
	            after.setIsArtist(before.getIsArtist());
	        }
	
	        if (null == after.getPercardNum())
	        {
	            after.setPercardNum(before.getPercardNum());
	        }
	
	        if (null == after.getPhone())
	        {
	            after.setPhone(before.getPhone());
	        }
	
	        if (null == after.getRealName())
	        {
	            after.setRealName(before.getRealName());
	
	        }
	
	        if (null == after.getUserName())
	        {
	            after.setUserName(before.getUserName());
	        }
	    }
	
	    public int modifyUserInfo(User user, String magicKey)
	    {
	        int result = 1;
	        if ("0" == user.getId())
	        {
	            return result;
	        }
	
	        User before = null;
	        dbManager = DBManager.getInstance();
	        Connection connection = null;
	        String sql = "select * from user where id = ? and magic_key = ?";
	        DBUntil dbUntil = new DBUntil();
	        try
	        {
	            connection = dbManager.getConection();
	            connection.setAutoCommit(true);
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1, user.getId());
	            statement.setString(2, magicKey);
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next())
	            {
	                before = dbUntil.getUserFromResultSet(resultSet);
	            }
	
	            if (before == null)
	            {
	                return 0;
	            }
	            updateUserInfoFromBefore(before, user);
	
	            String updatesql = "update user set email = ?, phone = ?, percardnum = ?, isartist = ?, realname = ?, memberlevel = ? ,address = ? where id = ? and magic_key = ?";
	            PreparedStatement statement2 = connection.prepareStatement(updatesql);
	            statement2.setString(1, user.getEmail());
	            statement2.setString(2, user.getPhone());
	            statement2.setString(3, user.getPercardNum());
	            statement2.setInt(4, Integer.parseInt(user.getIsArtist()));
	            statement2.setString(5, user.getRealName());
	            statement2.setInt(6, Integer.parseInt(user.getMemberLevel()));
	            statement2.setString(7, user.getAddress());
	            statement2.setString(8, user.getId());
	            statement2.setString(9, magicKey);
	            statement2.executeUpdate();
	
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
	        return result;
	    }
}
