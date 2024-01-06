package cn.gionrose.component

object FeaturesComponent: AbstractScanner () {

    override val level = 4

    override val next = NumberScanner ()
    override fun choose() {
        FeatureType.idOf(answer)?.let {
            chooseResult = it.id
            isEnd = true
        }
    }

    override fun question() {
        FeatureType.entries.forEach {
            println ("${it.id}. ${it.featureName}")
        }
        print ("请选择你想要的功能序号: ")
    }

    override fun execute() {
        next.type = FeatureType.idOf(answer)!!

    }

    enum class FeatureType (val id: String, val featureName: String)
    {
        Feature_10W(id = "1", featureName = "10w金币"),
        Feature_100W(id = "2", featureName = "100w金币"),
        Feature_Item(id = "3", featureName = "物品");

       companion object{

           @JvmStatic
           fun idOf (id: String): FeatureType?
           {
               return entries.find { it.id == id }
           }
       }

    }
}