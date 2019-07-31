package com.tys.security.service.impl.provider;

import com.tys.entity.vo.UserVo;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserSqlProvider {

    public String queryByEntity(UserVo record){
       return new SQL(){
           {
               SELECT("user_id,user_name,user_pwd,user_phone,display_name,user_rank,user_lock,status");
               FROM("user");
               WHERE("1 = 1");
               if(record.getDisplayName()!= null ){
                   WHERE("display_name like concat('%',#{displayName,jdbcType=VARCHAR},'%')");
               }
               if(record.getUserPhone() != null){
                   WHERE("user_phone like concat('%',#{userPhone,jdbcType=VARCHAR},'%')");
               }
               if(record.getUserLock() != null &&  !"".equals(record.getUserLock())){
                   WHERE("user_lock = #{userLock,jdbcType=TINYINT}");
               }
           }
       }.toString();
    }


    public String updateByPrimaryKey(UserVo record) {
        SQL sql = new SQL();
        sql.UPDATE("user");
        sql.SET("user_name = #{userName,jdbcType=VARCHAR}");
//        if(!"******".equals(record.getUserPwd())){
//            BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
//            String password = bpe.encode(record.getUserPwd());
//            record.setUserPwd(password);
//            sql.SET("user_pwd = #{userPwd,jdbcType=VARCHAR}");
//        }
        sql.SET("user_phone = #{userPhone,jdbcType=VARCHAR}");
        sql.SET("display_name = #{displayName,jdbcType=VARCHAR}");
        sql.SET("user_lock = #{userLock,jdbcType=TINYINT}");
        sql.SET("user_rank = #{userLock,jdbcType=VARCHAR}");
        sql.WHERE("user_id = #{userId,jdbcType=INTEGER}");
        return sql.toString();
    }

}