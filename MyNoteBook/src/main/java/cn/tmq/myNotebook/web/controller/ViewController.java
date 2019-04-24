package cn.tmq.myNotebook.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tmq.myNotebook.constants.Constants;
import cn.tmq.myNotebook.constants.Message;

/**
 *   查看以及更新操作
 * @author 陶敏麒
 *
 */
@Controller
public class ViewController extends BaseController{
	
	private Supplier<Map<String, Object>> supplier = HashMap<String, Object>::new;

	@RequestMapping("/note/{id}")
	public String view(@PathVariable String id, Model model) {
		try {
			Map<String, Object> paramMap = this.supplier.get();
			paramMap.put("id", id);
			Message<Map<String, Object>> message = this.postForService("view", paramMap);
			if ("200".equals(message.getStatus())) {
				model.addAttribute("title", message.getResult().get("title"));
				model.addAttribute("content", message.getResult().get("content"));
			}
			return this.validateResult(message, model, Constants.VIEW_VIEW, Constants.VIEW_HOME);
		} catch (Exception e) {
			e.printStackTrace();
			return this.exceptionHandler(model);
		}
	}
}
