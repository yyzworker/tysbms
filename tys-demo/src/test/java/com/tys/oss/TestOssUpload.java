package com.tys.oss;

import com.aliyun.oss.OSSClient;
import com.tys.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestOssUpload {

    private static Logger logger = LoggerFactory.getLogger(TestOssUpload.class);
    static String fileName ="D:\\ossUpload.txt";

    @Test
    public void testUploadSingleThread() throws Exception{

        ThreadPoolExecutor tp = new ThreadPoolExecutor(10, 15, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(20000));

        while (true) {
          Thread.sleep(1000);
            tp.execute(new Runnable()
            {
                public void run()
                {
                    try{
                        uploadOssSingleThread("singleThread");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Test
    public void testUploadMultiThread() throws Exception{
        HashMap<String,String> mlxx = new HashMap<>();
        mlxx.put("1","线程1");
        mlxx.put("2","线程2");
        mlxx.put("3","线程3");
        mlxx.put("4","线程4");
        mlxx.put("5","线程5");
        mlxx.put("6","线程6");
        mlxx.put("7","线程7");
        mlxx.put("8","线程8");
        mlxx.put("9","线程9");
        mlxx.put("10","线程10");

        ThreadPoolExecutor tp = new ThreadPoolExecutor(10, 15, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(20000));
        Iterator iter = mlxx.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            tp.execute(new Runnable()
            {
                public void run()
                {
                    try{
                        uploadOss(entry.getValue().toString(),3);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }
        tp.shutdown();
        try
        {
            tp.awaitTermination(10, TimeUnit.DAYS);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }


    }




    public void uploadOssSingleThread(String threadName) throws Exception {
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
            String accessKeyId = "LTAIXHSmc5UnRHKK";
            String accessKeySecret = "PtYYJP6THLNYPzalL7sQNYh49K0r3X";

// 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
            InputStream inputStream = new FileInputStream("E:\\wgp.jpg");
            long begain = System.currentTimeMillis();
            ossClient.putObject("dljy-public-read", "bmsdemo/wgp" +threadName+ UUID.randomUUID().toString().replaceAll("-", ""), inputStream);
// 关闭OSSClient。
            ossClient.shutdown();
            long end = System.currentTimeMillis();
            long time = end - begain;
            WriteLog(time);

    }



    public static void WriteLog(long time) throws Exception{
        String log = "";
        if(time  > 10000){
             log ="cost[over"+10+"s]:" + time + "ms  " + time / 1000 + "s";
        }else if(time > 9000){
            log ="cost[over"+9+"s]:" + time + "ms  " + time / 1000 + "s";
        }else if(time > 8000){
            log ="cost[over"+8+"s]:" + time + "ms  " + time / 1000 + "s";
        }else if(time > 7000){
            log ="cost[over"+7+"s]:" + time + "ms  " + time / 1000 + "s";
        }else if(time > 6000){
            log ="cost[over"+6+"s]:" + time + "ms  " + time / 1000 + "s";
        }else if(time > 5000){
            log ="cost[over"+5+"s]:" + time + "ms  " + time / 1000 + "s";
        }else if(time > 4000){
            log ="cost[over"+4+"s]:" + time + "ms  " + time / 1000 + "s";
        }else if(time> 3000){
            log ="cost[over"+3+"s]:" + time + "ms  " + time / 1000 + "s";
        }else if(time > 2000){
            log ="cost[over"+2+"s]:" + time + "ms  " + time / 1000 + "s";
        }else if(time > 1000){
            log ="cost[over"+1+"s]:" + time + "ms  " + time / 1000 + "s";
        }else{
            log ="cost[over"+0+"s]:" + time + "ms  " + time / 1000 + "s";
        }
        logger.error(log);
        WriteFile(log);
    }

    public static void WriteFile( String content) {
        try {
// 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
// 文件长度，字节数
            long fileLength = randomFile.length();
// 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content+"\r\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void uploadOss(String threadName,int loopCount) throws Exception {
        for (int i = 0; i < loopCount; i++) {
            Thread.sleep(1000);
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
            String accessKeyId = "LTAIXHSmc5UnRHKK";
            String accessKeySecret = "PtYYJP6THLNYPzalL7sQNYh49K0r3X";

// 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
            InputStream inputStream = new FileInputStream("E:\\wgp.jpg");
            long begain = System.currentTimeMillis();
            ossClient.putObject("dljy-public-read", "bmsdemo/wgp" +threadName+ i, inputStream);
// 关闭OSSClient。
            ossClient.shutdown();
            long end = System.currentTimeMillis();
            long time = end - begain;
            WriteLog(time);

        }
    }
}

