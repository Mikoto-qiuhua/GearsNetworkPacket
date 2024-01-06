package cn.gionrose.component

import cn.gionrose.http.FeatureRequest

object ItemTypeScanner: AbstractScanner ()
{
    override val level = 7

    private var executeCount = 0

    private var isSuccess = false

    var itemCategory = ItemCategoryScanner.ItemCategory.STONE

    override val root = FeaturesComponent

    override var next =  CardTypeScanner

    override fun choose() {
        when (itemCategory)
        {
            ItemCategoryScanner.ItemCategory.STONE -> {
                StoneType.idOf(answer)?.let {
                    chooseResult = it.id
                    isEnd = true
                }
            }
            ItemCategoryScanner.ItemCategory.CARD -> {
                CardType.idOf(answer)?.let {
                    chooseResult = it.id
                    isEnd = true
                }
            }
        }
        //todo 这里的chooseResult是数字 得改成 chineseName
    }

    override fun question() {
        when (itemCategory)
        {
            ItemCategoryScanner.ItemCategory.STONE -> {
                StoneType.entries.forEach{
                    println ("${it.id}. ${it.chineseName}")
                }
            }
            ItemCategoryScanner.ItemCategory.CARD -> {
                CardType.entries.forEach{
                    println ("${it.id}. ${it.typeName}")
                }
            }
        }
        print ("请选择具体的物品编号: ")
    }

    override fun execute() {
        when (itemCategory)
        {
            ItemCategoryScanner.ItemCategory.STONE -> {
                isSuccess = FeatureRequest.selectTypeGetItem(executeCount,
                    itemCategory.typeName,
                    null,
                    StoneType.idOf(chooseResult)!!.stoneId,
                    StoneType.idOf(chooseResult)!!.typeName
                )
            }

            ItemCategoryScanner.ItemCategory.CARD -> {
                next.passed(executeCount)
                next.cardType = CardType.idOf(chooseResult)!!
                next.itemCategory = ItemCategoryScanner.ItemCategory.CARD
            }
        }
    }

    override fun afterExecute() {
        if (itemCategory == ItemCategoryScanner.ItemCategory.STONE)
        {
            if (isSuccess)
                println ("成功刷出")
            else println("失败... (请查看接口是否可用)")

            root.loop()
        }
    }

    fun passed (executeCount: Int)
    {
        this.executeCount = executeCount
    }

    enum class StoneType (val chineseName: String, val typeName: String, val id: String, val stoneId: String)
    {
        FireRemakeStone ("火山石", "FireRemakeStone", "1", "JEWEL_001"),
        UpgradeStone ("磨练石", "UpgradeStone", "2", "JEWEL_003"),
        WindRemakeStone ("风化石", "WindRemakeStone", "3", "JEWEL_001");

        companion object
        {
            @JvmStatic
            fun idOf (id: String): StoneType?
            {
                StoneType.entries.forEach{
                    if (id == it.id)
                        return it
                }
                return null
            }
        }

    }

    enum class CardType (val id: String, val typeName: String)
    {
        MONSTER ("1", "小怪"),
        BOSS ("2", "Boss"),
        NPC ("3", "NPC");

        companion object
        {
            @JvmStatic
            fun idOf (id: String): CardType?
            {
                CardType.entries.forEach{
                    if (id == it.id)
                        return it
                }
                return null
            }
        }
    }

}