package com.tys.constant;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Author haoxu
 * @Date 2019/4/10 14:13
 **/
public class Constant {

    public static final Map<String,Integer> COMMODITYTYPE = Maps.newHashMap();

    public static final String EMHEARTBEAT = "emHeartbeat:";

    public final static String THREED_KEY = "normal";

    public final static String THTREED_SUFFIX = "_ar";

    public final static String AI_URL = "http://192.168.0.13:22222/photos";

    public final static String PUSH_AI_FAILED = "push_ai_failed:";

    public static final String COSMETICS_CACHE_NAME = "MyCosmeticsCache";

    public static Map<Integer,String> SKINTYPE = ImmutableMap.of(1,"干性",2,"油性",3,"中性",4,"混合性");

    public static Map<Integer,String> EFFICACY = ImmutableMap.of(1,"除皱",2,"淡斑",3,"祛痘",4,"改善毛孔");

    {
        COMMODITYTYPE.put("洁面",1);
        COMMODITYTYPE.put("化妆水",2);
        COMMODITYTYPE.put("精华",3);
        COMMODITYTYPE.put("乳霜",4);
        COMMODITYTYPE.put("眼霜",5);
        COMMODITYTYPE.put("面膜",6);
        COMMODITYTYPE.put("防晒",7);
        COMMODITYTYPE.put("洗护",8);
    }

}
