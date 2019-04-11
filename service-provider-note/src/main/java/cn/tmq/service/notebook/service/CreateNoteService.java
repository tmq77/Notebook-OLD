package cn.tmq.service.notebook.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tmq.service.notebook.entity.MNotes;
import cn.tmq.service.notebook.mapper.custom.MNotesCustomMapper;

/**
 * 新建笔记的业务处理
 * 2019年4月3日 23点33分
 * @author 陶敏麒
 * @version 1.0
 */
@Service
public class CreateNoteService {

	@Autowired
	private MNotesCustomMapper mapper;
	
	/**
	 * 插入数据
	 * @param mNotes
	 * @return 插入的数据数量
	 */
	public int insertBlog(MNotes mNote) {
		return mapper.insertNote(mNote);
	}
	
	/**
	 * 搜索对应用户的笔记
	 * @param userId 用户id
	 * @return 笔记列表
	 */
	public List<MNotes> selectAllNotesByUserId(int userId,int index) {
		return this.mapper.selectAllNotesByUserId(userId, index);
	}
	
	/**
	 * 获取用户所有的笔记的数量
	 * @param userId 用户id
	 * @return 笔记数量
	 */
	public int selectNotesCountByUserId(int userId) {
		return this.mapper.selectCountFromNotesByUserId(userId);
	}
}
