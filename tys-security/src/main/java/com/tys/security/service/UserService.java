package com.tys.security.service;


import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.UserVo;

import java.util.List;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-2-27 16:31
 */
public interface UserService {
	/**
	 * 根据username获取user
	 * 
	 * @param userName 用户名
	 * @return 返回用户集合
	 * */
	List<UserVo> getUserByUsername(String userName);
	/**
	 * 根据userid获取user
	 * 
	 * @param userName 用户名
	 * @return 返回用户集合
	 * */
	UserVo getUserByUserid(int userId);

	/**
	 * 创建用户
	 * 
	 * @param user 用户类
	 * @return 返回判断码：0：失败，1：成功
	 * */
	int insertUser(UserVo user);

	/**
	 * 查询账号列表
	 * @param user
	 * @return
	 */
	PageInfo<UserVo> queryUser(UserVo user);

	/**
	 *修改账号信息
	 * @param user
	 * @return
	 */
	int updateUser(UserVo user);

	/**
	 * 根据userid获取user
	 *
	 * @param userId 用户id
	 * @return 返回用户集合
	 * */
	UserVo getUserAndRoleByUserid(int userId);
}
