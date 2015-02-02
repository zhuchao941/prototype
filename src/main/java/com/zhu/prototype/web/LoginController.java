package com.zhu.prototype.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhu.prototype.dto.LoginDTO;
import com.zhu.prototype.dto.UserPreferences;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage() {
		return "user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submitLoginForm(LoginDTO loginDTO, HttpSession session) {
		if ("success".equals(validateUser(loginDTO, session))) {
			return "user/success";
		}
		return showLoginPage();
	}

	private String validateUser(LoginDTO loginDTO, HttpSession session) {
		if (loginDTO.getUsername().equals("zhu")
				&& loginDTO.getPassword().equals("1234")) {
			UserPreferences preferences = new UserPreferences();
			preferences.setUsername(loginDTO.getUsername());
			session.setAttribute("userPreferences", preferences);
			return "success";
		}
		return "fail";
	}

}
