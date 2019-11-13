package com.diesgut.medical.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	SecurityUserServiceImp userServiceImp;
	
	@Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		build.userDetailsService(userServiceImp).passwordEncoder(new BCryptPasswordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/","/app/**", "/css/**","/demo/**", "/scss/**","/js/**", "/img/**").permitAll()
		//only consults
		.antMatchers("/general/patients/patients").authenticated()
		.antMatchers("/general/doctors/doctors").authenticated()
		.antMatchers("/general/specialities/specialities").authenticated()
		.antMatchers("/general/**").hasAnyRole("ADMIN")
		.antMatchers("/consultation/**").hasAnyRole("ADMIN","DOCTOR")
		.antMatchers("/reports/**").hasAnyRole("ADMIN","DOCTOR")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
		.and()
		.logout().permitAll()
	     .and()
         .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .accessDeniedPage("/error_403");
	}

}
