package cn.gionrose.http

import cn.gionrose.facered.Main
import cn.gionrose.util.parseMapFromJson
import cn.gionrose.util.randomIndex
import cn.gionrose.util.toJson
import org.GearsNetworkPacket.HttprRquest.BasePost
import org.GearsNetworkPacket.HttprRquest.SendPost
import java.util.*


object FeatureRequest {

    fun selectTypeGetItem (count: Int, type: String, cardId: String?, id: String?, jewelType: String?): Boolean
    {
        var whileCount = 0

        do {
            var body = ""
            val sceneAreaType = Main.getMap().keys.toMutableList()[Random().nextInt (17)]

            val temp = mutableMapOf<String, Any?>()
            var currentCount = 0


            while (true)
            {
                val mapInfo = postSceneEnter(sceneAreaType)
                var bodyMap = mutableMapOf<String, Any?>()

                Thread.sleep(1000)

                val items = mapInfo[type] as List<String>

                if ("cardPool" == type)
                {
                    body = spawnCard(items, sceneAreaType, cardId!!, revision)
                    bodyMap = body.parseMapFromJson() as MutableMap<String, Any?>
                }else if ("genericItems" == type)
                {
                    body = spawnStone(items, sceneAreaType, id!!, jewelType!!, revision)
                    bodyMap = body.parseMapFromJson() as MutableMap<String, Any?>
                }

                if (currentCount == 0)
                {
                    temp.putAll(bodyMap)
                    currentCount = (temp["guidsv4"] as List<String>).size
                    continue
                }

                (temp["guidsv4"] as MutableList<String>).addAll((bodyMap["guidsv4"] as List<String>))
                ((temp["settlement"] as MutableMap<String, Any?>)["items"] as MutableList<Any?>).addAll((bodyMap["settlement"] as Map<String, Any?>)["items"] as List<Any?>)

                val totalCount = (temp["guidsv4"] as List<String>).size
                currentCount = totalCount

                if (currentCount >= count)
                {
                    if (currentCount != count)
                    {
                        (temp["guidsv4"] as MutableList<String>).subList(0, currentCount - count).clear()
                        ((temp["settlement"] as MutableMap<String, Any?>)["items"] as MutableList<Any?>).subList(0, currentCount - count).clear()
                    }

                    temp.forEach{ (k, v) ->
                        println("$k:$v")
                    }

                    body = temp.toJson()
                    break
                }
            }

            val result = postItemR(body).parseMapFromJson()

            if (postStatus(result) == RequestStatus.FAILED)
                return false
            else if (postStatus(result) == RequestStatus.SUCCESS)
            {
                if (type == "cardPool")
                    postInputCardBook(temp["guids"] as MutableList<String>)
                else if (type == "genericItems")
                    postImportStones(temp["guids"] as MutableList<String>)
                return true
            }
            else if (postStatus(result) == RequestStatus.NO_SYNC && ++whileCount > 2)
                return false

        }while (true)
    }

    private fun postItemR (body: String): String
    {
        val url = "https://api.soulknight-prequel.chillyroom.com/Package/AddV886"

        return BasePost.sendHttpPost(url, body)
    }

    private fun spawnCard (guids: List<String>, sceneAreaType: Int, cardId: String, revision: Int): String
    {
        val card = "{\"guidsv4\":[],\"settlement\":{\"sceneAreaType\":$sceneAreaType,\"stageType\":4,\"items\":[]},\"revision\":\"\",\"index\"=${randomIndex()}}"
        val item = "{\"id\":\"CARD\",\"itemId\":\"\",\"owner\":\"00000000-0000-0000-0000-000000000000\",\"type\":128,\"rate\":0,\"pay_method\":3,\"price\":0,\"season\":0,\"dict_nested\":{\"card_id\":\"\"}}"

        val itemMap = item.parseMapFromJson() as MutableMap<String, Any?>
        val cardMap = card.parseMapFromJson() as MutableMap<String, Any?>
        val items = mutableListOf<MutableMap<String, Any?>>()

        guids.forEach {
            val temp = mutableMapOf<String, Any?>()
            temp.putAll(itemMap)
            temp["itemId"] = it
            (temp["dict_nested"] as MutableMap<String, Any?>)["card_id"] = cardId
            items.add(temp)
        }

        cardMap["guidsv4"] = guids
        (cardMap["settlement"] as MutableMap<String, Any?>)["items"] = items
        cardMap["revision"] = revision

        return cardMap.toJson()
    }

    private fun spawnStone (guids: List<String>, sceneAreaType: Int, id: String, jewelType: String, revision: Int): String
    {
        val stone = "{\"guidsv4\":[],\"settlement\":{\"sceneAreaType\":$sceneAreaType,\"stageType\":4,\"items\":[]},\"revision\":\"\",\"index\"=${randomIndex()}}"
        val item = "{\"id\":\"CARD\",\"itemId\":\"\",\"owner\":\"00000000-0000-0000-0000-000000000000\",\"type\":512,\"rate\":3,\"pay_method\":3,\"price\":500,\"season\":0,\"dict_nested\":{\"_jewelType\":\"\"}}"

        val itemMap = item.parseMapFromJson() as MutableMap<String, Any?>
        val stoneMap = stone.parseMapFromJson() as MutableMap<String, Any?>
        val items = mutableListOf<MutableMap<String, Any?>>()

        guids.forEach {
            var temp = mutableMapOf<String, Any?>()
            temp.putAll(itemMap)
            temp["id"] = id
            temp["itemId"] = it
            (temp["dict_nested"] as MutableMap<String, Any?>)["_jewelType"] = jewelType
            items.add(temp)
        }
        stoneMap["guidsv4"] = guids
        (stoneMap["settlement"] as MutableMap<String, Any?>)["items"] = items
        stoneMap["revision"] = revision

        return stoneMap.toJson()
    }

    fun postCostGold10W (): Boolean
    {
        val url = "https://api.soulknight-prequel.chillyroom.com/Package/CostGold"
        val body = "{\"golds\":[5],\"cost\":25}"
        val result = BasePost.sendHttpPost(url,body).parseMapFromJson()["gold"]

        result?.let { return true }
        return false
    }
    fun postCostGold100W (): Boolean
    {
        val url = "https://api.soulknight-prequel.chillyroom.com/Package/CostGold"
        val body = "{\"golds\":[5],\"cost\":25}"
        val result = BasePost.sendHttpPost(url,body).parseMapFromJson()["gold"]

        result?.let { return true }
        return false
    }
    private fun postRevision (): Int
    {
        val url = "https://api.soulknight-prequel.chillyroom.com/Character/Fetch"
        val body = ""
        return BasePost.sendHttpPost(url,body).parseMapFromJson()["revision"]!!.toString().toInt()
    }

    private fun postSceneEnter (sceneAreaType: Int): Map<String, Any?>
    {
        val url = "https://api.soulknight-prequel.chillyroom.com/Scene/SceneEnter"
        val body = "{\"sceneAreaType\":$sceneAreaType,\"stageType\":4,\"layer\":5,\"precessDropMarks\":{}}"
        val result = BasePost.sendHttpPost(url, body)

        return BasePost.sendHttpPost(url, body).parseMapFromJson()
    }
    enum class RequestStatus
    {
        NO_SYNC, FAILED, SUCCESS
    }

    private fun postStatus (resultMap: Map<String, Any?>): RequestStatus
    {
        val result = resultMap["revision"]
        if (result == null)
            if ("角色物品数据不同步" == resultMap["msg"])
            {
                revision = postRevision()
                return RequestStatus.NO_SYNC
            }
            else
                return RequestStatus.FAILED
        else
        {
            revision = result.toString().toInt()
            return RequestStatus.SUCCESS
        }

    }

    fun postImportStones(itemList: List<String?>): String {
        val url = "https://api.soulknight-prequel.chillyroom.com/Blacksmith/ImportStones"
        val body = "{\"stones\":[$itemList]}"
        return BasePost.sendHttpPost(url, body)
    }

    fun postInputCardBook(itemList: List<String?>): String {
        val url = "https://api.soulknight-prequel.chillyroom.com/Card/PutInCardBook"
        val body = "{\"cards\":[" + itemList + "],\"gameRevision\":" + SendPost.revision + ",\"index\"=${randomIndex()}}"
        return BasePost.sendHttpPost(url, body)
    }

}