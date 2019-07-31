package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author ：王志浩
 * @date ：Created in 2019-4-17 16:31
 */
@ApiModel(description = "角色信息")
public class RoleVo extends BaseEntity implements Serializable {
	@ApiModelProperty(value = "角色主键", required = false)
    private Integer roleId;
    
    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    @ApiModelProperty(value = "角色描述", required = false)
    private String roleDesc;

    private String menus;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
    public String getMenus() {
        return menus;
    }
    public void setMenus(String menus) {
        this.menus = menus;
    }
}