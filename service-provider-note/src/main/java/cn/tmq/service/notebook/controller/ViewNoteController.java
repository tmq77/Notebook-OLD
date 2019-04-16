package cn.tmq.service.notebook.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tmq.service.notebook.entity.MNotes;
import cn.tmq.service.notebook.service.ViewNoteService;

/**
 * 查看笔记
 * @author 陶敏麒
 *
 */
@RestController
public class ViewNoteController {
	
	@Autowired
	private ViewNoteService service;
	
	private Supplier<Map<String, Object>> supplier = HashMap<String,Object>::new;
	
	/**
	 * 获取用户笔记(单篇笔记)
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/note", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> view(@RequestParam Map<String, String> paramMap) throws Exception {
		try {
			Map<String, Object> resultMap = this.supplier.get();
			if (StringUtils.isEmpty(paramMap.get("id"))) {
				resultMap.put("status", "700");
				resultMap.put("message", "必须项目(笔记id)不能为空");
				return resultMap;
			}
			MNotes note = this.service.selectNotesById(paramMap.get("id"));
			if (null == note) {
				// 笔记不存在或者已经删除
				resultMap.put("status", "704");
				resultMap.put("message", "笔记不存在");
			} else {
				// 笔记存在
				resultMap.put("status", "200");
				resultMap.put("result", note);
			}
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			// 此处抛出异常会被服务调用者接收然后触发回退
			throw new Exception("获取用户笔记异常");
		}
	}
}
