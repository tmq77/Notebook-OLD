package cn.tmq.service.notebook.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tmq.service.notebook.entity.MBlogs;
import cn.tmq.service.notebook.mapper.custom.MBlogsCustomMapper;

/**
 * 新建博客的业务处理
 * 2019年4月3日 23点33分
 * @author 陶敏麒
 * @version 1.0
 */
@Service
public class CreateBlogService {

	@Autowired
	private MBlogsCustomMapper mapper;
	
	/**
	 * 插入数据
	 * @param mBlogs
	 * @return
	 */
	public int insertBlog(MBlogs mBlogs) {
		return mapper.insertBlog(mBlogs);
	}
	
	/**
	 * 搜索对应用户的笔记
	 * @param userId
	 * @return
	 */
	public List<MBlogs> selectAllBlogsByUserId(int userId,int index) {
		return this.mapper.selectAllBlogsByUserId(userId, index);
	}
}
