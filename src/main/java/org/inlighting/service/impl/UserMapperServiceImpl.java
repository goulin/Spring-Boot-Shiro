package org.inlighting.service.impl;

import org.inlighting.mapper.UserMapper;
import org.inlighting.model.User;
import org.inlighting.service.UserMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author goulin
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/3/25下午10:12
 */
@Service
public class UserMapperServiceImpl implements UserMapperService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findByUsername(String name) {
        return userMapper.findUserByName(name);
    }
}
