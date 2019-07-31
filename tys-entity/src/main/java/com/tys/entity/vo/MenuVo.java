package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author ：王志浩
 * @date ：Created in 2019-4-17 16:31
 */
@ApiModel(description = "菜单信息")
public class MenuVo  extends BaseEntity implements Serializable {
	@ApiModelProperty(value = "菜单主键", required = false)
    private Integer id;
    
    @ApiModelProperty(value = "菜单名称", required = false)
    private String menuName;

    @ApiModelProperty(value = "父节点ID", required = false)
    private Integer pid;

    @ApiModelProperty(value = "排序", required = false)
    private Integer sort;

    @ApiModelProperty(value = "图标", required = false)
    private String icon;

    @ApiModelProperty(value = "路径", required = false)
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}