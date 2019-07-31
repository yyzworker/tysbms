package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-2-27 16:31
 */
@ApiModel(description = "用户信息")
public class UserVo extends BaseEntity implements Serializable {
	@ApiModelProperty(value = "用户主键", required = false)
    private String userId;
    
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;
    
    @ApiModelProperty(value = "密码", required = true)
    private String userPwd;

    @ApiModelProperty(value = "用户姓名", required = true)
    private String displayName;
    
    @ApiModelProperty(value = "手机号", required = true)
    private String userPhone;

    @ApiModelProperty(value = "用户权限", required = false)
    private String userRank;

    @ApiModelProperty(value = "用户锁定状态", required = true)
    private Byte userLock;


    private List<RoleVo> roles;

    private String roleUserId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public Byte getUserLock() {
        return userLock;
    }

    public void setUserLock(Byte userLock) {
        this.userLock = userLock;
    }

    public List<RoleVo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVo> roles) {
        this.roles = roles;
    }

    public String getRoleUserId() {
        return roleUserId;
    }

    public void setRoleUserId(String roleUserId) {
        this.roleUserId = roleUserId;
    }
}