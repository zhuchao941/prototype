package com.zhu.prototype.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhu.prototype.dto.LoginDTO;
import com.zhu.prototype.dto.UserPreferences;

@Controller
public class LoginController {

	@Resource
	private UserPreferences userPreferences;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage() {
		return "user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submitLoginForm(LoginDTO loginDTO) {
		if ("success".equals(validateUser(loginDTO))) {
			return "user/success";
		}
		return showLoginPage();
	}

	private String validateUser(LoginDTO loginDTO) {
		if (loginDTO.getUsername().equals("zhu")
				&& loginDTO.getPassword().equals("1234")) {
			userPreferences.setUsername(loginDTO.getUsername());
			return "success";
		}
		return "fail";
	}

}
