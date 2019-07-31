package com.tys.util.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

/**
 * @Author haoxu
 * @Date 2019/5/22 17:50
 **/
@Configuration
public class ElasticSearchConfig {

    @Value("${es.cluster-nodes}")
    private String custerNodes;

    @Value("${es.port}")
    private int port;

    @Value("${es.connect_time_out}")
    private int connect_time_out;

    @Value("${es.socket_time_out}")
    private int socket_time_out ;

    @Value("${es.connection_request_time_out}")
    private int connection_request_time_out ;

    @Value("${es.max_connect_num}")
    private int max_connect_num ;

    @Value("${es.max_connect_per_route}")
    private int max_connect_per_route ;

    @Value("${es.username}")
    private String userName;

    @Value("${es.password}")
    private String password;



    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestClientBuilder builder = RestClient.builder(new HttpHost(custerNodes,port));
        setConnectTimeOutConfig(builder);
        setMutiConnectConfig(builder);
        builder.setMaxRetryTimeoutMillis(5*60*1000);
        return new RestHighLevelClient(builder);
    }


    // 主要关于异步httpclient的连接延时配置
    private void setConnectTimeOutConfig(RestClientBuilder builder){
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(connect_time_out);
                requestConfigBuilder.setSocketTimeout(socket_time_out);
                requestConfigBuilder.setConnectionRequestTimeout(connection_request_time_out);
                return requestConfigBuilder;
            }
        });
    }

    // 主要关于异步httpclient的连接数配置
    private  void setMutiConnectConfig(RestClientBuilder builder){
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        if(!ObjectUtils.isEmpty(userName)){
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(userName, password));
        }
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.setMaxConnTotal(max_connect_num);
                httpClientBuilder.setMaxConnPerRoute(max_connect_per_route);
                if(!ObjectUtils.isEmpty(userName)){
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }
                return  httpClientBuilder;
            }
        });
    }

}
