package com.pokolegas.forum.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pokolegas.forum.dto.ResponseDto;

@RestControllerAdvice
public class ValidationErrorHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ResponseDto> handle(MethodArgumentNotValidException exception) {
		List<ResponseDto> responseDtoList = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		fieldErrors.forEach(field -> {
			String mensagem = messageSource.getMessage(field, LocaleContextHolder.getLocale());
			ResponseDto dto = new ResponseDto(field.getField(), mensagem);
			responseDtoList.add(dto);
		});

		return responseDtoList;
	}
}
