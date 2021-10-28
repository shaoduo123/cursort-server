package com.shao.cursort.mapper;

import com.shao.cursort.base.BaseMapper;
import com.shao.cursort.pojo.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;


@Repository
public interface UserMapper extends BaseMapper<User> {

    public User getUserByNameOrPhone(String nameOrPhone) ;

    public User getUserByPhone(String phone) ;

    public User getUserById(long id) ;

    public int addUser(User user) ;


}
