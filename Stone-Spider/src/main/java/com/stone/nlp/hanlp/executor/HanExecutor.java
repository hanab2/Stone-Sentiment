package com.stone.nlp.hanlp.executor;

import cn.hutool.core.util.StrUtil;
import com.stone.nlp.hanlp.annotation.HanApi;
import com.stone.nlp.hanlp.annotation.HanApiParm;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface HanExecutor {

    String execute(Map<String, Object> params);

    default String sendAndGetResultJsonString(Map<String, Object> params){
        Class<? extends HanExecutor> clazz = this.getClass();
        String url = clazz.getAnnotation(HanApi.class).url();
        String token = clazz.getAnnotation(HanApi.class).token();
        if(StrUtil.isBlank(url) || StrUtil.isBlank(token)){
            throw new RuntimeException("url或token为空");
        }
        return doHanlpApi(token,url,params);
    }

    default String doHanlpApi(String token, String url, Map<String, Object> params) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //添加header请求头，token请放在header里
            httpPost.setHeader("token", token);
            // 创建参数列表
            List<NameValuePair> paramList = new ArrayList<>();
            if (params != null) {
                for (String key : params.keySet()) {
                    //所有参数依次放在paramList中
                    paramList.add(new BasicNameValuePair(key, (String) params.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            return resultString;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
