package cn.tmq.service.notebook.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.tmq.service.notebook.entity.MBlogs;

public interface MBlogsCustomMapper {

	@Insert("insert into m_blogs(id, user_id, title, content, display_content) values(#{id}, #{userId}, #{title}, #{content}, #{displayContent})")
	int insertBlog(MBlogs mBlogs);
	
	@Select("select id, title from m_blogs where user_id = #{userId} limit #{index}, 10")
	List<MBlogs> selectAllBlogsByUserId(@Param("userId") int userId, @Param("index") int index);
}
