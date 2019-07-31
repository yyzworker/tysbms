package com.tys.service.imp.provider;

import com.tys.entity.vo.DetectMemberVo;
import com.tys.entity.vo.DetectRecord;
import org.apache.ibatis.jdbc.SQL;

public class DetectRecordSqlProvider {

    public String insertSelective(DetectRecord record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("detectrecord");

        if (record.getMemberId() != null) {
            sql.VALUES("member_id", "#{memberId,jdbcType=INTEGER}");
        }

        if (record.getEquipmentId() != null) {
            sql.VALUES("equipment_id", "#{equipmentId,jdbcType=INTEGER}");
        }

        if (record.getDetectTime() != null) {
            sql.VALUES("detect_time", "#{detectTime,jdbcType=TIMESTAMP}");
        }

        if (record.getDetectLocation() != null) {
            sql.VALUES("detect_location", "#{detectLocation,jdbcType=VARCHAR}");
        }

        if (record.getDetectProvince() != null) {
            sql.VALUES("detect_province", "#{detectProvince,jdbcType=VARCHAR}");
        }

        if (record.getDetectCity() != null) {
            sql.VALUES("detect_city", "#{detectCity,jdbcType=VARCHAR}");
        }

        if (record.getPhotoPath() != null) {
            sql.VALUES("photo_path", "#{photoPath,jdbcType=VARCHAR}");
        }

        if (record.getResult() != null) {
            sql.VALUES("result", "#{result,jdbcType=VARCHAR}");
        }

        if (record.getData() != null) {
            sql.VALUES("data", "#{data,jdbcType=LONGVARCHAR}");
        }

        if (record.getUploadType() != null) {
            sql.VALUES("upload_type", "#{uploadType,jdbcType=TINYINT}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DetectRecord record) {
        SQL sql = new SQL();
        sql.UPDATE("detectrecord");

        if (record.getMemberId() != null) {
            sql.SET("member_id = #{memberId,jdbcType=INTEGER}");
        }

        if (record.getEquipmentId() != null) {
            sql.SET("equipment_id = #{equipmentId,jdbcType=INTEGER}");
        }

        if (record.getDetectTime() != null) {
            sql.SET("detect_time = #{detectTime,jdbcType=TIMESTAMP}");
        }

        if (record.getDetectLocation() != null) {
            sql.SET("detect_location = #{detectLocation,jdbcType=VARCHAR}");
        }


        if (record.getDetectProvince() != null) {
            sql.SET("detect_province = #{detectProvince,jdbcType=VARCHAR}");
        }

        if (record.getDetectCity() != null) {
            sql.SET("detect_city = #{detectCity,jdbcType=VARCHAR}");
        }

        if (record.getPhotoPath() != null) {
            sql.SET("photo_path = #{photoPath,jdbcType=VARCHAR}");
        }

        if (record.getResult() != null) {
            sql.SET("result = #{result,jdbcType=VARCHAR}");
        }

        if (record.getData() != null) {
            sql.SET("data = #{data,jdbcType=LONGVARCHAR}");
        }

        if (record.getUploadType() != null) {
            sql.VALUES("upload_type", "#{uploadType,jdbcType=TINYINT}");
        }

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }

    public String queryDetectMember(DetectMemberVo record){
        return new SQL(){
            {
                SELECT("d.id, d.member_id, d.equipment_id, d.detect_time, d.detect_location, d.photo_path, d.result",
                        "m.name, m.phone, m.sex, m.birthdate, m.age, m.height, m.weight, m.job_tag",
                        "m.hobby_tag, m.registration_date, m.detections_number, m.detections_lasttime, m.avatar_url,m.inviter",
                        "e.em_name");
                FROM("detectrecord d ");
                LEFT_OUTER_JOIN("memberinfo m on d.member_id = m.id");
                LEFT_OUTER_JOIN("equipment e on d.equipment_id = e.id");
                WHERE("d.status = 1");
                WHERE("m.status = 1");
                if(record.getMemberId() != null){
                    WHERE("d.member_id = #{memberId,jdbcType=INTEGER}");
                }
                if (record.getPhone()!= null) {
                    WHERE("m.phone = #{phone,jdbcType=VARCHAR}");
                }
                if (record.getName() != null) {
                    WHERE("m.name like concat('%',#{name,jdbcType=VARCHAR},'%')");
                }
                if (record.getInviter() != null) {
                    WHERE("m.inviter = #{inviter,jdbcType=VARCHAR}");
                }
                if (record.getSex() != null) {
                    WHERE("m.sex = #{sex,jdbcType=VARCHAR}");
                }
                if(record.getDetectProvince() != null){
                    WHERE("d.detect_province = #{detectProvince,jdbcType=VARCHAR}");
                }
                if(record.getDetectCity() != null){
                    WHERE("d.detect_city = #{detectCity,jdbcType=VARCHAR}");
                }
                if(record.getBirthdateStart() != null){
                    WHERE("m.birthdate >= #{birthdateStart,jdbcType=TIMESTAMP}");
                }
                if(record.getBirthdateEnd() != null){
                    WHERE("m.birthdate <= #{birthdateEnd,jdbcType=TIMESTAMP}");
                }
                if(record.getDetectTimeStart() != null){
                    WHERE("d.detect_time >= #{detectTimeStart,jdbcType=TIMESTAMP}");
                }
                if(record.getDetectTimeStart() != null){
                    WHERE("d.detect_time <= #{detectTimeEnd,jdbcType=TIMESTAMP}");
                }
                ORDER_BY("d.detect_time desc");
            }
        }.toString();
    }
}