package org.inlighting.shiro;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.inlighting.database.UserService;
import org.inlighting.database.UserBean;
import org.inlighting.mapper.UserMapper;
import org.inlighting.model.Permission;
import org.inlighting.model.Role;
import org.inlighting.model.User;
import org.inlighting.service.UserMapperService;
import org.inlighting.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);

    @Autowired
    private UserMapperService userMapperService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JWTUtil.getUsername(principals.toString());
//        UserBean user = userService.getUser(username);
        User byUsername = userMapperService.findByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<Role> roles = byUsername.getRoles();
        StringBuffer role_sb = new StringBuffer();
        Set<Permission> permissionSet = new HashSet<>();
        for(Role r : roles){
            role_sb.append(r.getRoleName()).append(",");
            for(Permission p : r.getPermissions()){
                permissionSet.add(p);
            }
        }
        simpleAuthorizationInfo.addRole(role_sb.substring(0, role_sb.length()-1));
        StringBuffer permission_sb = new StringBuffer();
        for(Permission p : permissionSet){
            permission_sb.append(p.getResource()).append(",");
        }
        Set<String> permission = new HashSet<>(Arrays.asList(permission_sb.substring(0,permission_sb.length()-1).split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        User byUsername = userMapperService.findByUsername(username);
        if (byUsername == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (! JWTUtil.verify(token, username, byUsername.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
