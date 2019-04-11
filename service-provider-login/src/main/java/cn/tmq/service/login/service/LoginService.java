package cn.tmq.service.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tmq.service.login.entity.MUsers;
import cn.tmq.service.login.entity.custom.MUserBean;
import cn.tmq.service.login.mapper.custom.MUsersCustomMapper;

/**
 * 登录业务处理.
 * @author 陶敏麒
 *
 */
@Service
public class LoginService {

	@Autowired
	private MUsersCustomMapper mUserCustomMapper;
	
	/**
	 * 获取登录用户信息.
	 * @param user
	 * @return
	 */
	public MUserBean selectUserInfo(MUsers user) {
		return this.mUserCustomMapper.selectUserByAccount(user);
	}
}
