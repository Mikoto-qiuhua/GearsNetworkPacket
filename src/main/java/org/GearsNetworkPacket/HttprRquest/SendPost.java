package org.GearsNetworkPacket.HttprRquest;


import cn.gionrose.facered.Main;
import cn.gionrose.http.CharacterInfoKt;
import cn.gionrose.util.UtilKt;
import com.google.gson.Gson;

import java.util.*;

import static org.GearsNetworkPacket.HttprRquest.SendPost.Status.*;

public class SendPost {


    public static int revision = -1;




    public static void getLoginId(String account, String password){
        String url = "https://api.soulknight-prequel.chillyroom.com/UserAuth/Login";

        String body = "{\"loginType\":\"phone\",\"account\":\"" + account + "\",\"password\":\"" + password + "\"}";

        String results = BasePost.sendHttpPost(url,body);
        new Gson ().fromJson(results, Map.class).forEach((key, value) -> {
            if ("session".equals(key))
            {
                ((Map)value).forEach((key1, value1) -> {
                    if ("token".equals(key1))
                        CharacterInfoKt.setAuthorization(value1.toString());
                });
            }
        });
    }


    public static Map<String, String> getRoleId(){
        String url = "https://api.soulknight-prequel.chillyroom.com/Character/FetchGameData";

        String body = "{\"dateTimeUtcOffsetOfMinutes\":480,\"firstLaunch\":false}";

        Map<String, String> idAndName = new HashMap<>();


        String results = BasePost.sendHttpPost(url,body);

        new Gson ().fromJson(results, Map.class).forEach((key, value) -> {
//            System.out.println(key + ":" + value);
            if ("characters".equals(key))
            {
                ((List)value).forEach(user -> {
//                    System.out.println(user);
                    ((Map)user).forEach((key1, value1) -> {
//                        System.out.println(key1 + ":" + value1);
                        if ("basic".equals(key1))
                        {
                            String id = ((Map<String, String>)value1).get ("id");
                            String name = ((Map<String, String>)value1).get ("name");
                            idAndName.put(id, name);
                        }
                    });
                });
            }
        });

        return idAndName;

    }





    public static Object CostGold(Boolean is100W){
        String url = "https://api.soulknight-prequel.chillyroom.com/Package/CostGold";
        String body = null;
        if(is100W){
            body = "{\"golds\":[20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000,20000],\"cost\":25}";
        }else {
            body = "{\"golds\":[999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999],\"cost\":25}";
        }

        String results = BasePost.sendHttpPost(url,body);

        Object returnValue = new Gson().fromJson(results, Map.class).getOrDefault("gold", null);
        if (returnValue != null)
        {
            return returnValue;
        }
        return null;

    }





    public static Integer getRevision(){
        String url = "https://api.soulknight-prequel.chillyroom.com/Character/Fetch";

        String body = "";

        String results = BasePost.sendHttpPost(url,body);
        return Integer.parseInt(new Gson().fromJson(results, Map.class).get("revision").toString());

    }


    public static Map SceneEnter(Integer sceneAreaType)  {
        Gson gson = new Gson();
        String url = "https://api.soulknight-prequel.chillyroom.com/Scene/SceneEnter";

        String body = "{\"sceneAreaType\":"+ sceneAreaType + ",\"stageType\":4,\"layer\":5,\"precessDropMarks\":{}}";

        String results = BasePost.sendHttpPost(url, body);

        return gson.fromJson(results, Map.class);
    }

    public static boolean selectType (int count,String type, String cardId, String id, String jewelType)
    {
        Gson gson = new Gson();
        int whileCount = 0;

        do {
            String body = "";
            int sceneAreaType = (Integer) Main.INSTANCE.getMap().keySet().stream().toArray()[new Random().nextInt(17)];

            HashMap temp = new HashMap();

            int currentCount = 0;

            while (true)
            {

                Map mapInfo = SceneEnter (sceneAreaType);

                Map bodyMap = new HashMap ();
                try
                {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                List items = (List)mapInfo.get(type);

                if ("cardPool".equals(type))
                {
                    body = spawnCard(items,sceneAreaType, cardId, SendPost.revision);//使用SendPost.revision目前就是随便写的测试
                    bodyMap = gson.fromJson(body, Map.class);
                }

                if ("genericItems".equals(type))
                {
                    body = spawnStone(items, sceneAreaType,id, jewelType, SendPost.revision);
                    bodyMap = gson.fromJson(body, Map.class);
                }
                if (currentCount == 0)
                {
                    temp.putAll(bodyMap);
                    currentCount = ((List) temp.get("guidsv4")).size();
                    continue;
                }

                ((List)temp.get("guidsv4")).addAll (((List)bodyMap.get("guidsv4")));
                ((List)((Map)temp.get("settlementv2")).get("items")).addAll (((List)((Map)bodyMap.get("settlementv2")).get ("items")));

                int totalCount = ((List) temp.get("guidsv4")).size();
                currentCount = totalCount;
                if (currentCount >= count)
                {

                    if (currentCount != count)
                    {
                        ((List)temp.get("guidsv4")).subList(0, currentCount - count).clear();
                        ((List)((Map)temp.get("settlementv2")).get("items")).subList(0, currentCount - count).clear();
                    }

//                    temp.forEach((k, v) -> {
//                        System.out.println(k + ":" + v);
//                    });

                    body = gson.toJson(temp);
                    break;
                }
            }
//            System.out.println(body);
            Map resultMap = gson.fromJson(getItem_R(body), Map.class);

            if (status (resultMap) == FAILED)
                return false;
            else if (status (resultMap) == SUCCESS)
                return true;
            else if (status(resultMap) == NO_SYNC && ++whileCount > 2)
                return false;


        } while(true);

    }


    enum Status {
        NO_SYNC, FAILED, SUCCESS
    }
    public static Status status (Map resultMap)
    {
        Object result = resultMap.get("revision");
        if (result == null)
        {
            if ("角色物品数据不同步".equals(resultMap.get ("msg")))
            {
                SendPost.revision = getRevision();
                return NO_SYNC;
            }
            return FAILED;
        }else {
            SendPost.revision = Integer.parseInt(result.toString());
            return SUCCESS;
        }
    }

    /**
     * 返回获取卡片的 Json 结果
     * @param guids uuid列表
     * @param cardId 卡片名
     * @param revision 版本
     * @return 卡片的 Json 结果
     */
    public static String spawnCard (List<String> guids, Integer sceneAreaType, String cardId, Integer revision)
    {
        String card = "{\"guidsv4\":[],\"settlementv2\":{\"sceneAreaType\":"+ sceneAreaType +",\"stageType\":4,\"items\":[]},\"revision\":\"\",\"index\"=1231231}";
        String item = "{\"id\":\"CARD\",\"itemId\":\"\",\"owner\":\"00000000-0000-0000-0000-000000000000\",\"type\":128,\"rate\":0,\"pay_method\":3,\"price\":0,\"season\":0,\"dict_nested\":{\"card_id\":\"\"}}";
        Map cardObject = new Gson().fromJson(card, Map.class);
        Map itemObject = new Gson().fromJson(item, Map.class);
        ArrayList<Map<String, Object>> items = new ArrayList<>();

        guids.forEach(v -> {
            HashMap<String, Object> temp = new HashMap<>();
            temp.putAll(itemObject);
            temp.put("itemId", v);
            ((Map)temp.get("dict_nested")).put("card_id", cardId);
            items.add(temp);
        });

        cardObject.put ("guidsv4", guids);
        ((Map)cardObject.get("settlementv2")).put("items", items);
        cardObject.put("revision", revision);

        return new Gson().toJson (cardObject);

    }

    /**
     * 返回获取石头的 Json 结果
     * @param guids uuid列表
     * @param id 石头 id
     * @param jewelType 石头类型
     * @param revision 版本
     * @return 石头的 Json 结果
     */
    public static String spawnStone (List<String> guids, Integer sceneAreaType,String id, String jewelType, Integer revision)
    {
        String stone = "{\"guidsv4\":[],\"settlementv2\":{\"sceneAreaType\":"+ sceneAreaType +",\"stageType\":4,\"items\":[]},\"revision\":\"\",\"index\"=1231231}";
        String item = "{\"id\":\"CARD\",\"itemId\":\"\",\"owner\":\"00000000-0000-0000-0000-000000000000\",\"type\":512,\"rate\":3,\"pay_method\":3,\"price\":500,\"season\":0,\"dict_nested\":{\"_jewelType\":\"\"}}";
        Map stoneObject = new Gson().fromJson(stone, Map.class);
        Map itemObject = new Gson().fromJson(item, Map.class);
        ArrayList<Map<String, Object>> items = new ArrayList<>();

        guids.forEach(v -> {
            HashMap<String, Object> temp = new HashMap<>();
            temp.putAll(itemObject);
            temp.put ("id", id);
            temp.put("itemId", v);
            ((Map)temp.get("dict_nested")).put("_jewelType", jewelType);
            items.add(temp);
        });

        stoneObject.put ("guidsv4", guids);
        ((Map)stoneObject.get("settlementv2")).put("items", items);
        stoneObject.put("revision", revision);

        return new Gson().toJson (stoneObject);
    }


    public static String getItem_R(String body){
        String url = "https://api.soulknight-prequel.chillyroom.com/Package/AddV886";

        return BasePost.sendHttpPost(url,body);

    }



    public static String getEquipment_R(List<String> itemList){
        String url = "https://api.soulknight-prequel.chillyroom.com/Package/AddV886";

        int index = UtilKt.randomIndex();

        String body = "{\"guidsv4\":[" + itemList + "],\"revision\":" + revision + ",\"index\"=" + index + "}";

        return BasePost.sendHttpPost(url,body);


    }

    public static String ImportStones(List<String> itemList){
        String url = "https://api.soulknight-prequel.chillyroom.com/Blacksmith/ImportStones";

        String body = "{\"stones\":[" + itemList + "]}";

        return BasePost.sendHttpPost(url,body);
    }


    public static String PutInCardBook_R(List<String> itemList){
        String url = "https://api.soulknight-prequel.chillyroom.com/Card/PutInCardBook";

        int index = UtilKt.randomIndex();

        String body = "{\"cards\":[" + itemList + "],\"gameRevision\":" + revision + ",\"index\"=" + index + "}";

        return BasePost.sendHttpPost(url,body);
    }


}
