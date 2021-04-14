package com.slinemotors.slineproject.config;

import com.slinemotors.slineproject.exceptions.ForbiddenException;
import com.slinemotors.slineproject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN", "USER")
//                .and()
//                .withUser("user").password("{noop}password").roles("USER");
//
//        System.out.println(encoder().encode("admin"));
//        System.out.println(encoder().encode("admin").equals("$2a$10$qTmORz/BZOdenmGZhb.qfu5X/vTNiEYYtzYaXvD2fM8Kprd.LF2z6"));

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/test", "/css/*", "/js/*", "/img/*").permitAll()
                .antMatchers("/user", "/user/*").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin", "/admin/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/users/*", "/employees/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .exceptionHandling().accessDeniedHandler(new ForbiddenException("Forbidden"));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

//        registry.addMapping("/**")
//          // с какого сайта можно отправлять запросы на этот сервер:
//                .allowedOrigins("*")
//                .allowedMethods("*");
    }
}
