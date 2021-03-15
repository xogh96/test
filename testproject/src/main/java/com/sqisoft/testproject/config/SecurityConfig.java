package com.sqisoft.testproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

	@Autowired
	private AuthProvider authProvider;
	
	@Autowired
	private AuthFailHandler authFailHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.authenticationProvider(authProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web)
	{
		web.ignoring().antMatchers("/favicon.ico", "/images/**", "/js/**", "/libs/**", "/resources/**", "/webjars/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests().antMatchers("/login", "/signUp").permitAll() // ,"/**"
						.antMatchers("/**").permitAll()
						//.hasRole("ADMIN").anyRequest().authenticated()

						.and().formLogin().loginPage("/login").defaultSuccessUrl("/").usernameParameter("userId")
						.passwordParameter("userPassword").failureHandler(authFailHandler).permitAll()

						.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
						.invalidateHttpSession(true).clearAuthentication(true)

						.and().csrf().disable()
		;
	}

}
