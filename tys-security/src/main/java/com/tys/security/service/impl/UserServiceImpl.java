package com.tys.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.RoleVo;
import com.tys.entity.vo.UserVo;
import com.tys.security.service.UserService;
import com.tys.security.service.impl.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-2-27 16:31
 */
@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private UserMapper usermapper;
	
	@Override
	public List<UserVo> getUserByUsername(String userName) {
		return usermapper.selectUserByUsername(userName);
	}
	
	
	@Override
	@Transactional
	public int insertUser(UserVo user) {
		List<UserVo> userVoList_before = getUserByUsername(user.getUserName());
		if(!ObjectUtils.isEmpty(userVoList_before)){
			return 2;
		}
		int result = usermapper.insertUser(user);
		if(result == 1){
			for (RoleVo role : user.getRoles()) {
				usermapper.insertRoleUser(role.getRoleId(),user.getUserId());
			}
		}
		return  result;
	}

	@Override
	public UserVo getUserByUserid(int userId) {
		List<UserVo> users = usermapper.selectUserByUserid(userId);
		if(users.size()==0){
			return null;
		}
		return users.get(0);
	}
	@Override
	@Transactional(readOnly = true)
	public PageInfo<UserVo> queryUser(UserVo user) {
		PageInfo<UserVo> pageInfo = null;
		if(user.getCurrentPage() != null && user.getPageSize() != null){
			pageInfo = PageHelper.startPage(user.getCurrentPage(), user.getPageSize()).doSelectPageInfo(() -> usermapper.queryByEntity(user));
		}else {
			pageInfo = new PageInfo(usermapper.queryByEntity(user));
		}
		return pageInfo;
	}

	@Override
	@Transactional
	public int updateUser(UserVo user) {
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		String password = bpe.encode(user.getUserPwd());
		if(!"******".equals(user.getUserPwd())){
			user.setUserPwd(password);
		}else{
			user.setUserPwd(null);
		}
		int result = usermapper.updateByPrimaryKey(user);
		//更新用户时，先删除掉映射表中的数据，再重新插入数据
		usermapper.deleteRoleUser(user.getUserId());
		//插入用户时，需要同时往role_user表中插值;
		for (RoleVo role : user.getRoles()) {
			usermapper.insertRoleUser(role.getRoleId(),user.getUserId());
		}
		return result;
	}
	@Override
	public UserVo getUserAndRoleByUserid(int userId) {
		List<UserVo> users = usermapper.selectUserAndRoleByUserid(userId);
		if(users.size()==0){
			return null;
		}
		UserVo user = users.get(0);
		user.setUserPwd("******");
		return user;
	}
}
