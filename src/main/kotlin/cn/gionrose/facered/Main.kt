package cn.gionrose.facered

import cn.gionrose.http.authorization
import cn.gionrose.http.character
import com.google.gson.Gson
import org.GearsNetworkPacket.HttprRquest.BasePost
import org.GearsNetworkPacket.HttprRquest.SendPost
import java.util.*

/**
 * @description TODO
 * @author facered
 * @date 2024/1/1 10:14
 */
object Main {

    @JvmStatic
    fun main(arg: Array<String>) {

        var serverName = ""
        var password = ""
        var userName = ""
        val scanner = Scanner(System.`in`)
        var serverBool = true

        while (true)
        {
            while (serverBool)
            {
                println("请输入服务器编号:")
                println("1. taptap")
                println("2. 好游快爆")
                println("3. ios")
                println("4. 4399")
                val serverId = scanner.nextLine()
                when (serverId)
                {
                    "1" -> {
                       serverName = "taptap"
                        println ("你选择了服务器 [ $serverName ]")
                        break
                    }
                    "2" -> {
                        serverName = "好游快爆"
                        println ("你选择了服务器 [ $serverName ]")
                        break
                    }
                    "3" -> {
                        serverName = "ios"
                        println ("你选择了服务器 [ $serverName ]")
                        break
                    }
                }
            }

            println()
            BasePost.setDistro(serverName)

            while (true)
            {
                println("请输入账号:")
                userName = scanner.nextLine()

                println ("你确定账号 [ $userName ] (y/n):")
                val isOk = scanner.nextLine()
                if (isOk == "y")
                    break
            }

            println()

            while (true)
            {
                println("请输入密码:")
                password = scanner.nextLine()

                println ("你确定密码 [ $password ] (y/n):")
                val isOk = scanner.nextLine()
                if (isOk == "y")
                    break
            }

            SendPost.getLoginId(userName, password)

            if (authorization == "")
            {
                println ("登录失败")
                print ("请重新输入")
                serverBool = false
                continue
            }

            println()
            println ("登录成功")

            val roleId = SendPost.getRoleId()

            while (true)
            {
                println("请输入角色数字编号:")
                var i = 0
                roleId.forEach { (_, name) ->
                    println("${++i}.$name")
                }
                val id = scanner.nextLine().toInt()
                val uuid = roleId.keys.toMutableList()[id-1]
                character = uuid
                val name = roleId.values.toMutableList()[id-1]
                println("你选择了角色 [ $name ]")
                println("是否确认 (y/n):")
                val isOk = scanner.nextLine()
                if (isOk == "y")
                    break
            }

            println()
//            var mapId = 0
//            while (true)
//            {
//                println ("请输入地图序号 例如 1:")
//                println("1. 南部草原")
//                println("2. 芦苇湿地")
//                println("3. 迷雾森林")
//                println("4. 潮湿溶洞")
//                println("5. 水晶洞窟")
//                println("6. 寂静山谷")
//                println("7. 干燥荒漠")
//                println("8. 废弃城堡")
//                println("9. 巨石山地")
//                println("10. 宁静森林")
//                println("11. 热浪海岛")
//                println("12. 炽热火山")
//                println("13. 严寒雪山")
//                println("14. 古代遗迹")
//                println("15. 机械大陆")
//                println("16. 最终之塔")
//                println("17. 魔法石神殿")
//
//                val mapNum = scanner.nextLine()
//                when (mapNum)
//                {
//                    "1" -> {
//                        mapId = 9
//                        break
//                    }
//                    "2" -> {
//                        mapId = 10
//                        break
//                    }
//                    "3" -> {
//                        mapId = 11
//                        break
//                    }
//                    "4" -> {
//                        mapId = 12
//                        break
//                    }
//                    "5" -> {
//                        mapId = 13
//                        break
//                    }
//                    "6" -> {
//                        mapId = 14
//                        break
//                    }
//                    "7" -> {
//                        mapId = 15
//                        break
//                    }
//                    "8" -> {
//                        mapId = 16
//                        break
//                    }
//                    "9" -> {
//                        mapId = 17
//                        break
//                    }
//                    "10" -> {
//                        mapId = 18
//                        break
//                    }
//                    "11" -> {
//                        mapId = 19
//                        break
//                    }
//                    "12" -> {
//                        mapId = 20
//                        break
//                    }
//                    "13" -> {
//                        mapId = 21
//                        break
//                    }
//                    "14" -> {
//                        mapId = 22
//                        break
//                    }
//                    "15" -> {
//                        mapId = 23
//                        break
//                    }
//                    "16" -> {
//                        mapId = 24
//                        break
//                    }
//                    "17" -> {
//                        mapId = 8
//                        break
//                    }
//                }
//            }

            while (true)
            {

                println ("请选择功能的编号:")
                println("1. 增加金币(10w 通用)")
                println("2. 增加金币(100w 限满级角色)")
                println("3. 强化石")
                println("4. 卡片")

                val id = scanner.nextLine()

                when (id)
                {
                    "1" -> {
                        while (true)
                        {
                            println ("请输入循环次数:")
                            val count = scanner.nextLine().toInt()
                            println ("你确定要增加 $count 次 (y/n):")
                            val isOk = scanner.nextLine()
                            if (isOk == "y")
                            {

                                for (i in 1..count)
                                {
                                    Thread.sleep(300)
                                    val result = SendPost.CostGold(false)
                                    if (result != null)
                                        println ("成功 $i 次 当前金币 $result")
                                    else break
                                }
                                break
                            }
                        }
                    }

                    "2" -> {
                        while (true)
                        {
                            println ("请输入循环次数:")
                            val count = scanner.nextLine().toInt()
                            println ("你确定要增加 $count 次 (y/n):")
                            val isOk = scanner.nextLine()
                            if (isOk == "y")
                            {

                                for (i in 1..count)
                                {
                                    Thread.sleep(300)
                                    val result = SendPost.CostGold(true)
                                    if (result != null)
                                        println ("成功 $i 次 当前金币 $result")
                                    else break
                                }
                                break
                            }
                        }
                    }

                    "3" -> {
                        var stoneId = ""
                        var stoneType = ""
                        var itemNum: Int
                        var stoneDispay = ""
                        while (true)
                        {
                            println("请选择石头类型:")
                            println ("1.火山石 ")
                            println ("2.磨炼石 ")
                            println ("3.风化石 ")
                            val stoneNum = scanner.nextLine()
                            when (stoneNum)
                            {
                                "1" -> {
                                    stoneId = "JEWEL_001"
                                    stoneType = "FireRemakeStone"
                                    stoneDispay = "风化石"
                                }
                                "2" -> {
                                    stoneId = "JEWEL_003"
                                    stoneType = "UpgradeStone"
                                    stoneDispay = "磨炼石"
                                }
                                "3" -> {
                                    stoneId = "JEWEL_001"
                                    stoneType = "WindRemakeStone"
                                    stoneDispay = "火山石"
                                }
                            }

                            println ("请输入需要物品数量:")
                            itemNum = scanner.nextLine().toInt()

                            println ("你确定要刷 $stoneDispay $itemNum 次 ? (y/n):")
                            val isOk = scanner.nextLine()
                            if (isOk == "y")
                            {
                                println ("请稍等....")
                                break
                            }

                        }
                        val result = SendPost.selectType(itemNum,"genericItems", null, stoneId, stoneType)
                        if (result)
                            println ("成功 $itemNum 次")
                        else println("失败... (请查看接口是否可用)")
                    }

                    "4" -> {
                        var itemNum: Int
                        while (true)
                        {
                            println("请选择卡片类型:")
                            println ("1.小怪 ")
                            println ("2.Boss ")
                            println ("3.Npc ")
                            val cardTypeNum = scanner.nextLine()
                            var masterTypeNum: Int
                            var masterTypeName: String
                            var masterMap = mapOf("" to "")
                            while (true)
                            {

                                when (cardTypeNum)
                                {
                                    "1" -> masterMap = getXiaoGuai()
                                    "2" -> masterMap = getBoss()
                                    "3" -> masterMap = getNpc()
                                }

                                masterMap.keys.forEachIndexed { index, k ->
                                    val afterIndex = index+1

                                    when (k.length)
                                    {
                                        3 -> print("$afterIndex.$k          ")
                                        4 -> print("$afterIndex.$k        ")
                                        5 -> print("$afterIndex.$k      ")
                                        6 -> print("$afterIndex.$k    ")
                                    }

                                    if ((afterIndex % 10) == 0)
                                        println()
                                }
                                println()
                                println ("请输入类型的编号:")
                                masterTypeNum = scanner.nextLine().toInt()
                                masterTypeName = masterMap.keys.toMutableList()[masterTypeNum-1]

                                println ("请输入需要物品数量:")
                                itemNum = scanner.nextLine().toInt()

                                println ("你确定要刷 [$masterTypeName] $itemNum 次 ? (y/n):")
                                val isOk = scanner.nextLine()
                                if (isOk == "y")
                                {
                                    println ("请稍等....")
                                    break
                                }
                            }

                            val masterCardId = masterMap[masterTypeName].toString()
                            println(masterCardId)
                            val result = SendPost.selectType(itemNum, "cardPool", masterCardId, "", "")
                            if (result)
                                println ("成功 $itemNum 次")
                            else println("失败... (请查看接口是否可用)")
                        }

                    }

                }
            }
        }



    }


    fun getMap (): Map<Int, String>
    {

        return mapOf(9 to "南部草原",
            10 to "芦苇湿地",
            11 to "迷雾森林",
            12 to "潮湿溶洞",
            13 to "水晶洞窟",
            14 to "寂静山谷",
            15 to "干燥荒漠",
            16 to "废弃城堡",
            17 to "巨石山地",
            18 to "宁静森林",
            19 to "热浪海岛",
            20 to "炽热火山",
            21 to "严寒雪山",
            22 to "古代遗迹",
            23 to "机械大陆",
            24 to "最终之塔",
            8 to "魔法石神殿")
    }

    fun getNpc (): Map<String, String>
    {
        val npc = "{\n" +
                "    \"旅行者\":\"E_N_Traveler\",\n" +
                "    \"巴克斯\":\"NPC_Alchemy\",\n" +
                "    \"马伦\":\"NPC_Assassin\",\n" +
                "    \"雷夫\":\"NPC_Berserker\",\n" +
                "    \"威拉德\":\"NPC_Druid\",\n" +
                "    \"碧洛迪丝\":\"NPC_Elf\",\n" +
                "    \"特里斯坦\":\"NPC_Engineer\",\n" +
                "    \"亚伦\":\"NPC_Knight\",\n" +
                "    \"霍华德\":\"NPC_Paladin\",\n" +
                "    \"格洛莉亚\":\"NPC_Priest\",\n" +
                "    \"兰迪\":\"NPC_Rogue\",\n" +
                "    \"德里克\":\"NPC_Werewolf\",\n" +
                "    \"梅\":\"NPC_Witch\",\n" +
                "    \"布莱姆\":\"NPC_Vampire\"\n" +
                "}"
        return (Gson ().fromJson(npc, Map::class.java) as Map<String, String>)

    }

    fun getBoss (): Map<String, String>
    {
        val boss = "{\n" +
                "    \"恶犬帮首领\":\"E01_B01\",\n" +
                "    \"野猪王\":\"E01_B02\",\n" +
                "    \"巨型沼泽螺\":\"E02_B01\",\n" +
                "    \"曼陀罗花1\":\"E03_B01\",\n" +
                "    \"精灵曼陀罗\":\"E03_B02\",\n" +
                "    \"曼陀罗花2\":\"E03_B03\",\n" +
                "    \"美杜莎\":\"E04_B01\",\n" +
                "    \"感电史莱姆王\":\"E04_B02\",\n" +
                "    \"水晶巨蟹\":\"E05_B01\",\n" +
                "    \"黄金巨蟹\":\"E05_B02\",\n" +
                "    \"枯骨王\":\"E06_B01\",\n" +
                "    \"巨大沙虫\":\"E07_B01\",\n" +
                "    \"阿努比斯\":\"E07_B02\",\n" +
                "    \"大骑士\":\"E08_B01\",\n" +
                "    \"大巫师\":\"E08_B02\",\n" +
                "    \"黑暗大骑士\":\"E08_B03\",\n" +
                "    \"符文巨石 火\":\"E09_B01\",\n" +
                "    \"符文巨石 冰\":\"E09_B02\",\n" +
                "    \"符文巨石 雷\":\"E09_B03\",\n" +
                "    \"符文巨石\":\"E09_B\",\n" +
                "    \"哥布林大祭司\":\"E10_B01\",\n" +
                "    \"海盗头目\":\"E11_B01\",\n" +
                "    \"灰烬火龙\":\"E12_B01\",\n" +
                "    \"雪山大猿王\":\"E13_B01\",\n" +
                "    \"巨像 祖兰\":\"E14_B01\",\n" +
                "    \"维C机甲\":\"E15_B01\",\n" +
                "    \"瓦克恩\":\"E16_B01\",\n" +
                "    \"虚空瓦克恩\":\"E16_B02\",\n" +
                "    \"雪人\":\"E13_B02\",\n" +
                "    \"雪人王\":\"E13_B03\"\n" +
                "}\n"
        return (Gson ().fromJson(boss, Map::class.java) as Map<String, String>)

    }
    fun getXiaoGuai (): Map<String, String>
    {
        val xiaoguai = "{\n" +
                "    \"宝藏喵\": \"E00_S01\",\n" +
                "    \"宝藏犬\": \"E00_S02\",\n" +
                "    \"小野猪\": \"E01_S01\",\n" +
                "    \"暴躁野猪\": \"E01_S02\",\n" +
                "    \"土狼战士\": \"E01_S03\",\n" +
                "    \"土狼斧兵\": \"E01_S04\",\n" +
                "    \"土狼双刀兵\": \"E01_S05\",\n" +
                "    \"土狼长刀兵\": \"E01_S06\",\n" +
                "    \"土狼哨兵\": \"E01_S07\",\n" +
                "    \"土狼弓兵\": \"E01_S08\",\n" +
                "    \"史莱姆\": \"E01_S09\",\n" +
                "    \"草原蝙蝠\": \"E01_S10\",\n" +
                "    \"潮汐田螺\": \"E02_S01\",\n" +
                "    \"硬化田螺\": \"E02_S02\",\n" +
                "    \"沼泽青蛙\": \"E02_S03\",\n" +
                "    \"沼泽毒蛙\": \"E02_S04\",\n" +
                "    \"沼泽蚊子\": \"E02_S05\",\n" +
                "    \"沼泽大蚊\": \"E02_S06\",\n" +
                "    \"沼泽鱼人\": \"E02_S07\",\n" +
                "    \"沼泽鱼人战士\": \"E02_S08\",\n" +
                "    \"森林灰狼\": \"E03_S01\",\n" +
                "    \"木头树妖\": \"E03_S02\",\n" +
                "    \"多刺松果\": \"E03_S03\",\n" +
                "    \"大黄蜂\": \"E03_S04\",\n" +
                "    \"大紫蜂\": \"E03_S05\",\n" +
                "    \"豌豆射手\": \"E03_S06\",\n" +
                "    \"奇异毒花\": \"E03_S07\",\n" +
                "    \"树妖首领\": \"E03_S08\",\n" +
                "    \"藤蔓触手\": \"E03_S09\",\n" +
                "    \"蛇人士兵\": \"E04_S01\",\n" +
                "    \"蛇人战士\": \"E04_S02\",\n" +
                "    \"蛇人弩手\": \"E04_S03\",\n" +
                "    \"蛇人法师\": \"E04_S04\",\n" +
                "    \"蛇人刺客\": \"E04_S05\",\n" +
                "    \"萤火巨蜂\": \"E04_S06\",\n" +
                "    \"巨型电史莱姆\": \"E04_S07\",\n" +
                "    \"感电史莱姆\": \"E04_S08\",\n" +
                "    \"哥布林矿工\": \"E05_S01\",\n" +
                "    \"哥布林投石者\": \"E05_S02\",\n" +
                "    \"哥布林爆破工\": \"E05_S03\",\n" +
                "    \"大型蓝晶虫\": \"E05_S04\",\n" +
                "    \"成年蓝晶虫\": \"E05_S05\",\n" +
                "    \"幼年蓝晶虫\": \"E05_S06\",\n" +
                "    \"矿洞史莱姆\": \"E05_S07\",\n" +
                "    \"矿洞蝙蝠\": \"E05_S08\",\n" +
                "    \"矿洞咬咬\": \"E05_S09\",\n" +
                "    \"矿洞石巨人\": \"E05_S10\",\n" +
                "    \"骨头小兵\": \"E06_S01\",\n" +
                "    \"骨头射手\": \"E06_S02\",\n" +
                "    \"骨头战士\": \"E06_S03\",\n" +
                "    \"骨头犬\": \"E06_S04\",\n" +
                "    \"游魂\": \"E06_S05\",\n" +
                "    \"高阶游魂\": \"E06_S06\",\n" +
                "    \"幽灵\": \"E06_S07\",\n" +
                "    \"骨头巫师\": \"E06_S08\",\n" +
                "    \"荒漠刺客\": \"E07_S01\",\n" +
                "    \"荒漠射手\": \"E07_S02\",\n" +
                "    \"荒漠法师\": \"E07_S03\",\n" +
                "    \"木乃伊\": \"E07_S04\",\n" +
                "    \"木乃伊法师\": \"E07_S05\",\n" +
                "    \"呆头秃鹫\": \"E07_S06\",\n" +
                "    \"荒漠蝎子\": \"E07_S07\",\n" +
                "    \"荒漠豺狼\": \"E07_S08\",\n" +
                "    \"沙漠石巨人\": \"E07_S09\",\n" +
                "    \"轮回砂碑\": \"E07_S10\",\n" +
                "    \"城堡骑士\": \"E08_S01\",\n" +
                "    \"城堡守卫\": \"E08_S02\",\n" +
                "    \"城堡弩手\": \"E08_S03\",\n" +
                "    \"城堡小兵\": \"E08_S04\",\n" +
                "    \"蓝色小法师\": \"E08_S05\",\n" +
                "    \"红色小法师\": \"E08_S06\",\n" +
                "    \"秘术法师\": \"E08_S07\",\n" +
                "    \"壮硕骑士\": \"E08_S08\",\n" +
                "    \"壮硕炮手\": \"E08_S09\",\n" +
                "    \"机关弩\": \"E08_S10\",\n" +
                "    \"石化飞龙1\": \"E08_S11\",\n" +
                "    \"石化飞龙2\": \"E08_S12\",\n" +
                "    \"山地大鸟\": \"E09_S01\",\n" +
                "    \"山地石巨人\": \"E09_S02\",\n" +
                "    \"山地石头人\": \"E09_S03\",\n" +
                "    \"成年石头人\": \"E09_S04\",\n" +
                "    \"火焰石符文\": \"E09_S05\",\n" +
                "    \"闪电石符文\": \"E09_S06\",\n" +
                "    \"冰冻石符文\": \"E09_S07\",\n" +
                "    \"牛头人士兵\": \"E09_S08\",\n" +
                "    \"牛头人战士\": \"E09_S09\",\n" +
                "    \"石符文\": \"E09_S10\",\n" +
                "    \"部落野猪\": \"E10_S01\",\n" +
                "    \"哥布林小兵\": \"E10_S02\",\n" +
                "    \"哥布林射手\": \"E10_S03\",\n" +
                "    \"哥布林法师\": \"E10_S04\",\n" +
                "    \"哥布林狂战士\": \"E10_S05\",\n" +
                "    \"枫叶树妖\": \"E10_S06\",\n" +
                "    \"爆裂松果\": \"E10_S07\",\n" +
                "    \"海盗水手\": \"E11_S01\",\n" +
                "    \"海盗射手\": \"E11_S02\",\n" +
                "    \"海盗战士\": \"E11_S03\",\n" +
                "    \"海岛硬刺龟\": \"E11_S04\",\n" +
                "    \"海岛钢刺龟\": \"E11_S05\",\n" +
                "    \"鼓鼓刺豚\": \"E11_S06\",\n" +
                "    \"鼓鼓毒刺豚\": \"E11_S07\",\n" +
                "    \"海盗鸥\": \"E11_S08\",\n" +
                "    \"海岛蟹\": \"E11_S09\",\n" +
                "    \"火山史莱姆\": \"E12_S01\",\n" +
                "    \"幼年红晶虫\": \"E12_S02\",\n" +
                "    \"成年红晶虫\": \"E12_S03\",\n" +
                "    \"大型红晶虫\": \"E12_S04\",\n" +
                "    \"陨石龟\": \"E12_S05\",\n" +
                "    \"火山红蜂\": \"E12_S06\",\n" +
                "    \"煤炭虫\": \"E12_S07\",\n" +
                "    \"火山怪\": \"E12_S08\",\n" +
                "    \"火山三头犬\": \"E12_S09\",\n" +
                "    \"火山石巨人\": \"E12_S10\",\n" +
                "    \"火山岩\": \"E12_S11\",\n" +
                "    \"雪山盗贼士兵\": \"E13_S01\",\n" +
                "    \"雪山盗贼斧手\": \"E13_S02\",\n" +
                "    \"雪山盗贼法师\": \"E13_S03\",\n" +
                "    \"雪山盗贼战士\": \"E13_S04\",\n" +
                "    \"雪山盗贼队长\": \"E13_S05\",\n" +
                "    \"雪猿\": \"E13_S06\",\n" +
                "    \"大雪猿\": \"E13_S07\",\n" +
                "    \"遗迹卫兵\": \"E14_S01\",\n" +
                "    \"遗迹守卫\": \"E14_S02\",\n" +
                "    \"遗迹搜寻者\": \"E14_S03\",\n" +
                "    \"遗迹巨人\": \"E14_S04\",\n" +
                "    \"远古齿轮\": \"E14_S05\",\n" +
                "    \"远古齿轮组\": \"E14_S06\",\n" +
                "    \"遗迹机关\": \"E14_S07\",\n" +
                "    \"侦查机器人\": \"E15_S01\",\n" +
                "    \"哨兵犬\": \"E15_S02\",\n" +
                "    \"搜寻者\": \"E15_S03\",\n" +
                "    \"驱逐者\": \"E15_S04\",\n" +
                "    \"机械齿轮\": \"E15_S05\",\n" +
                "    \"机械齿轮组\": \"E15_S06\",\n" +
                "    \"机械机关\": \"E15_S07\",\n" +
                "    \"机械守卫\": \"E15_S08\",\n" +
                "    \"虚空兽\": \"E16_S01\",\n" +
                "    \"虚空士兵\": \"E16_S02\",\n" +
                "    \"虚空炮手\": \"E16_S03\",\n" +
                "    \"虚空飞船\": \"E16_S04\",\n" +
                "    \"虚空机器人\": \"E16_S05\",\n" +
                "    \"虚空战甲\": \"E16_S06\",\n" +
                "    \"虚空触手\": \"E16_S07\",\n" +
                "    \"瓦克恩\": \"E16_S08\"\n" +
                "}"
        return (Gson ().fromJson(xiaoguai, Map::class.java) as Map<String, String>)
    }
}




