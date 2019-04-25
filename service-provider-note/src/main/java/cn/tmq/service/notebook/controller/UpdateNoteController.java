package cn.tmq.service.notebook.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.tmq.service.notebook.entity.MNotes;
import cn.tmq.service.notebook.service.UpdateNoteService;

@RestController
public class UpdateNoteController {

	private Supplier<Map<String, Object>> supplier = HashMap<String,Object>::new;
	
	@Autowired
	private UpdateNoteService service;
	
	/**
	 * 修改笔记
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/note", method=RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> update(@RequestBody Map<String, String> paramMap) throws Exception {
		try {
			Map<String, Object> resultMap = this.supplier.get();
			
			MNotes note = new MNotes();
			BeanUtils.copyProperties(paramMap, note);
			int result = this.service.updateNote(note);
			if (result != 0) {
				resultMap.put("status", "200");
				resultMap.put("message", "更新成功");
			} else {
				resultMap.put("status", "707");
				resultMap.put("message", "更新失败：此笔记不存在或处理异常");
			}
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("修改笔记异常");
		}
	}
}
