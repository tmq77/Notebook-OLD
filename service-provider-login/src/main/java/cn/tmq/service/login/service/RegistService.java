package cn.tmq.service.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tmq.service.login.entity.MUsers;
import cn.tmq.service.login.mapper.MUsersMapper;
import cn.tmq.service.login.mapper.custom.MUsersCustomMapper;

/**
 * 注册业务处理.
 * @author 陶敏麒
 *
 */
@Service
public class RegistService {
	
	@Autowired
	private MUsersMapper mUsersMapper;
	/**
	 * 注册用户.
	 * @param user 注册信息
	 * @return 0:失败,1成功
	 */
	public int registUserInfo(MUsers user) {
		return this.mUsersMapper.insert(user);
	}
}
