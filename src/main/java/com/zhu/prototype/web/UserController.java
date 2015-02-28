package com.zhu.prototype.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zhu.prototype.dto.UserDTO;
import com.zhu.prototype.dto.UserPreferences;
import com.zhu.prototype.entity.User;
import com.zhu.prototype.service.UserService;

@Controller
public class UserController extends BaseController {

	@Resource
	private Validator userValidator;

	@Resource
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage() {
		return "user/login";
	}

	/*@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submitLoginForm(User user, HttpSession session) {
		if ("success".equals(validateUser(user, session))) {
			return "redirect:news/newsList";
		}
		return showLoginPage();
	}*/

	private String validateUser(User user, HttpSession session) {
		if (userService.validateUser(user)) {
			doLogin(user, session);
			return "success";
		}
		return "fail";
	}

	private void doLogin(User user, HttpSession session) {
		UserPreferences preferences = new UserPreferences();
		preferences.setUsername(user.getUsername());
		session.setAttribute("userPreferences", preferences);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegisterPage() {
		ModelAndView mv = new ModelAndView("user/login");
		mv.addObject("type", "register");
		return mv;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView submitRegisterForm(
			@ModelAttribute("user") UserDTO user, Errors errors,
			HttpSession session) {

		userValidator.validate(user, errors);
		if (errors.hasErrors()) {
			System.out.println(errors);
			return showRegisterPage();
		}
		userService.register(user);
		doLogin(user, session);
		return new ModelAndView("redirect:news/newsList");
	}

	@ModelAttribute("user")
	public UserDTO getUser() {
		return new UserDTO();
	}

}
