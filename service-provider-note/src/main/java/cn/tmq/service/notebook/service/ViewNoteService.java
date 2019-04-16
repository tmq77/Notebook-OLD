package cn.tmq.service.notebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tmq.service.notebook.entity.MNotes;
import cn.tmq.service.notebook.mapper.MNotesMapper;

@Service
public class ViewNoteService {

	@Autowired
	private MNotesMapper mapper;
	
	public MNotes selectNotesById(String id) {
		return this.mapper.selectByPrimaryKey(id);
	} 
}
