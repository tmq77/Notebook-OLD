package cn.tmq.myNotebook.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tmq.myNotebook.constants.Constants;

/**
 *   查看以及更新操作
 * @author 陶敏麒
 *
 */
@Controller
public class ViewController extends BaseController{

	@RequestMapping("/note/{id}")
	public String view(@PathVariable String id) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.VIEW_VIEW;
	}
}
