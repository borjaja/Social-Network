package app.rest.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import app.model.exceptions.DuplicateInstanceException;
import app.model.exceptions.IncorrectLoginException;
import app.model.exceptions.InstanceNotFoundException;
import app.rest.dto.UserLoginDto;

@ControllerAdvice
public class AdviseController {

	private final static String INSTANCE_NOT_FOUND_EXCEPTION = "exception.InstanceNotFoundException";
	private final static String DUPLICATE_INSTANCE_EXCEPTION = "exception.DuplicateInstanceException";
	private final static String INCORRECT_LOGIN_EXCEPTION = "exception.IncorrectLoginException";

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(InstanceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final String handleInstanceNotFoundException(Model model, InstanceNotFoundException exception,
			Locale locale) {
		model.addAttribute("user", new UserLoginDto());
		String s = messageSource.getMessage(INSTANCE_NOT_FOUND_EXCEPTION, null, INSTANCE_NOT_FOUND_EXCEPTION, locale);
		model.addAttribute("error", s);
		return "home";
	}

	@ExceptionHandler(DuplicateInstanceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final String handleDuplicateInstanceException(Model model, DuplicateInstanceException exception,
			Locale locale) {
		model.addAttribute("user", new UserLoginDto());
		String s = messageSource.getMessage(DUPLICATE_INSTANCE_EXCEPTION, null, DUPLICATE_INSTANCE_EXCEPTION, locale);
		model.addAttribute("error", s);
		return "home";
	}

	@ExceptionHandler(IncorrectLoginException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final String handleDuplicateIn(Model model, IncorrectLoginException exception, Locale locale) {
		model.addAttribute("user", new UserLoginDto());
		String s = messageSource.getMessage(INCORRECT_LOGIN_EXCEPTION, null, INCORRECT_LOGIN_EXCEPTION, locale);
		model.addAttribute("error", s);
		return "home";
	}

}
