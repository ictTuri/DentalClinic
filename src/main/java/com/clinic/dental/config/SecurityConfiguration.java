package com.clinic.dental.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.clinic.dental.security.auth.AppAuthenticationEntryPoint;
import com.clinic.dental.security.auth.ApplicationUserService;
import com.clinic.dental.security.auth.CustomAccessDeniedHandler;
import com.clinic.dental.security.jwt.JwtTokenVerifier;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	private final LogoutSuccessHandler logoutSuccess;
	private final SecretKey secretKey;
	private final AppAuthenticationEntryPoint authenticationEntryPoint;
	private final CustomAccessDeniedHandler accessDeniedHandler;	
	
	public JwtTokenVerifier jwtTokenVerifier(){
        return new JwtTokenVerifier(secretKey);
    }

	@Override
    public void configure(WebSecurity http) {
        http.ignoring().antMatchers(getDisabledUrlPaths());
        http.ignoring().antMatchers(HttpMethod.OPTIONS);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		http.csrf().disable()
						.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.and()
						.addFilterAfter(jwtTokenVerifier(), UsernamePasswordAuthenticationFilter.class)
						.authorizeRequests()
						.anyRequest()
						.authenticated()
						.and()
						.logout()
						.logoutUrl("/api/_logout").logoutSuccessHandler(logoutSuccess)
						.deleteCookies("jwttoken").invalidateHttpSession(true);
		
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		var provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}

	private String[] getDisabledUrlPaths() {
		return new String[] {"/api/register", "/api/_login", "/open/**", "/h2-console/**","/h2-console", "/api/register", "/webjars/**", "/v2/api-docs/**",
				"/swagger-resources/**", "/swagger-ui.html", "/swagger/**", "/favicon.ico", "/api/swagger.json",
				"/actuator/health" };
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
