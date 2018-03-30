package org.inlighting.mapper;

import org.inlighting.model.UserAndRole;

public interface UserAndRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAndRole record);

    int insertSelective(UserAndRole record);

    UserAndRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAndRole record);

    int updateByPrimaryKey(UserAndRole record);
}