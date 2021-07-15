package com.diamon.crm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
public class WebSecurityConfiguration
	extends WebSecurityConfigurerAdapter {
	
	// @Autowired does not work here, use ctor
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder passwordEncoder;
	
	public WebSecurityConfiguration(
			BCryptPasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService)
	{
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.cors().disable()
		.authorizeRequests()
			.antMatchers("/login*").permitAll()
			.antMatchers("/rest/v1/users/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.httpBasic()
		.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.formLogin()
				.loginPage("/login.html")
				.loginProcessingUrl("/perform_login")
				.defaultSuccessUrl("/rest/v1/users", true)
				.failureUrl("/login.html?error=true")
				.failureHandler(authenticationFailureHandler())
				.and()
				.logout()
				.logoutUrl("/perform_logout")
				.deleteCookies("JSESSIONID")
				.logoutSuccessHandler(logoutSuccessHandler());
		;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}
/*	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.applyPermitDefaultValues(); // GET & POST
		corsConfiguration.addAllowedMethod(HttpMethod.PUT);
		corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
		corsConfiguration.addAllowedMethod(HttpMethod.DELETE);

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
*/

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}
}
