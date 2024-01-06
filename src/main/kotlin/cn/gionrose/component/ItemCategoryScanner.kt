package cn.gionrose.component

object ItemCategoryScanner: AbstractScanner ()
{
    override val level = 6

    private var executeCount = 0

    override val next = ItemTypeScanner
    override fun choose() {
        ItemCategory.idOf(answer)?.let {
            chooseResult = it.id
            isEnd = true
            //todo 这里的chooseResult是数字 得改成 chineseName
        }
    }

    override fun question() {
        ItemCategory.entries.forEach {
            println ("${it.id}. ${it.chineseName}")
        }
        print ("请输入物品类型: ")
    }

    override fun execute() {
        next.itemCategory = ItemCategory.idOf(chooseResult)!!
        next.passed(executeCount)
    }

    fun passed (executeCount: Int)
    {
        this.executeCount = executeCount
    }

    enum class ItemCategory (val id: String, val typeName: String, val chineseName: String)
    {
        CARD ("1", "cardPool", "卡片"),
        STONE ("2", "genericItems", "石头");

        companion object
        {
            @JvmStatic
            fun idOf (id: String): ItemCategory?
            {
                entries.forEach{
                    if (id == it.id)
                        return it
                }
                return null
            }
        }


    }

}