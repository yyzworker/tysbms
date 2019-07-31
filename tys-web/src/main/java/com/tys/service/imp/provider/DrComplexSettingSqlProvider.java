package com.tys.service.imp.provider;

import com.tys.entity.vo.DrComplexSetting;
import org.apache.ibatis.jdbc.SQL;

public class DrComplexSettingSqlProvider {

    public String insertSelective(DrComplexSetting record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("dr_complexsetting");
        
        if (record.getSkinType() != null) {
            sql.VALUES("skin_type", "#{skinType,jdbcType=TINYINT}");
        }

        if (record.getDescriptionType() != null) {
            sql.VALUES("description_type", "#{descriptionType,jdbcType=TINYINT}");
        }
        
        if (record.getDescriptionContent() != null) {
            sql.VALUES("description_content", "#{descriptionContent,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DrComplexSetting record) {
        SQL sql = new SQL();
        sql.UPDATE("dr_complexsetting");
        
        if (record.getSkinType() != null) {
            sql.SET("skin_type = #{skinType,jdbcType=TINYINT}");
        }

        if (record.getDescriptionType() != null) {
            sql.SET("description_type = #{descriptionType,jdbcType=TINYINT}");
        }
        
        if (record.getDescriptionContent() != null) {
            sql.SET("description_content = #{descriptionContent,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }


    public String query(DrComplexSetting record){
        return new SQL(){
            {
                SELECT("id, skin_type,description_type, description_content");
                FROM("dr_complexsetting");
                WHERE("status = 1");
                if(record.getSkinType() != null){
                    WHERE("skin_type = #{skinType,jdbcType=TINYINT}" );
                }
                if(record.getDescriptionType() != null){
                    WHERE("description_type = #{descriptionType,jdbcType=TINYINT}" );
                }
                if(record.getDescriptionContent() != null){
                    WHERE("description_content like CONCAT('%',#{descriptionContent,jdbcType=VARCHAR},'%')");
                }
            }
        }.toString();
    }
}