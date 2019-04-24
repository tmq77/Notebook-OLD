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

import cn.tmq.service.notebook.service.DeleteNoteService;

/**
 * 删除操作的控制器
 * @author 陶敏麒
 *
 */
@RestController
public class DeleteNoteController {
	
	private Supplier<Map<String, Object>> supplier = HashMap<String,Object>::new;
	
	@Autowired
	private DeleteNoteService service;
	
	/**
	 *  删除笔记.
	 * @param id 笔记id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/note/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> note(@PathVariable String id) throws Exception {
		try {
			Map<String, Object> resultMap = this.supplier.get();
			
			int result = this.service.deleteNote(id);
			
			if (result != 0) {
				resultMap.put("status", "200");
				resultMap.put("message", "删除成功");
			} else {
				resultMap.put("status", "707");
				resultMap.put("message", "删除失败：此笔记已删除或者处理异常");
			}
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			// 此处抛出异常会被服务调用者接收然后触发回退
			throw new Exception("删除用户笔记异常");
		}
	}
}
