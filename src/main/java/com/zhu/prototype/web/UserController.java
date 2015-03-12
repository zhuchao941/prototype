package com.zhu.prototype.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhu.prototype.dto.UserDTO;
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
		session.setAttribute("userPreferences", userService
				.getUserPreferences(user));
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterPage(@ModelAttribute Model model) {
		model.addAttribute("type", "register");
		return "user/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String submitRegisterForm(@ModelAttribute("user") UserDTO user,
			Errors errors, Model model, HttpSession session) {

		userValidator.validate(user, errors);
		if (errors.hasErrors()) {
			System.out.println(errors);
			return showRegisterPage(model);
		}
		userService.register(user);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user
				.getUsername(), user.getPassword2());
		subject.login(token);
		buildUserPreferences(user, session);
		return "redirect:news/newsList";
	}

	@ModelAttribute("user")
	public UserDTO getUser() {
		return new UserDTO();
	}

}
