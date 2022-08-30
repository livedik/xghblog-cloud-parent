package com.xghblog.services.demoSpring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xghblog.services.demoSpring.pojo.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<Users> {
}
