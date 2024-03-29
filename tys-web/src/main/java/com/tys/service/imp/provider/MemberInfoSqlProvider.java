package com.tys.service.imp.provider;

import com.tys.entity.vo.MemberInfo;
import org.apache.ibatis.jdbc.SQL;

public class MemberInfoSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberinfo
     *
     * @mbg.generated Wed Mar 06 10:48:30 CST 2019
     */
    public String insertSelective(MemberInfo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("memberinfo");

        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }

        if (record.getPhone() != null) {
            sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }

        if (record.getSex() != null) {
            sql.VALUES("sex", "#{sex,jdbcType=TINYINT}");
        }

        if (record.getBirthdate() != null) {
            sql.VALUES("birthdate", "#{birthdate,jdbcType=TIMESTAMP}");
        }

        if (record.getAge() != null) {
            sql.VALUES("age", "#{age,jdbcType=INTEGER}");
        }

        if (record.getHeight() != null) {
            sql.VALUES("height", "#{height,jdbcType=INTEGER}");
        }

        if (record.getWeight() != null) {
            sql.VALUES("weight", "#{weight,jdbcType=INTEGER}");
        }

        if (record.getJobTag() != null) {
            sql.VALUES("job_tag", "#{jobTag,jdbcType=VARCHAR}");
        }

        if (record.getHobbyTag() != null) {
            sql.VALUES("hobby_tag", "#{hobbyTag,jdbcType=VARCHAR}");
        }

        if (record.getRegistrationDate() != null) {
            sql.VALUES("registration_date", "#{registrationDate,jdbcType=TIMESTAMP}");
        }

        if (record.getDetectionsNumber() != null) {
            sql.VALUES("detections_number", "#{detectionsNumber,jdbcType=INTEGER}");
        }

        if (record.getDetectionsLasttime() != null) {
            sql.VALUES("detections_lasttime", "#{detectionsLasttime,jdbcType=TIMESTAMP}");
        }

        if (record.getAvatarUrl() != null) {
            sql.VALUES("avatar_url", "#{avatarUrl,jdbcType=VARCHAR}");
        }

        if (record.getInviter() != null) {
            sql.VALUES("inviter", "#{inviter,jdbcType=INTEGER}");
        }

        return sql.toString();
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberinfo
     *
     * @mbg.generated Wed Mar 06 10:48:30 CST 2019
     */
    public String updateByPrimaryKeySelective(MemberInfo record) {
        SQL sql = new SQL();
        sql.UPDATE("memberinfo");

        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }

        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=VARCHAR}");
        }

        if (record.getSex() != null) {
            sql.SET("sex = #{sex,jdbcType=TINYINT}");
        }

        if (record.getBirthdate() != null) {
            sql.SET("birthdate = #{birthdate,jdbcType=TIMESTAMP}");
        }

        if (record.getAge() != null) {
            sql.SET("age = #{age,jdbcType=INTEGER}");
        }

        if (record.getHeight() != null) {
            sql.SET("height = #{height,jdbcType=INTEGER}");
        }

        if (record.getWeight() != null) {
            sql.SET("weight = #{weight,jdbcType=INTEGER}");
        }

        if (record.getJobTag() != null) {
            sql.SET("job_tag = #{jobTag,jdbcType=VARCHAR}");
        }

        if (record.getHobbyTag() != null) {
            sql.SET("hobby_tag = #{hobbyTag,jdbcType=VARCHAR}");
        }

        if (record.getRegistrationDate() != null) {
            sql.SET("registration_date = #{registrationDate,jdbcType=TIMESTAMP}");
        }

        if (record.getDetectionsNumber() != null) {
            sql.SET("detections_number = #{detectionsNumber,jdbcType=INTEGER}");
        }

        if (record.getDetectionsLasttime() != null) {
            sql.SET("detections_lasttime = #{detectionsLasttime,jdbcType=TIMESTAMP}");
        }

        if (record.getAvatarUrl() != null) {
            sql.SET("avatar_url = #{avatarUrl,jdbcType=VARCHAR}");
        }

        if (record.getInviter() != null) {
            sql.SET("inviter = #{inviter,jdbcType=INTEGER}");
        }


        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }

    public String queryMemberInfo(MemberInfo record) {
        return new SQL() {
            {
                SELECT("id, name, phone, sex, birthdate, age, height, weight, bust, waistline, buttline, job_tag, hobby_tag, registration_date, detections_number, silent_number, detections_lasttime, avatar_url,inviter");
                FROM("memberinfo");
                WHERE("status = 1");
                if (record.getPhone()!= null) {
                    WHERE("phone = #{phone,jdbcType=VARCHAR}");
                }
                if (record.getName() != null) {
                    WHERE("name like concat('%',#{name,jdbcType=VARCHAR},'%')");
                }
                if (record.getJobTag() != null) {
                    WHERE("jobtag like concat('%',#{jobTag,jdbcType=VARCHAR'},'%')");
                }
                if (record.getDetectionsLasttimeStart() != null) {
                    WHERE("detections_lasttime >= #{detectionsLasttimeStart,jdbcType=TIMESTAMP}");
                }
                if (record.getDetectionsLasttimeEnd() != null) {
                    WHERE("detections_lasttime <= #{detectionsLasttimeEnd,jdbcType=TIMESTAMP}");
                }
                if (record.getRegistrationDateStart() != null) {
                    WHERE("registration_date >= #{registrationDateStart,jdbcType=TIMESTAMP}");
                }
                if (record.getRegistrationDateEnd() != null) {
                    WHERE("registration_date <= #{registrationDateEnd,jdbcType=TIMESTAMP}");
                }
                if (record.getDetectionsNumberStart() != null) {
                    WHERE("(detections_number + IFNULL(silent_number,0)) >= #{detectionsNumberStart,jdbcType=INTEGER}");
                }
                if (record.getDetectionsNumberEnd() != null) {
                    WHERE("(detections_number + IFNULL(silent_number,0)) <= #{detectionsNumberEnd,jdbcType=INTEGER}");
                }
                if(record.getBirthdateStart() != null){
                    WHERE("birthdate >= #{birthdateStart,jdbcType=TIMESTAMP}");
                }
                if(record.getBirthdateEnd() != null){
                    WHERE("birthdate <= #{birthdateEnd,jdbcType=TIMESTAMP}");
                }
                if (record.getAgeStart() != null) {
                    WHERE("age >= #{ageStart,jdbcType=INTEGER}");
                }
                if (record.getAgeEnd() != null) {
                    WHERE("age <= #{ageEnd,jdbcType=INTEGER}");
                }
                if (record.getSex() != null) {
                    WHERE("sex = #{sex,jdbcType=TINYINT}");
                }
                if (record.getInviter() != null) {
                    WHERE("inviter = #{inviter,jdbcType=INTEGER}");
                }
                ORDER_BY("registration_date desc");
            }
        }.toString();
    }

}