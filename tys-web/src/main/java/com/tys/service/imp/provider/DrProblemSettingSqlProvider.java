package com.tys.service.imp.provider;

import com.tys.entity.vo.DetectScoreVo;
import com.tys.entity.vo.DrProblemSetting;
import org.apache.ibatis.jdbc.SQL;

public class DrProblemSettingSqlProvider {

    public String insertSelective(DrProblemSetting record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("dr_problemsetting");
        
        if (record.getAnalysisProject() != null) {
            sql.VALUES("analysis_project", "#{analysisProject,jdbcType=TINYINT}");
        }
        
        if (record.getLevel() != null) {
            sql.VALUES("level", "#{level,jdbcType=TINYINT}");
        }
        
        if (record.getMinValue() != null) {
            sql.VALUES("min_value", "#{minValue,jdbcType=DOUBLE}");
        }
        
        if (record.getMaxValue() != null) {
            sql.VALUES("max_value", "#{maxValue,jdbcType=DOUBLE}");
        }
        
        if (record.getSkinType() != null) {
            sql.VALUES("skin_type", "#{skinType,jdbcType=TINYINT}");
        }

        if (record.getSensitiveType() != null) {
            sql.VALUES("sensitive_type", "#{sensitiveType,jdbcType=TINYINT}");
        }

        if (record.getDescriptionType() != null) {
            sql.VALUES("description_type", "#{descriptionType,jdbcType=TINYINT}");
        }
        
        if (record.getDescriptionContent() != null) {
            sql.VALUES("description_content", "#{descriptionContent,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DrProblemSetting record) {
        SQL sql = new SQL();
        sql.UPDATE("dr_problemsetting");
        
        if (record.getAnalysisProject() != null) {
            sql.SET("analysis_project = #{analysisProject,jdbcType=TINYINT}");
        }
        
        if (record.getLevel() != null) {
            sql.SET("level = #{level,jdbcType=TINYINT}");
        }
        
        if (record.getMinValue() != null) {
            sql.SET("min_value = #{minValue,jdbcType=DOUBLE}");
        }
        
        if (record.getMaxValue() != null) {
            sql.SET("max_value = #{maxValue,jdbcType=DOUBLE}");
        }
        
        if (record.getSkinType() != null) {
            sql.SET("skin_type = #{skinType,jdbcType=TINYINT}");
        }

        if (record.getSensitiveType() != null) {
            sql.SET("sensitive_type = #{sensitiveType,jdbcType=TINYINT}");
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

    public String query(DrProblemSetting record){
        return new SQL(){
            {
                SELECT("id, analysis_project, level, min_value, max_value, skin_type,sensitive_type, description_type, description_content");
                FROM("dr_problemsetting");
                WHERE("status = 1");
                if(record.getAnalysisProject() != null){
                    WHERE("analysis_project = #{analysisProject,jdbcType=TINYINT}");
                }
                if(record.getLevel() != null){
                    WHERE("level = #{level,jdbcType=TINYINT}");
                }
                if(record.getSkinType() != null){
                    WHERE("skin_type = #{skinType,jdbcType=TINYINT}");
                }
                if(record.getSensitiveType() != null){
                    WHERE("sensitive_type = #{sensitiveType,jdbcType=TINYINT}");
                }
                if(record.getDescriptionType() != null){
                    WHERE("description_type = #{descriptionType,jdbcType=TINYINT}");
                }
                if(record.getDescriptionContent() != null){
                    WHERE("description_content like CONCAT('%',#{descriptionContent,jdbcType=VARCHAR},'%')");
                }
            }
        }.toString();
    }

    public String selectDrProblemByScore(Byte skinType, DetectScoreVo scoreVo){
        StringBuffer sql = new StringBuffer("select id, analysis_project, skin_type, sensitive_type, description_type ,description_content from dr_problemsetting where status = 1 and sensitive_type = 0 and skin_type = "+skinType);
        sql.append(" and (");
        boolean began = false;
        if(scoreVo.getWrinkle() != null){
            double wrinkle = scoreVo.getWrinkle();
            began = true;
            sql.append("(analysis_project = 1  and ");
            if( wrinkle < 0.1){
                sql.append("level = 1");
            }else if(wrinkle >=0.1 && wrinkle <0.4){
                sql.append("level = 2");
            }else {
                sql.append("level = 3");
            }
            sql.append(")");
        }
        if(scoreVo.getFleck() != null){
            double fleck = scoreVo.getFleck();
            if(!began){
                began = true;
            }else {
                sql.append(" or ");
            }
            sql.append("(analysis_project = 2  and ");
            if( fleck < 0.1){
                sql.append("level = 1");
            }else if(fleck >=0.1 && fleck <0.4){
                sql.append("level = 2");
            }else {
                sql.append("level = 3");
            }
            sql.append(")");
        }
        if(scoreVo.getFleck() != null){
            double fleck = scoreVo.getFleck();
            if(!began){
                began = true;
            }else {
                sql.append(" or ");
            }
            sql.append("(analysis_project = 3  and ");
            if( fleck < 0.1){
                sql.append("level = 1");
            }else if(fleck >=0.1 && fleck <0.4){
                sql.append("level = 2");
            }else {
                sql.append("level = 3");
            }
            sql.append(")");
        }
        if(scoreVo.getPore() != null){
            double proe = scoreVo.getPore();
            if(!began){
                began = true;
            }else {
                sql.append(" or ");
            }
            sql.append("(analysis_project = 4  and ");
            if( proe < 0.1){
                sql.append("level = 1");
            }else if(proe >=0.1 && proe <0.2){
                sql.append("level = 2");
            }else {
                sql.append("level = 3");
            }
            sql.append(")");
        }
        if(scoreVo.getAcne() != null){
            double acne = scoreVo.getAcne();
            if(!began){
                began = true;
            }else {
                sql.append(" or ");
            }
            sql.append("(analysis_project = 5  and ");
            if( acne < 0.03){
                sql.append("level = 1");
            }else if(acne >=0.03 && acne <0.1){
                sql.append("level = 2");
            }else {
                sql.append("level = 3");
            }
            sql.append(")");
        }
        sql.append(")");
        return sql.toString();
    }
}