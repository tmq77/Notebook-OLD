package cn.tmq.service.notebook.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tmq.service.notebook.entity.MNotes;
import cn.tmq.service.notebook.mapper.MNotesMapper;

@Service
public class UpdateNoteService {
	
	@Autowired
	private MNotesMapper mapper;
	
	/**
	 *  修改笔记
	 * @param id 笔记id
	 * @return
	 */
	public int updateNote(MNotes note) {
		if (note.getId() == null || !Pattern.matches("^[2-9][0-9]{17,}$", note.getId())) {
			// id合法性验证：纯数字并且大于等于18位并且开头为2(包含)以上
			return 0;
		}
		return this.mapper.updateByPrimaryKey(note);
	}
}
