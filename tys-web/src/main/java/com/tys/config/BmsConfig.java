package com.tys.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author haoxu
 * @Date 2019/3/29 16:51
 **/
@Configuration
public class BmsConfig {

    @Value("${constant.compositionPk}")
    private String compositionPk;

    @Value("${constant.commodityPk}")
    private String commodityPk;

    public String getCompositionPk() {
        return compositionPk;
    }

    public void setCompositionPk(String compositionPk) {
        this.compositionPk = compositionPk;
    }

    public String getCommodityPk() {
        return commodityPk;
    }

    public void setCommodityPk(String commodityPk) {
        this.commodityPk = commodityPk;
    }
}
