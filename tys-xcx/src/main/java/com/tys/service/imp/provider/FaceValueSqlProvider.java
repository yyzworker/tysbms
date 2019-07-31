package com.tys.service.imp.provider;

import com.tys.entity.vo.DetectScoreVo;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author haoxu
 * @Date 2019/4/15 16:59
 **/
public class FaceValueSqlProvider {

    public String selectFaceValueBySorce(Byte sex, DetectScoreVo scoreVo){
        StringBuffer sql =  new StringBuffer("select id, sex, test_items, test_results, face_value from facevalue where status = 1 and sex = "+sex);
        sql.append(" and (");
        boolean began = false;
        if(scoreVo.getAxunge() != null){
            double axunge = scoreVo.getAxunge();
            began = true;
            sql.append(" ( test_items = 1 and ");
            if( axunge < 0.1){
                sql.append("test_results = 1");
            }else if(axunge >=0.1 && axunge < 0.4){
                sql.append("test_results = 2");
            }else {
                sql.append("test_results = 3");
            }
            sql.append(")");
        }
        if(scoreVo.getWrinkle() != null){
            double wrinkle = scoreVo.getWrinkle();
            if(!began){
                began = true;
            }else {
                sql.append(" or ");
            }
            sql.append("(test_items = 2  and ");
            if( wrinkle < 0.1){
                sql.append("test_results = 1");
            }else if(wrinkle >=0.1 && wrinkle <0.4){
                sql.append("test_results = 2");
            }else {
                sql.append("test_results = 3");
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
            sql.append("(test_items = 3  and ");
            if( fleck < 0.1){
                sql.append("test_results = 1");
            }else if(fleck >=0.1 && fleck <0.4){
                sql.append("test_results = 2");
            }else {
                sql.append("test_results = 3");
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
            sql.append("(test_items = 4  and ");
            if( proe < 0.1){
                sql.append("test_results = 1");
            }else if(proe >=0.1 && proe <0.2){
                sql.append("test_results = 2");
            }else {
                sql.append("test_results = 3");
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
            sql.append("(test_items = 5 and ");
            if( acne < 0.03){
                sql.append("test_results = 1");
            }else if(acne >=0.03 && acne <0.1){
                sql.append("test_results = 2");
            }else {
                sql.append("test_results = 3");
            }
            sql.append(")");
        }
        sql.append(")");
        return sql.toString();
    }
}
