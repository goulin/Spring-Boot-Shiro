package org.inlighting.mapper;

import org.inlighting.model.RoleAndPermission;

public interface RoleAndPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleAndPermission record);

    int insertSelective(RoleAndPermission record);

    RoleAndPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleAndPermission record);

    int updateByPrimaryKey(RoleAndPermission record);
}