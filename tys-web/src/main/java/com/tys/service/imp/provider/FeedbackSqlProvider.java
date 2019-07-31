package com.tys.service.imp.provider;


import com.tys.entity.vo.Feedback;
import org.apache.ibatis.jdbc.SQL;

public class FeedbackSqlProvider {
    public String queryFeedback(Feedback record) {
        return new SQL() {
            {
                SELECT("id, member_id, contact, content, status, create_time");
                FROM("feedback");
                if (record.getContact()!= null) {
                    WHERE("contact like concat('%',#{contact,jdbcType=VARCHAR},'%')");
                }
                if (record.getStatus() != null) {
                    WHERE("status = #{status,jdbcType=TINYINT}");
                }
                if (record.getCreateTimeStart() != null) {
                    WHERE("create_time >= #{createTimeStart,jdbcType=TIMESTAMP}");
                }
                if (record.getCreateTimeEnd() != null) {
                    WHERE("create_time <= #{createTimeEnd,jdbcType=TIMESTAMP}");
                }
                ORDER_BY("create_time desc");
            }
        }.toString();
    }
}
