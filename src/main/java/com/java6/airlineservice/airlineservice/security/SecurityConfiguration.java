package com.java6.airlineservice.airlineservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    DataSource dataSource;

        @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
       auth.jdbcAuthentication().dataSource(dataSource);
       //auth.userDetailsService(userDetailsService());

        /*auth.inMemoryAuthentication()
                .withUser("Thomas")
                .password("admin")
                .roles("ADMIN")
               .and()
                .withUser("Kaspar")
                .password("admin")
                .roles("ADMIN")
                .and()
                .withUser("Miguel")
                .password("admin")
                .roles("ADMIN");*/

    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                //.antMatchers("/addflight").hasRole("FLIGHT_WRITE")
                .and().formLogin();
    }
}

