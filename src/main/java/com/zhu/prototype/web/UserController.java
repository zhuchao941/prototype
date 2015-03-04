package com.zhu.prototype.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String onLoginFail(User user, HttpServletRequest request, Model model) {

		String errorClassName = (String) request
				.getAttribute("shiroLoginFailure");

		String authticationError = null;
		if (UnknownAccountException.class.getName().equals(errorClassName)) {
			authticationError = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(
				errorClassName)) {
			authticationError = "用户名/密码错误";
		} else if (errorClassName != null) {
			authticationError = "未知错误：" + errorClassName;
		}
		model.addAttribute("authticationError", authticationError);
		return showLoginPage();

	}

	private void buildUserPreferences(User user, HttpSession session) {
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
		buildUserPreferences(user, session);
		return new ModelAndView("redirect:news/newsList");
	}

	@ModelAttribute("user")
	public UserDTO getUser() {
		return new UserDTO();
	}

}
