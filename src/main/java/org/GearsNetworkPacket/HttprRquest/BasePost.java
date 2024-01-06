package org.GearsNetworkPacket.HttprRquest;

import cn.gionrose.http.CharacterInfoKt;
import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasePost {
    private static String distro = null;


    public static void setDistro(String serverId) {
        switch (serverId) {
            case "taptap":
                distro = "2e9a2011-2a42-46e1-b648-42a8c598b43b";
                break;
            case "好游快爆":
                distro = "3e414bc2-063d-4a62-a347-9b9848eb9ecf";
                break;
            case "ios":
                distro = "a24d3e9f-4fdc-4b6e-935a-74aebb715173";
                break;
            case "4399":
                distro = "39e85428-7278-4836-b839-8355bb8a5767";
                break;

        }
    }


    public static String sendHttpPost(String url, String body) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("x-distro-id", distro);
            httpPost.setHeader("x-unique-id", "b0cf8709c2fe262c");
            httpPost.setHeader("x-game-version", "1.0.3");
            httpPost.setHeader("Cookie", "aliyungf_tc=cda0eae1ad50e83b749872ae0bd2d54ef73baa05df0a8ed5b72d7a57f9869364");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate");
            httpPost.setHeader("x-locale", "zh-CN");
            httpPost.setHeader("x-game-lang", "Chinese");
            httpPost.setHeader("x-game-id", "5");
            httpPost.setHeader("Accept-Language", "zh-Hans-CN,  zh-CN");
            httpPost.setHeader("x-delivery-platform", "origin_taptap");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Host", "api.soulknight-prequel.chillyroom.com");
            if(!CharacterInfoKt.getAuthorization().isEmpty()){
                httpPost.setHeader("Authorization", "Bearer " + CharacterInfoKt.getAuthorization());
            }
            if(!CharacterInfoKt.getCharacter().isEmpty()){
                httpPost.setHeader("x-character-id", CharacterInfoKt.getCharacter());
            }


            StringEntity requestEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);


            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    // 读取响应数据
                    return EntityUtils.toString(responseEntity);
                }
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取4399用户信息  拿到用户信息后 就替换获取游戏账号包的请求体内容
    //state会更新  更新后会出现登入过期 且返回的登入地址内有新的state可以拿来用
    public static void login4399(String u, String p){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            String url = "https://ptlogin.4399.com/oauth2/loginAndAuthorize.do";
            String state = "16ecae430200c69735f4e0e27bcd0765";
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", u));
            params.add(new BasicNameValuePair("password", p));
            params.add(new BasicNameValuePair("response_type", "TOKEN"));
            params.add(new BasicNameValuePair("client_id", "e4917859432826d117aae3734532bb89"));
            params.add(new BasicNameValuePair("state", state));
            params.add(new BasicNameValuePair("redirect_uri", "https://m.4399api.com/openapi/oauth-callback.html?gamekey=49321&game_key=138471"));
            // 将参数列表编码为表单数据
            HttpEntity entity = new UrlEncodedFormEntity(params);
            // 设置请求的实体
            httpPost.setEntity(entity);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                String responseBody = EntityUtils.toString(responseEntity);
                new Gson().fromJson(responseBody, Map.class).forEach((key, value) -> {
                    System.out.println (key + ":" + value);
                });
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }


    }






}
