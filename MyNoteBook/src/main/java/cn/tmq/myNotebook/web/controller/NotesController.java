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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class NotesController extends BaseController{
	
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
	public String notes(Model model, HttpSession session, HttpServletRequest request) {
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
			return this.validateResult(message, model, Constants.VIEW_HOME, Constants.VIEW_HOME);
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
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/note", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> note(HttpServletRequest request, HttpSession session) {
		Map<String, Object> resultMap = this.supplier.get();
		resultMap.put("error", "1");
		resultMap.put("message", "系统异常");
		try {
			// 必须项目check
			String title = request.getParameter("txt-title");
			String content = request.getParameter("my-editormd-markdown-doc");
			String contentHtml = request.getParameter("my-editormd-html-code");
			if (StringUtils.isAnyEmpty(title,content,contentHtml)) {
				// 必须check
				resultMap.put("message", "必须项目不能为空");
			} else {
				Map<String, Object>  paramMap = this.supplier.get();
				paramMap.put("title", title);
				paramMap.put("content", content);
				paramMap.put("contentHtml", contentHtml);
				paramMap.put("id", ((Map<String, Object>)session.getAttribute(Constants.SESSION_USER)).get("id"));
				// 远程调用
				Message<List<UserNotesResponseDto>> message = this.postForService("note", paramMap);
				if ("200".equals(message.getStatus())) {
					resultMap.put("error", "0");
				}
				resultMap.put("message", message.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 查看笔记
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/notes/{id}")
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
	
	/**
	 * 删除笔记
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id") String id) {
		Map<String, Object> resultMap = this.supplier.get();
		resultMap.put("error", "1");
		resultMap.put("message", "系统异常");
		try {
			Map<String, Object>  paramMap = this.supplier.get();
			paramMap.put("id", id);
			
			Message<String> message = this.postForService("delete", paramMap);
			
			if ("200".equals(message.getStatus())) {
				resultMap.put("error", "0");
			}
			resultMap.put("message", message.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 修改笔记
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/note", method=RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, HttpSession session) {
		Map<String, Object> resultMap = this.supplier.get();
		resultMap.put("error", "1");
		resultMap.put("message", "系统异常");
		try {
			// 必须项目check
			String title = request.getParameter("txt-title-update");
			String content = request.getParameter("my-editormd-update-markdown-doc");
			String contentHtml = request.getParameter("my-editormd-update-html-code");
			String id = request.getParameter("note-id");
			if (StringUtils.isAnyEmpty(title,content,contentHtml)) {
				// 必须check
				resultMap.put("message", "必须项目不能为空");
			} else {
				Map<String, Object>  paramMap = this.supplier.get();
				paramMap.put("title", title);
				paramMap.put("content", content);
				paramMap.put("displayContent", contentHtml);
				paramMap.put("id", id);
				paramMap.put("userId", ((Map<String, Object>)session.getAttribute(Constants.SESSION_USER)).get("id"));
				// 远程调用
				Message<List<UserNotesResponseDto>> message = this.postForService("update", paramMap);
				if ("200".equals(message.getStatus())) {
					resultMap.put("error", "0");
				}
				resultMap.put("message", message.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

}
