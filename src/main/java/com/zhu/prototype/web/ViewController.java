package com.zhu.prototype.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Zhu
 * @date 2015-2-18下午3:20:38
 * @description 查看页面的控制器
 * 
 */
@Controller
public class ViewController extends BaseController{

	@RequestMapping("/view")
	protected String view(String viewName) {
		return viewName;
	}
}
