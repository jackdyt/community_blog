package com.jackdyt.blog.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: define the authority of admin
 */

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean( DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> map = new HashMap<>();
        map.put("/login.html","anon");
        map.put("/register","anon");
        map.put("/registerview","anon");
        map.put("/login","anon");
        map.put("/admin","authc");

        filterFactoryBean.setLoginUrl("/loginview");

        filterFactoryBean.setFilterChainDefinitionMap(map);
        return filterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean
    public Realm userRealm(){
        UserRealm userRealm = new UserRealm();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    //solve the following problem
    //Consider injecting the bean as one of its interfaces or forcing the use of CGLib-based proxies by setting proxyTargetClass=true on @EnableAsync and/or @EnableCaching.
    //The bean has been used by both spring and shiro so it becomes a proxy
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();

        creator.setProxyTargetClass(true);
        return creator;


    }


}
