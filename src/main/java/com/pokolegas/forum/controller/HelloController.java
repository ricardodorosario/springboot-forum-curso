package com.pokolegas.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@RequestMapping("/")
	/*
	 * Por padrão, o Spring considera que o retorno do método é o nome da página que ele deve carregar,
	 * mas ao utilizar a anotação @ResponseBody, indicamos que o retorno do método deve ser serializado
	 *  e devolvido no corpo da resposta.
	 * */
	@ResponseBody
	public String hello() {
		return "Hello World";
	}

}
