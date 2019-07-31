package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author ：王志浩
 * @date ：Created in 2019-5-7 09:31
 */
@ApiModel(description = "会员分析")
public class MemberAnalysisVo implements Serializable {
    private int  ageRatio;
    private int man;
    private int woman;
    private int total;

    public MemberAnalysisVo(int ageRatio, int man, int woman, int total) {
        this.ageRatio = ageRatio;
        this.man = man;
        this.woman = woman;
        this.total = total;
    }
    public MemberAnalysisVo() {
    }

    public int getAgeRatio() {
        return ageRatio;
    }
    public void setAgeRatio(int ageRatio) {
        this.ageRatio = ageRatio;
    }
    public int getMan() {
        return man;
    }

    public void setMan(int man) {
        this.man = man;
    }

    public int getWoman() {
        return woman;
    }

    public void setWoman(int woman) {
        this.woman = woman;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}