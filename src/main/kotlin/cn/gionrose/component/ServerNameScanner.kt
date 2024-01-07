package cn.gionrose.component

import cn.gionrose.http.LoginRequest
import cn.gionrose.util.time
import org.GearsNetworkPacket.HttprRquest.BasePost

object ServerNameScanner : AbstractScanner(){

    override val level = 0

    override val next = UserNameScanner

    override fun choose() {

        ServerType.idOf(answer)?.let {
            if (!time())
            {
                when (answer)
                {
                    "2", "3", "4"-> {
                        println()
                        println("试用版不含此功能")
                        println()
                        loop()
                    }
                }

                chooseResult = it.serverName
                isEnd = true
            }else
            {
                when (answer)
                {
                    "1"-> {
                        loop()
                    }
                    "2", "3", "4"-> {
                        println()
                        println("试用版不含此功能")
                        println()
                    }

                }
            }
        }

    }

    override fun question() {
        if (!time())
            println ("1.taptap")

        println("2.好游快爆")
        println("3.ios")
        println("4.4399")


        print ("请输入服务器编号: ")
    }

    override fun execute() {
        LoginRequest.distro = chooseResult
        BasePost.setDistro(LoginRequest.distro)
    }

    override fun isOk(input: String): Boolean {
        return true
    }






    enum class ServerType (val id: String, val serverName: String){
        TAP_TAP("1","taptap"),
        H_Y_K_B("2","好游快爆"),
        IOS("3","ios"),
        FOUR_THREE_NINE_NINE("4","4399");

        companion object {
            @JvmStatic
            fun idOf (id: String): ServerType?
            {
                entries.forEach {
                    if (id == it.id)
                        return it
                }
                return null
            }
        }
    }

}