package com.zhu.prototype.shiro.filter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.zhu.prototype.dto.UserPreferences;
import com.zhu.prototype.entity.User;
import com.zhu.prototype.service.UserService;

public class SessionFormAuthenticationFilter extends FormAuthenticationFilter {

	@Resource
	private UserService userService;

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		
		super.onLoginSuccess(token, subject, request, response);
		initCustomSessionAttributes(((HttpServletRequest) request)
				.getSession(false), token);

		return false;
	}

	protected void initCustomSessionAttributes(HttpSession session,
			AuthenticationToken token) {

		String username = token.getPrincipal().toString();
		User user = userService.getUserByUsername(username);
		UserPreferences preferences = new UserPreferences();
		preferences.setUsername(user.getUsername());
		session.setAttribute("userPreferences", preferences);
	}
}
