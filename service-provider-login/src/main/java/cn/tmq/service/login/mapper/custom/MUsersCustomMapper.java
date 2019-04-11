package cn.tmq.service.login.mapper.custom;

import org.apache.ibatis.annotations.Select;

import cn.tmq.service.login.entity.MUsers;
import cn.tmq.service.login.entity.custom.MUserBean;

public interface MUsersCustomMapper {

	@Select("select id, account, password, level from m_users where account = #{account}")
	MUserBean selectUserByAccount(MUsers user);
}
