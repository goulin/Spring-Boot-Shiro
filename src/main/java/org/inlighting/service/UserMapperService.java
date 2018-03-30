package org.inlighting.service;

import org.inlighting.model.User;

/**
 * @author goulin
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/3/29下午10:19
 */
public interface UserMapperService {
    User findByUsername(String name);

}
