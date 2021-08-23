package com.pokolegas.forum.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pokolegas.forum.repository.UsuarioRepository;
import com.pokolegas.forum.service.AuthService;
import com.pokolegas.forum.service.TokenService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	AuthService authService;

	@Autowired
	TokenService tokenService;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	//Configurações de autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
	}

	//Configurações de autorização
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/topicos").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers("/swagger-ui/**").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
		.anyRequest().authenticated()
		.and().csrf().disable() //cross origin
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AuthFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
	}

	//Configurações de recursos estáticos
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	        .antMatchers("/**.html", "/swagger-ui/", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
	}
}
