package cn.tmq.service.notebook.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	 * GET请求接收参数时，如果参数为Map类型，则需要加注释@RequestParam 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/notes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> view(@PathVariable String id) throws Exception {
		try {
			Map<String, Object> resultMap = this.supplier.get();
			
			MNotes note = this.service.selectNotesById(id);
			if (null == note) {
				// 笔记不存在或者已经删除
				resultMap.put("status", "704");
				resultMap.put("message", "笔记不存在");
			} else {
				// 笔记存在
				// blob读出来的是ISO-8859-1编码，需要转换为UTF-8编码才可以
				// 如果你确认全部是文本文件，可以采用Text类型,本例中mysql使用了blob
				String content = new String(note.getContent().getBytes("ISO-8859-1"), "UTF-8");
				note.setContent(content);
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
