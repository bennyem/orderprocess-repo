package com.etisalat.ops.dao;

public interface QueryMapper {
	public static final String ADD_USER="insert into user(user_name,password,create_date,email) values(?,?,?,?)";
	public static final String GET_USER="select * from user where user_name=? and password=?";
	public static final String GET_ALL_USERS= "select * from user";
}
