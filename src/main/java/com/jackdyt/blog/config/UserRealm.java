package com.jackdyt.blog.config;

import com.jackdyt.blog.model.Admin;
import com.jackdyt.blog.service.AdminService;
import com.jackdyt.blog.model.Admin;
import com.jackdyt.blog.service.AdminService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm  extends AuthorizingRealm{
    @Autowired
    private AdminService adminService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();
        Admin admin = adminService.findByAdminName(principal);
        if (admin != null && admin.getRole() != null){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRole(admin.getRole());
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
//        AdminService adminService = (AdminService) ApplicationContextUtils.getBean("AdminService");
        Admin admin = adminService.findByAdminName(principal);
        if (admin != null){
            return new SimpleAuthenticationInfo(admin.getUserName(), admin.getPassword(),
                    ByteSource.Util.bytes(admin.getSalt()), this.getName());
        }

        return null;



    }


}
