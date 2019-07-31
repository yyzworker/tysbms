package com.tys.entity.vo;

import java.io.Serializable;

/**
 * @Author haoxu
 * @Date 2019/3/27 14:06
 **/
public class DetectScoreVo implements Serializable {

    private Integer rid;

    /**
     * 油脂百分比
     */
    private Double axunge;

    /**
     * 斑点百分比
     */
    private Double fleck;

    /**
     * 毛孔百分比
     */
    private Double pore;

    /**
     * 皱纹百分比
     */
    private Double wrinkle;

    /**
     * 痤疮百分比
     */
    private Double acne;

    /**
     * 粗糙度百分比
     */
    private Double roughness;

    /**
     * 感染区百分比
     */
    private Double infection;


    public Double getAcne() {
        return acne;
    }

    public void setAcne(Double acne) {
        this.acne = acne;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Double getAxunge() {
        return axunge;
    }

    public void setAxunge(Double axunge) {
        this.axunge = axunge;
    }

    public Double getFleck() {
        return fleck;
    }

    public void setFleck(Double fleck) {
        this.fleck = fleck;
    }

    public Double getPore() {
        return pore;
    }

    public void setPore(Double pore) {
        this.pore = pore;
    }

    public Double getWrinkle() {
        return wrinkle;
    }

    public void setWrinkle(Double wrinkle) {
        this.wrinkle = wrinkle;
    }

    public Double getRoughness() {
        return roughness;
    }

    public void setRoughness(Double roughness) {
        this.roughness = roughness;
    }

    public Double getInfection() {
        return infection;
    }

    public void setInfection(Double infection) {
        this.infection = infection;
    }

}
