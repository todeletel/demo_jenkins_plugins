package io.jenkins.plugins.sample.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class Auth {

    public String base="http://192.168.120.62:81";

    public boolean auth(String user, String password) throws Exception{
        String url = base + "/auth/rest_login";
        // 1.建立HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();

        //创建一个HttpContext对象，用来保存Cookie
        HttpClientContext httpClientContext = HttpClientContext.create();

        // 2.建立Get请求
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        //设置参数到请求对象中

        nameValuePairs.add(new BasicNameValuePair("name", user));
        nameValuePairs.add(new BasicNameValuePair("password", password));

        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nameValuePairs.toString());

        //设置header信息
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost, httpClientContext);
        HttpEntity e = response.getEntity();
        String body = EntityUtils.toString(e,"utf8");
        // 使用new方法
        Gson gson = new Gson();
        Return u = gson.fromJson(body, Return.class);
        return u.ok;
    }

}

class Return{
    public boolean ok;
}