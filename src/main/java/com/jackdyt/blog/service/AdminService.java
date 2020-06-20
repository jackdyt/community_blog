package com.jackdyt.blog.service;

import com.jackdyt.blog.mapper.AdminMapper;
import com.jackdyt.blog.model.Admin;
import com.jackdyt.blog.model.AdminExample;
import com.jackdyt.blog.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;


    public void register(@RequestParam("name") String name,
                         @RequestParam("password") String password){
        String salt = SaltUtils.getSalt(4);
        Admin admin = new Admin();
        admin.setUserName(name);
        admin.setSalt(salt);
        Md5Hash md5Hash = new Md5Hash(password,salt,1024);
        admin.setPassword(md5Hash.toHex());

        adminMapper.insert(admin);
        return;
    }

    public Admin findByAdminName(String name){
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andUserNameEqualTo(name);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (admins.size() == 1){
            return admins.get(0);
        }else{
            return null;
        }

    }




}
