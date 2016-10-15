package com.jira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("bill").password("abc123").authorities("ROLE_USER");
	}

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
	// http.authorizeRequests().antMatchers("/").permitAll().and().csrf().and().exceptionHandling()
	// .accessDeniedPage("/error");
	// }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/home").access("hasRole('ROLE_USER')").and().formLogin()
				.loginPage("/index").defaultSuccessUrl("/home").failureUrl("/index").usernameParameter("email")
				.passwordParameter("password").and().logout().logoutSuccessUrl("/index");

	}

}
