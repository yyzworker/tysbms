import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tys.Application;
import com.tys.service.ApkMd5;
import com.tys.service.EsDateImport;
import com.tys.service.RedisInit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.util.*;

/**
 * @Author haoxu
 * @Date 2019/3/28 9:51
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BaseTest {

    @Autowired
    EsDateImport esDateImport;

    @Autowired
    ApkMd5 apkMd5;

    @Autowired
    RedisInit redisInit;

    @Test
    public void testRedisInit() {
        redisInit.initRedis();
    }

    @Test
    public void testCompositionImport() throws Exception{
        esDateImport.compositionImport("D:\\svn\\Product_Design\\产品管理\\03 产品\\P01002 调颜室第二版（量产100台）\\01 需求\\02 原始数据及材料\\成分数据表.xls");
    }
    @Test
    public void testCommodityImport() throws Exception{
        esDateImport.commodityImport("C:\\Users\\Administrator\\Desktop\\美丽修行数据汇总-修改成分分隔符.xlsx");
    }
    @Test
    public void testCommodityUpdate() throws Exception{
        esDateImport.commodityUpdate("C:\\Users\\Administrator\\Desktop\\美丽修行数据汇总-修改成分分隔符.xlsx");
    }
    private static final Function<String, Integer> LENGTH_FUNCTION =
            new Function<String, Integer>() {
                @Override
                public Integer apply(String input) {
                    return input.length();
                }
            };

    @Test
    public void testAsMap() {
        Set<String> strings = ImmutableSet.of("one", "two", "three");
        Map<String, Integer> map = ImmutableMap.of("one", 3, "two", 3, "three", 5);

    }

    @Test
    public void md5() throws Exception{
        File file = new File("C:\\Users\\Administrator\\Desktop\\skindetector_20190514_1.2.2.apk");
        apkMd5.md5(file);
    }
}
