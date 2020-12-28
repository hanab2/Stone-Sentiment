package com.stone.ltp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class ltp {
    private static final String WEBTTS_URL = "http://ltpapi.xfyun.cn/v1/cws";
    private static final String APPID = "5b67c3b7";
    private static final String API_KEY = "b8a2b55f7c2c713f101af7b7bf5a3e8b";
    private static final String TEXT = "打开页面物联网";
    private static final String TYPE = "dependent";

    public ltp() {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("打开页面物联网".length());
        Map<String, String> header = buildHttpHeader();
        String result = HttpUtil.doPost1("http://ltpapi.xfyun.cn/v1/cws", header, "text=" + URLEncoder.encode("打开页面物联网", "utf-8"));
        System.out.println("itp 接口调用结果：" + result);
    }

    private static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
        String curTime = String.valueOf(System.currentTimeMillis() / 1000L);
        String param = "{\"type\":\"dependent\"}";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
        String checkSum = DigestUtils.md5Hex("b8a2b55f7c2c713f101af7b7bf5a3e8b" + curTime + paramBase64);
        Map<String, String> header = new HashMap();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-CheckSum", checkSum);
        header.put("X-Appid", "5b67c3b7");
        return header;
    }
}