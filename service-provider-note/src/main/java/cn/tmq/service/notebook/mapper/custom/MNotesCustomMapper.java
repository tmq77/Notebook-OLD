package cn.tmq.service.notebook.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.tmq.service.notebook.entity.MNotes;

public interface MNotesCustomMapper {

	@Insert("insert into m_notes(id, user_id, title, content, display_content) values(#{id}, #{userId}, #{title}, #{content}, #{displayContent})")
	int insertNote(MNotes mNotes);
	
	@Select("select id, title from m_notes where user_id = #{userId} limit #{index}, 10")
	List<MNotes> selectAllNotesByUserId(@Param("userId") int userId, @Param("index") int index);
}
