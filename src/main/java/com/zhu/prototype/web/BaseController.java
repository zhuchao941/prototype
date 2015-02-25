package com.zhu.prototype.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BaseController {

	@ModelAttribute("contextPath")
	protected String contextPath(HttpServletRequest request) {
		return request.getContextPath();
	}

}
