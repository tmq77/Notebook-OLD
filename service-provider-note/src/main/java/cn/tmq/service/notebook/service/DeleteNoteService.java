package cn.tmq.service.notebook.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tmq.service.notebook.mapper.MNotesMapper;

@Service
public class DeleteNoteService {
	
	@Autowired
	private MNotesMapper mapper;
	
	/**
	 *  删除笔记
	 * @param id 笔记id
	 * @return
	 */
	public int deleteNote(String id) {
		if (id == null || !Pattern.matches("^[2-9][0-9]{17,}$", id)) {
			// id合法性验证：纯数字并且大于等于18位并且开头为2(包含)以上
			return 0;
		}
		return this.mapper.deleteByPrimaryKey(id);
	}
}
