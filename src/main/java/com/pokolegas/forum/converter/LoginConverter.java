package com.pokolegas.forum.converter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.pokolegas.forum.dto.LoginFormDto;

@Component
public class LoginConverter {

	public UsernamePasswordAuthenticationToken to(LoginFormDto formDto){
		UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(formDto.getEmail(), formDto.getPassword());
		return dadosLogin;
	}

}
