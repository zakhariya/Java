package ua.lpr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Value("${security.enable-csrf}")
	private boolean csrfEnabled;

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
//
//
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);

        if(!csrfEnabled)
            http.csrf().disable();
/*
		http.authorizeRequests()
				.antMatchers("/admin/**").access("hasRole('toster')")
				.and()
				.formLogin().loginPage("/login").failureUrl("/login?error")
				.usernameParameter("UserName").passwordParameter("UserPassword")
				.and()
				.logout().logoutSuccessUrl("/login?logout")
				.and()
				.exceptionHandling().accessDeniedPage("/403")
				.and()
				.csrf();
*/
	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
/*
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select UserName, UserPassword from tblUsers where UserName=?")
				.authoritiesByUsernameQuery("select UserName, Title from tblUsers where UserName=?");
*/
	}

}