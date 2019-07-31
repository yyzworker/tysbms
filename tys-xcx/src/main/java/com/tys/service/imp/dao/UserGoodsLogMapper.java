package com.tys.service.imp.dao;

import com.tys.entity.vo.UserGoodsLog;
import com.tys.entity.vo.UserShareLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

public interface UserGoodsLogMapper {

    @Insert({
            "<script>",
            "insert into usergoodslog ",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"userId != null\">",
            "userid,",
            "</if>",
            "<if test=\"goodsId != null\">",
            "goodsid,",
            "</if>",
            "<if test=\"createDate != null\">",
            "createdate,",
            "</if>",
            "<if test=\"point != null\">",
            "point,",
            "</if>",
            "<if test=\"demo != null\">",
            "demo,",
            "</if>",
            "<if test=\"phone != null\">",
            "phone,",
            "</if>",
            "<if test=\"name != null\">",
            "name,",
            "</if>",
            "<if test=\"address != null\">",
            "address,",
            "</if>",
            "</trim>",
            "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\"> ",
            "<if test=\"userId != null\">",
            "#{userId,jdbcType=INTEGER},",
            "</if>",
            "<if test=\"goodsId != null\">",
            "#{goodsId,jdbcType=INTEGER},",
            "</if>",

            "<if test=\"createDate != null\">",
            "#{createDate,jdbcType=TIMESTAMP}",
            "</if>",
            "<if test=\"point != null\">",
            "#{point,jdbcType=INTEGER},",
            "</if>",
            "<if test=\"demo != null\">",
            "#{demo,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"phone != null\">",
            "#{phone,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"name != null\">",
            "#{name,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"address != null\">",
            "#{address,jdbcType=VARCHAR},",
            "</if>",
            "</trim>",
            "</script>"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    int insertUserGoodsLog(UserGoodsLog userGoodsLog);
}
