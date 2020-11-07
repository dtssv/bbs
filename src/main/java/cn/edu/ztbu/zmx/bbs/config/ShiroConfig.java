package cn.edu.ztbu.zmx.bbs.config;

import cn.edu.ztbu.zmx.bbs.shiro.AdminRealm;
import cn.edu.ztbu.zmx.bbs.shiro.UserRealm;
import com.google.common.collect.Lists;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program bbs.ShiroConfig
 * @author: zhaomengxin
 * @date: 2020/11/7 18:09
 * @Description:
 */
@Configuration
public class ShiroConfig {


    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean
    public AdminRealm adminRealm(){
        return new AdminRealm();
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(Lists.newArrayList(userRealm(),adminRealm()));
        return securityManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();;
        creator.setProxyTargetClass(Boolean.TRUE);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("");
        factoryBean.setUnauthorizedUrl("");
        return factoryBean;
    }
}
