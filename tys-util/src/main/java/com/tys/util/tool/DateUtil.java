package com.tys.util.tool;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author haoxu
 * @Date 2019/3/14 15:27
 **/
public class DateUtil {
    //由出生日期获得年龄
    public static Short getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return Short.valueOf(age + "");
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取今天还剩下多少秒
     *
     * @return
     */
    public static long getRemainSecByToday(Timestamp time) {

        Calendar calendar = Calendar.getInstance();
        if(time!=null){
            calendar.setTime(time);
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间：" + df.format(calendar.getTime()));
        // 将日期调整为次日零时，即当前日期加一天，时、分、秒、毫秒都置零。
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
//        System.out.println("次日零时：" + df.format(calendar.getTime()));
//        System.out.println("从现在到凌晨还剩余：" + (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000 + " s");
        return (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

}
