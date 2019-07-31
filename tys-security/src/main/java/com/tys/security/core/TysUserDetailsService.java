package com.tys.security.core;

import com.tys.entity.vo.MenuVo;
import com.tys.entity.vo.RoleVo;
import com.tys.entity.vo.UserVo;
import com.tys.security.service.RoleService;
import com.tys.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-2-27 16:31
 */
@Service
public class TysUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userservice;

	@Autowired
	private RoleService roleService;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserVo> listuser=userservice.getUserByUsername(username);
		if(listuser.size()==0){
			throw new UsernameNotFoundException("not find user:"+username);
		}
		UserVo user=listuser.get(0);
		String userId = user.getUserId();
		List<MenuVo> menuList = roleService.getMenuByUserid(Integer.parseInt(userId));
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		for(MenuVo mv :menuList ){
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_" + mv.getPath());
			list.add(au);
		}
		boolean unlocked = user.getUserLock() == 0 ? true :false;
        SessionUser sessionuser=new SessionUser(user.getUserName(),user.getUserPwd(),true,true,true,unlocked, list);
        sessionuser.setUserId(user.getUserId());
        sessionuser.setDisplayName(user.getUserName());
        sessionuser.setUserPhone(user.getUserPhone());
		sessionuser.setDisplayName(user.getDisplayName());

		return sessionuser;
	}
}
