package cn.tmq.service.notebook.entity;

public class MBlogs {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column m_blogs.id
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	private String id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column m_blogs.user_id
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column m_blogs.category_id
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	private Integer categoryId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column m_blogs.title
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	private String title;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column m_blogs.content
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	private String content;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column m_blogs.display_content
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	private String displayContent;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column m_blogs.id
	 * @return  the value of m_blogs.id
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column m_blogs.id
	 * @param id  the value for m_blogs.id
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column m_blogs.user_id
	 * @return  the value of m_blogs.user_id
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column m_blogs.user_id
	 * @param userId  the value for m_blogs.user_id
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column m_blogs.category_id
	 * @return  the value of m_blogs.category_id
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column m_blogs.category_id
	 * @param categoryId  the value for m_blogs.category_id
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column m_blogs.title
	 * @return  the value of m_blogs.title
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column m_blogs.title
	 * @param title  the value for m_blogs.title
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column m_blogs.content
	 * @return  the value of m_blogs.content
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column m_blogs.content
	 * @param content  the value for m_blogs.content
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column m_blogs.display_content
	 * @return  the value of m_blogs.display_content
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public String getDisplayContent() {
		return displayContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column m_blogs.display_content
	 * @param displayContent  the value for m_blogs.display_content
	 * @mbg.generated  Wed Apr 03 23:28:58 CST 2019
	 */
	public void setDisplayContent(String displayContent) {
		this.displayContent = displayContent;
	}
}