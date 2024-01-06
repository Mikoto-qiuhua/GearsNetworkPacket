package org.GearsNetworkPacket.HttprRquest;

import cn.gionrose.http.CharacterInfoKt;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;

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

}
