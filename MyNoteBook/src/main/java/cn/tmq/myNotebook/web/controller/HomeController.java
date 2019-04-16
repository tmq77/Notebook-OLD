package cn.tmq.myNotebook.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.tmq.myNotebook.constants.Constants;
import cn.tmq.myNotebook.constants.Message;
import cn.tmq.myNotebook.web.responseDto.UserNotesResponseDto;

/**
 * 主页操作处理器:新建/一览
 * 2019年4月3日 21点58分
 * @author 陶敏麒
 * @version 1.0
 */
@Controller
public class HomeController extends BaseController{
	
	private Supplier<Map<String, Object>> supplier = HashMap<String, Object>::new;
	
	/**
	 * 获取笔记列表
	 * @param model
	 * @param session
	 * @param pageIndex 当前页的序号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/notes", method=RequestMethod.GET)
	public String home(Model model, HttpSession session, HttpServletRequest request) {
		try {
			Map<String, Object> paramMap = this.supplier.get();
			String pageIndex = request.getParameter("page");
			// 接口中做序号处理,调用处是第几页就传几，默认第一页
			if (!StringUtils.isNumeric(pageIndex)) {
				pageIndex = "1";
			}
			
			paramMap.put("id", ((Map<String, Object>)session.getAttribute(Constants.SESSION_USER)).get("id"));
			paramMap.put("pageIndex", pageIndex);
			// 远程调用
			Message<List<UserNotesResponseDto>> message = this.postForService("notes", paramMap);
			model.addAttribute("list", message.getResult());
			// 当前页码传入前台
			model.addAttribute("page", pageIndex);
			// 获取总页数
			model.addAttribute("pageCount", this.calculatePages(Integer.parseInt(message.getAddition().toString())));
			return this.validateResult(message, model, Constants.VIEW_HOME, Constants.VIEW_HOME, null);
		} catch (Exception e) {
			e.printStackTrace();
			return this.exceptionHandler(model);
		}
	}
	
	/**
	 * 计算需要分多少页.
	 * @param count 总数量
	 * @return 页数
	 */
	private int calculatePages(int count) {
		int pagination = 10;
		if (count == 10) {
			return 1;
		}
		return (count / pagination) + 1;
	}
	
	/**
	 * 新建笔记
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/note", method=RequestMethod.POST)
	public String blog(HttpServletRequest request, HttpSession session, Model model) {
		try {
			// 必须项目check
			String title = request.getParameter("txt-title");
			String content = request.getParameter("my-editormd-markdown-doc");
			String contentHtml = request.getParameter("my-editormd-html-code");
			if (StringUtils.isEmpty(title) || StringUtils.isEmpty(content) || StringUtils.isEmpty(contentHtml)) {
				model.addAttribute("error", "1");
				model.addAttribute("message", "必须项目不能为空");
				return Constants.VIEW_HOME;
			}
			
			Map<String, Object>  paramMap = new HashMap<>();
			paramMap.put("title", title);
			paramMap.put("content", content);
			paramMap.put("contentHtml", contentHtml);
			paramMap.put("id", ((Map<String, Object>)session.getAttribute(Constants.SESSION_USER)).get("id"));
			// 远程调用
			Message<List<UserNotesResponseDto>> message = this.postForService("note", paramMap);
			return this.validateResult(message, model, Constants.VIEW_INFO, Constants.VIEW_HOME, "/notes");
		} catch (Exception e) {
			e.printStackTrace();
			return this.exceptionHandler(model);
		}
	}

}
