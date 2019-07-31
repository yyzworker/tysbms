package com.tys.service.imp.provider;

import com.tys.entity.vo.DetectScoreVo;

public class DrProblemSettingSqlProvider {
    public String selectDrProblemByScore(Byte skinType, DetectScoreVo scoreVo){
        StringBuffer sql = new StringBuffer("select id, analysis_project, skin_type, sensitive_type, description_type ,description_content from dr_problemsetting where status = 1  and sensitive_type = 0 and  (skin_type = 0 or ( skin_type = "+skinType);
        sql.append(" and  ( ");
        boolean began = false;
        if(scoreVo.getAxunge() != null){
            double axunge = scoreVo.getAxunge();
            began = true;
            sql.append(" (analysis_project = 1  and ");
            if( axunge < 0.1){
                sql.append("level = 1");
            }else if(axunge >=0.1 && axunge <0.4){
                sql.append("level = 2");
            }else {
                sql.append("level = 3");
            }
            sql.append(")");
        }
        if(scoreVo.getWrinkle() != null){
            double wrinkle = scoreVo.getWrinkle();
            if(began){
               sql.append(" or "); 
            }else {
                began = true;
            }
            sql.append("(analysis_project = 2  and ");
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
            if(began){
                sql.append(" or ");
            }else {
                began = true;
            }
            sql.append("( analysis_project = 3  and ");
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
            if(began){
                sql.append(" or ");
            }else {
                began = true;
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
            if(began){
                sql.append(" or ");
            }else {
                began = true;
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
        sql.append(")))");
        return sql.toString();
    }
}