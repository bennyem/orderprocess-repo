package com.etisalat.ops.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.etisalat.ops.domain.User;

public class UserDaoImpl implements UserDAO{
	private Logger daoLogger=Logger.getLogger(UserDaoImpl.class);
	
	@Override
	public Integer addNewUser(User user) throws Exception {
		
		Connection connection=null;
		try {
			Context context= 
					(Context)new InitialContext().lookup("java:comp/env");
			DataSource dataSource= (DataSource) context.lookup("jdbc/userDB");
			connection=dataSource.getConnection();
			PreparedStatement preparedStatement=
					connection.prepareStatement(QueryMapper.ADD_USER);
			populatePreparedStatement(user,preparedStatement);
			int status=preparedStatement.executeUpdate();
			if(status>0) {
				daoLogger.info("New user: "+user.getUserId()+" added to database");
				return user.getUserId();
			}else {
				throw new SQLException("Unable to add user");
			}
		}catch(SQLException e) {
			daoLogger.error(e.getMessage());
			throw e;
		}catch(Exception e) {
			daoLogger.error(e.getMessage());
			throw e;
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		
		
	}

	private void populatePreparedStatement(User user, PreparedStatement preparedStatement) throws SQLException {
		//preparedStatement.setInt(1, user.getUserId());
		preparedStatement.setString(1, user.getUserName());
		preparedStatement.setString(2, user.getPassword());
		//java.time.LocalDate->java.sql.Date
		preparedStatement
		.setDate(3, java.sql.Date.valueOf(user.getCreateDate()));
		preparedStatement.setString(4, user.getEmail());
	}

	

	@Override
	public User getUserDetails(String username, String password) throws Exception {
		Connection connection=null;
		try {
			Context context= 
					(Context)new InitialContext().lookup("java:comp/env");
			DataSource dataSource= (DataSource) context.lookup("jdbc/userDB");
			connection=dataSource.getConnection();
			PreparedStatement preparedStatement=
					connection.prepareStatement(QueryMapper.GET_USER);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				User user=new User();
				populateUser(resultSet,user);
				return user;
			}else {
				throw new Exception("Unable to generate new userid");
			}
		}catch(SQLException e) {
			throw e;
		}catch(Exception e) {
			throw e;
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
	}

	private void populateUser(ResultSet resultSet, User user) throws SQLException {
		user.setUserId(resultSet.getInt("USER_ID"));
		user.setUserName(resultSet.getString("USER_NAME"));
		user.setEmail(resultSet.getString("EMAIL"));
		user.setPassword(resultSet.getString("PWD"));
		//java.sql.Date->java.time.LocalDate
		user.setCreateDate(resultSet.getDate("CREATE_DATE").toLocalDate());
		
	}

	@Override
	public User getUserDetails(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsers() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
