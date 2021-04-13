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
@Order(1)
public class SecurityCliente extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
				"select username as username, password as password, 1 as enable from table_Cliente where username=?")
				.authoritiesByUsernameQuery(
						"select username as username, 'cliente' as authority from table_Cliente where username=?")
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/finalizarCompra/**").hasAuthority("cliente")
		.antMatchers("/consultarPedido/**").hasAnyAuthority("cliente", "admnistrador")
		.and().formLogin().loginPage("/loginCliente").permitAll().and().logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/home").and()
		.exceptionHandling().accessDeniedPage("/negado");	
	
	}

}
