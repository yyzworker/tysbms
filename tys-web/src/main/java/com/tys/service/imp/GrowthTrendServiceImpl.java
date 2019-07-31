package com.tys.service.imp;

import com.tys.entity.vo.GrowthTrendVo;
import com.tys.entity.vo.MemberAnalysisVo;
import com.tys.service.GrowthTrendService;
import com.tys.service.imp.dao.GrowthTrendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GrowthTrendServiceImpl implements GrowthTrendService {
    @Autowired
    private GrowthTrendMapper growthTrendMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "DataCenterCacheListDetectRecordByDay" , unless="#result == null")
    public List<GrowthTrendVo> listDetectRecordByDay() {
        List<GrowthTrendVo> growthTrendVoList = growthTrendMapper.listDetectRecordByDay();
        List<GrowthTrendVo> result = processDay(growthTrendVoList);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "DataCenterCacheListDetectRecordByWeek" , unless="#result == null")
    public List<GrowthTrendVo> listDetectRecordByWeek() {
        List<GrowthTrendVo> growthTrendVoList = growthTrendMapper.listDetectRecordByWeek();
        List<GrowthTrendVo> result = processWeek(growthTrendVoList);
        return  result;
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "DataCenterCacheListDetectRecordByMonth" , unless="#result == null")
    public List<GrowthTrendVo> listDetectRecordByMonth() throws ParseException{
        List<GrowthTrendVo> growthTrendVoList = growthTrendMapper.listDetectRecordByMonth();
        List<GrowthTrendVo> result = processMonth(growthTrendVoList);
        return  result;

    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "DataCenterCacheListMemberByDay" , unless="#result == null")
    public List<GrowthTrendVo> listMemberByDay() {
        List<GrowthTrendVo> growthTrendVoList = growthTrendMapper.listMemberByDay();
        List<GrowthTrendVo> result = processDay(growthTrendVoList);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "DataCenterCacheListMemberByWeek" , unless="#result == null")
    public List<GrowthTrendVo> listMemberByWeek() {
        List<GrowthTrendVo> growthTrendVoList = growthTrendMapper.listMemberByWeek();
        List<GrowthTrendVo> result = processWeek(growthTrendVoList);
        return  result;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "DataCenterCacheListMemberByMonth" , unless="#result == null")
    public List<GrowthTrendVo> listMemberByMonth() throws ParseException{
        List<GrowthTrendVo> growthTrendVoList = growthTrendMapper.listMemberByMonth();
        List<GrowthTrendVo> result = processMonth(growthTrendVoList);
        return  result;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "DataCenterCacheListMemberSex" , unless="#result == null")
    public List<GrowthTrendVo> listMemberSex() {
        List<GrowthTrendVo> growthTrendVoList = growthTrendMapper.listMemberSex();
        List<GrowthTrendVo> result = new ArrayList<>();
        Map<String,Integer> sexMap = new HashMap<>();
        for (GrowthTrendVo growthTrendVo : growthTrendVoList) {
            sexMap.put(growthTrendVo.getTime(),growthTrendVo.getCount());
        }
        if(ObjectUtils.isEmpty(sexMap)){
            result.add(new GrowthTrendVo("男",0));
            result.add(new GrowthTrendVo("女",0));
            result.add(new GrowthTrendVo("其他",0));
        }else{
            if(sexMap.get("男")==null){
                result.add(new GrowthTrendVo("男",0));
            }else{
                result.add(new GrowthTrendVo("男",sexMap.get("男")));
            }
            if(sexMap.get("女")==null){
                result.add(new GrowthTrendVo("女",0));
            }else{
                result.add(new GrowthTrendVo("女",sexMap.get("女")));
            }
            if(sexMap.get("其他")==null){
                result.add(new GrowthTrendVo("其他",0));
            }else{
                result.add(new GrowthTrendVo("其他",sexMap.get("其他")));
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "DataCenterCacheListAgeRatio" , unless="#result == null")
    public Map<String,List<Integer>> listAgeRatio() {
        List<MemberAnalysisVo> memberAnalysisVoList = growthTrendMapper.listAgeRatio();
        Map<Integer,MemberAnalysisVo> ageMap = new HashMap<>();
        Map<String,List<Integer>> result = new HashMap<>();
        List<Integer> ageRatioMan = new ArrayList<>();
        List<Integer> ageRatioWoman = new ArrayList<>();
        List<Integer> ageRatioTotal = new ArrayList<>();
        for (MemberAnalysisVo memberAnalysisVo : memberAnalysisVoList) {
            ageMap.put(memberAnalysisVo.getAgeRatio(),memberAnalysisVo);
        }
        for(int i=1;i<=11;i++){
            MemberAnalysisVo memberAnalysisVo= ageMap.get(i);
            if(memberAnalysisVo == null){
                ageRatioMan.add(0);
                ageRatioWoman.add(0);
                ageRatioTotal.add(0);
            }else{
                ageRatioMan.add(memberAnalysisVo.getMan());
                ageRatioWoman.add(memberAnalysisVo.getWoman());
                ageRatioTotal.add(memberAnalysisVo.getTotal());
            }
        }
        result.put("ageRatioMan",ageRatioMan);
        result.put("ageRatioWoman",ageRatioWoman);
        result.put("ageRatioTotal",ageRatioTotal);
        return result;
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param startTime
     *            开始日期
     * @param endTime
     *            结束日期
     * @return
     */
    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }


    /**
     * 获取两个日期之间的所有月份
     *
     * @param minDate
     *            开始日期
     * @param maxDate
     *            结束日期
     * @return
     */
    private static List<String> getMonths(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 根据开始周和结束日期计算所有的周
     * @param startWeek
     * @param endWeek
     * @return
     */
    private List<String> getWeeks(String startWeek, String endWeek) {
        List<String> weeks = new ArrayList<>();
        int maxWeek = 54;
        String startYear = startWeek.substring(0,4);
        String startOfWeek = startWeek.substring(4,6);
        String endYear = endWeek.substring(0,4);
        String endOfWeek = endWeek.substring(4,6);
        if(startWeek.equals(endWeek)){
            weeks.add(startWeek);
            return weeks;
        }else if(startYear.equals(endYear)){
            for(int i =Integer.parseInt(startOfWeek);i<=Integer.parseInt(endOfWeek);i++){
                weeks.add(startYear+( i>9 ?String.valueOf(i) : "0"+i));
            }
        }else{
            for(int i =Integer.parseInt(startYear);i<=Integer.parseInt(endYear);i++){
                if(i==Integer.parseInt(startYear)){
                    for(int j =Integer.parseInt(startOfWeek);j<=maxWeek;j++){
                        weeks.add(startYear+( j>9 ?String.valueOf(j) : "0"+j));
                    }
                }else if(i==Integer.parseInt(endYear)){
                    for(int j =1;j<=Integer.parseInt(endOfWeek);j++){
                        weeks.add(endYear+( j>9 ?String.valueOf(j) : "0"+j));
                    }
                }else{
                    for(int j =1;j<=maxWeek;j++){
                        weeks.add(i+( j>9 ?String.valueOf(j) : "0"+j));
                    }
                }
            }
        }
        return weeks;
    }


    private int getCurrentWeek(String today){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = format.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);

    }

    private List<GrowthTrendVo> processDay(List<GrowthTrendVo> growthTrendVoList){
        List<GrowthTrendVo> result = new ArrayList<>();
        Map<String,Integer> timeMap = new HashMap<>();
        for (GrowthTrendVo trendVo : growthTrendVoList) {
            timeMap.put(trendVo.getTime(),trendVo.getCount());
        }
        //查询到了数据
        if(!ObjectUtils.isEmpty(timeMap)){
            String startDay = growthTrendVoList.get(0).getTime();
            Calendar cal=Calendar.getInstance();
            cal.add(Calendar.DATE,-1);
            Date d=cal.getTime();
            SimpleDateFormat sp=new SimpleDateFormat("yyyyMMdd");
            String endDay=sp.format(d);//获取昨天日期
            List<String> days = getDays(startDay,endDay);
            for (String day : days) {
                if(timeMap.get(day) != null){
                    GrowthTrendVo trendVo = new GrowthTrendVo(day,timeMap.get(day));
                    result.add(trendVo);
                }else{
                    GrowthTrendVo trendVo = new GrowthTrendVo(day,0);
                    result.add(trendVo);
                }
            }
        }
        return result;
    }
    private List<GrowthTrendVo> processWeek(List<GrowthTrendVo> growthTrendVoList){
        List<GrowthTrendVo> result = new ArrayList<>();
        Map<String,Integer> timeMap = new HashMap<>();
        for (GrowthTrendVo trendVo : growthTrendVoList) {
            timeMap.put(trendVo.getTime(),trendVo.getCount());
        }
        //查询到了数据
        if(!ObjectUtils.isEmpty(timeMap)){
            String startWeek = growthTrendVoList.get(0).getTime();
            Calendar cal=Calendar.getInstance();
            cal.add(Calendar.DATE,-1);
            Date d=cal.getTime();
            SimpleDateFormat sp=new SimpleDateFormat("yyyyMMdd");
            String endDay=sp.format(d);//获取昨天日期
            int currentWeek = getCurrentWeek(endDay);
            String endWeek = endDay.substring(0,4) + (currentWeek>9 ? String.valueOf(currentWeek):"0"+currentWeek);
            List<String> weeks = getWeeks(startWeek,endWeek);
            for (String week : weeks) {
                if(timeMap.get(week) != null){
                    GrowthTrendVo trendVo = new GrowthTrendVo(week,timeMap.get(week));
                    result.add(trendVo);
                }else{
                    GrowthTrendVo trendVo = new GrowthTrendVo(week,0);
                    result.add(trendVo);
                }
            }
        }
        return result;
    }
    private List<GrowthTrendVo> processMonth(List<GrowthTrendVo> growthTrendVoList) throws ParseException{
        List<GrowthTrendVo> result = new ArrayList<>();
        Map<String,Integer> timeMap = new HashMap<>();
        for (GrowthTrendVo trendVo : growthTrendVoList) {
            timeMap.put(trendVo.getTime(),trendVo.getCount());
        }
        //查询到了数据
        if(!ObjectUtils.isEmpty(timeMap)){
            String startMonth = growthTrendVoList.get(0).getTime();
            Calendar cal=Calendar.getInstance();
            cal.add(Calendar.DATE,-1);
            Date d=cal.getTime();
            SimpleDateFormat sp=new SimpleDateFormat("yyyyMM");
            String endMonth=sp.format(d);//获取昨天日期
            List<String> months = getMonths(startMonth,endMonth);
            for (String month : months) {
                if(timeMap.get(month) != null){
                    GrowthTrendVo trendVo = new GrowthTrendVo(month,timeMap.get(month));
                    result.add(trendVo);
                }else{
                    GrowthTrendVo trendVo = new GrowthTrendVo(month,0);
                    result.add(trendVo);
                }
            }
        }
        return result;
    }


}
