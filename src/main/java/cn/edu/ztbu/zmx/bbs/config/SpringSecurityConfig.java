package cn.edu.ztbu.zmx.bbs.config;

import cn.edu.ztbu.zmx.bbs.service.impl.DefaultUserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author zhaomengxin
 * @version 1.0
 * @program bbs.SpringSecurityConfig
 * @date 2020/10/17 15:30
 * @Description
 * @since 1.0
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {



    @Bean
    public UserDetailsService userDetailsService(){
        return new DefaultUserDetailServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.authorizeRequests().
                antMatchers("/resource/**").permitAll().
                antMatchers("/admin/**").hasRole("ADMIN").
                anyRequest().authenticated().
                and().
                formLogin().
                loginPage("/login").
                usernameParameter("username").
                passwordParameter("password").
                successForwardUrl("/index").
                permitAll().
                and().
                logout().
                logoutUrl("/logout").and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("daoAuthenticationProvider")
    protected AuthenticationProvider daoAuthenticationProvider() throws Exception{
        //这里会默认使用BCryptPasswordEncoder比对加密后的密码，注意要跟createUser时保持一致
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService());
        return daoProvider;
    }
}
