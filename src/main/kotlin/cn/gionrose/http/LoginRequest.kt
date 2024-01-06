package cn.gionrose.http

import cn.gionrose.util.parseMapFromJson
import org.GearsNetworkPacket.HttprRquest.BasePost

object LoginRequest
{
    lateinit var username: String

    lateinit var password: String

    lateinit var distro: String


    fun postLoginId ()
    {
        val url = "https://api.soulknight-prequel.chillyroom.com/UserAuth/Login" //登入接口
        val body = "{\"loginType\":\"phone\",\"account\":\"$username\",\"password\":\"$password\"}"

        BasePost.sendHttpPost(url, body).apply {
            parseMapFromJson().forEach { (k, v) ->
                if ("session" == k)
                    (v as Map<*, *>).forEach { (k1, v1) ->
                        if ("token" == k1)
                            authorization = v1.toString()
                    }
            }
        }
    }

    fun postRoleId (): Map<String, String>
    {
        val url = "https://api.soulknight-prequel.chillyroom.com/Character/FetchGameData"
        val body = "{\"dateTimeUtcOffsetOfMinutes\":480,\"firstLaunch\":false}"

        val idAndName = mutableMapOf<String, String>()

        BasePost.sendHttpPost(url, body).apply {
            parseMapFromJson().forEach { (k, v) ->
                if ("characters" == k)
                {
                    (v as List<*>).forEach {
                        (it as Map<*, *>).forEach { (k1, v1) ->
                            if ("basic" == k1)
                                idAndName[(v1 as Map<*, *>)["id"].toString()] = v1["name"].toString()
                        }
                    }
                }
            }
        }

        return idAndName
    }

}