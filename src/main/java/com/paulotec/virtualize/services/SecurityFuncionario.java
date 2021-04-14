package com.paulotec.virtualize.services;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityFuncionario extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
				"select username as username, password as password, 1 as enable from table_Usuarios where username=?")
				.authoritiesByUsernameQuery(
						"select username as username, role as authority from table_Usuarios where username=?")
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.authorizeRequests().antMatchers("/login").permitAll()
		.antMatchers("/administrativo/**").hasAnyAuthority("administrador")
		.antMatchers("/estoquista/**").hasAnyAuthority("administrador","estoquista")
		.and().formLogin().loginPage("/login").failureUrl("/login").loginProcessingUrl("/login")
		.defaultSuccessUrl("/administrativo").usernameParameter("username").passwordParameter("password")
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
		.and().exceptionHandling().accessDeniedPage("/negado")
		.and().csrf().disable();	
	
	}

}
