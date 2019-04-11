package cn.tmq.myNotebook.constants;

import java.io.Serializable;

public class Message<T> implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2679976229443210594L;
	
	/**
	 * 状态值
	 */
	private String status;
	
	/**
	 * 结果值
	 */
	private T result;
	/**
	 * 信息
	 */
	private String message;
	/**
	 * 微服务端口
	 */
	private String who;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 结果值
	 * @return the result
	 */
	public T getResult() {
		return result;
	}
	/**
	 * 结果值
	 * @param result the result to set
	 */
	public void setResult(T result) {
		this.result = result;
	}
	/**
	 * 信息
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 信息
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 微服务端口
	 * @return the who
	 */
	public String getWho() {
		return who;
	}
	/**
	 * 微服务端口
	 * @param who the who to set
	 */
	public void setWho(String who) {
		this.who = who;
	}
	
}
