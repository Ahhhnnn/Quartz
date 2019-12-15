package com.he.quartz.dao;

import com.he.quartz.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    void insert(@Param("user") User user);
}
