package com.zhu.prototype.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhu.prototype.dto.UserDTO;
import com.zhu.prototype.dto.UserPreferences;
import com.zhu.prototype.entity.User;

@Controller
public class UserController {

	@Resource
	private Validator userValidator;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage() {
		return "user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submitLoginForm(User user, HttpSession session) {
		if ("success".equals(validateUser(user, session))) {
			return "user/login-success";
		}
		return showLoginPage();
	}

	private String validateUser(User user, HttpSession session) {
		if (user.getUsername().equals("zhu")
				&& user.getPassword().equals("1234")) {
			UserPreferences preferences = new UserPreferences();
			preferences.setUsername(user.getUsername());
			session.setAttribute("userPreferences", preferences);
			return "success";
		}
		return "fail";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterPage() {
		return "user/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String submitRegisterForm(@ModelAttribute("user") UserDTO user,
			Errors errors) {

		userValidator.validate(user, errors);
		if (errors.hasErrors()) {
			System.out.println(errors);
			return showRegisterPage();
		}
		return "user/register-success";
	}

	@ModelAttribute("user")
	public UserDTO getUser() {
		return new UserDTO();
	}

}
