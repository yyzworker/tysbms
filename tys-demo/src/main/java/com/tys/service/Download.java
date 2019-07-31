package com.tys.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyuncs.exceptions.ClientException;

import java.io.File;

/**
 * @Author haoxu
 * @Date 2019/6/20 9:36
 **/
public class Download {
    public static void main(String[] args) throws ClientException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIXHSmc5UnRHKK";
        String accessKeySecret = "PtYYJP6THLNYPzalL7sQNYh49K0r3X";
        String bucketName = "dljy-xcx";
        String objectName = "allcfinfo";


        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("C:\\Users\\Administrator\\Desktop\\dljy-xcx"));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
