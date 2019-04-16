package cn.tmq.service.notebook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tmq.service.notebook.entity.MNotes;
import cn.tmq.service.notebook.service.CreateNoteService;
import cn.tmq.service.notebook.utils.IdGeneratorUtil;

/**
 * 创建笔记
 * @author 陶敏麒
 *
 */
@RestController
public class CreateNoteController {

	@Autowired
	private CreateNoteService service;
	
	private Supplier<Map<String, Object>> supplier = HashMap<String,Object>::new;
	
	/**
	 *  获取对应用户所有的笔记数据，从第一页开始，每页十条
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/notes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> notes(@RequestParam Map<String, String> paramMap) throws Exception {
		try {
			Map<String, Object> resultMap = this.supplier.get();
			if (StringUtils.isEmpty(paramMap.get("id")) || !StringUtils.isNumeric(paramMap.get("pageIndex"))) {
				// 必须项目check
				resultMap.put("status", "700");
				resultMap.put("message", "必须项目不能为空");
				return resultMap;
			}
			// 10(n-1)
			int index = 10 * (Integer.parseInt(paramMap.get("pageIndex")) - 1);
			if (index < 0) {
				// 必须项目check
				resultMap.put("status", "707");
				resultMap.put("message", "页数不能为负");
				return resultMap;
			}
			int userId = Integer.parseInt(paramMap.get("id"));
			// 检索对应用户的所有笔记，以每页10条数据显示
			List<MNotes> resultList = this.service.selectAllNotesByUserId(userId, index);
			int count = this.service.selectNotesCountByUserId(userId);
			
			resultMap.put("status", "200");
			resultMap.put("addition", String.valueOf(count));
			resultMap.put("result", resultList);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			// 此处抛出异常会被服务调用者接收然后触发回退
			throw new Exception("获取笔记列表异常");
		}
	}
	
	/**
	 * 插入笔记数据
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/note", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> note(@RequestBody Map<String, String> paramMap) throws Exception {
		try {
			Map<String, Object> resultMap = this.supplier.get();
			// 必要项目为空,不予处理
			if (StringUtils.isEmpty(paramMap.get("title")) 
					|| StringUtils.isEmpty(paramMap.get("content"))
					|| StringUtils.isEmpty(paramMap.get("contentHtml")) 
					|| StringUtils.isEmpty(paramMap.get("id"))) {
				resultMap.put("status", "700");
				resultMap.put("message", "项目不满足要求");
				return resultMap;
			}
			// 参数设置
			MNotes mNotes = new MNotes();
			mNotes.setId(IdGeneratorUtil.generateId(paramMap.get("id")));
			mNotes.setUserId(Integer.parseInt(paramMap.get("id")));
			mNotes.setTitle(paramMap.get("title"));
			mNotes.setContent(paramMap.get("content"));
			mNotes.setDisplayContent(paramMap.get("contentHtml"));
			// 插入笔记数据
			int result = this.service.insertBlog(mNotes);
			if (0 == result) {
				resultMap.put("message", "数据插入失败");
			} else {
				resultMap.put("status", "200");
				resultMap.put("message", "数据插入成功");
			}
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			// 此处抛出异常会被服务调用者接收然后触发回退
			throw new Exception("新建笔记服务异常");
		}
	}
	
	// Test
	public static void main(String[] args) {
		System.out.println(Integer.parseInt("\u0967\u0968\u0969"));
	}
}
