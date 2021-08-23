package com.pokolegas.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pokolegas.forum.converter.LoginConverter;
import com.pokolegas.forum.dto.LoginFormDto;
import com.pokolegas.forum.dto.TokenDto;
import com.pokolegas.forum.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private LoginConverter loginConverter;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDto> autenticar( @RequestBody @Valid LoginFormDto form){
		UsernamePasswordAuthenticationToken dadosLogin = loginConverter.to(form);
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
