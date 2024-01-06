package cn.gionrose.component

import cn.gionrose.facered.Main
import cn.gionrose.http.FeatureRequest
import org.GearsNetworkPacket.HttprRquest.SendPost

object CardTypeScanner: AbstractScanner () {
    override val level = 8

    private var executeCount = 0

    private var isSuccess = false

    override val root = FeaturesComponent

    var itemCategory = ItemCategoryScanner.ItemCategory.CARD

    var cardType = ItemTypeScanner.CardType.BOSS

    private lateinit var typeMap: Map<String, String>
    override fun choose() {
        try{
            val index = answer.toInt() -1
            val chineseNames = typeMap.keys.toMutableList()

            if (chineseNames.size > index && index >= 0)
            {
                chooseResult = chineseNames[index]
                isEnd = true
            }
        }catch (_: NumberFormatException){

        }
    }

    override fun question() {

        loadCardType ()

        typeMap.keys.onEachIndexed{index, chineseName ->

            val afterIndex = index+1
            val totalLength = "$afterIndex. $chineseName".length
            when (totalLength)
            {
                6 -> print("$afterIndex. $chineseName              ")
                7 -> print("$afterIndex. $chineseName            ")
                8 -> print("$afterIndex. $chineseName          ")
                9 -> print("$afterIndex. $chineseName        ")
                10 -> print("$afterIndex. $chineseName      ")
                11 -> print("$afterIndex. $chineseName    ")
            }

            if ((afterIndex % 10) == 0)
                    println()
        }
        println()
        print ("请选择 card 类型编号: ")
    }

    override fun execute() {
        isSuccess = FeatureRequest.selectTypeGetItem(executeCount, itemCategory.typeName, typeMap[chooseResult], null, null)
    }

    override fun afterExecute() {
        if (isSuccess)
            println ("成功刷出")
        else println("失败... (请查看接口是否可用)")

        root.loop()
    }

    fun passed (executeCount: Int)
    {
        this.executeCount = executeCount
    }

    private fun loadCardType ()
    {
        typeMap = when (cardType)
        {
            ItemTypeScanner.CardType.BOSS -> Main.getBoss()
            ItemTypeScanner.CardType.MONSTER -> Main.getXiaoGuai()
            ItemTypeScanner.CardType.NPC -> Main.getNpc()
        }
    }
}