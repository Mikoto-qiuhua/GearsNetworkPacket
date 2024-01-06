package cn.gionrose.component

import cn.gionrose.component.FeaturesComponent.FeatureType.*
import cn.gionrose.http.FeatureRequest

class NumberScanner: AbstractScanner() {
    override val level = 5

     var type: FeaturesComponent.FeatureType? = null

    override val root = FeaturesComponent

    override val next = ItemCategoryScanner

    override fun choose() {
        try {
            chooseResult = answer
            isEnd = true
        }catch (_: NumberFormatException)
        {}
    }

    override fun question() {
        print ("请输入次数: ")
    }

    override fun execute() {
        var count = 1
        try {
            count = chooseResult.toInt()
        }catch (_: NumberFormatException)
        {
            loop()
        }
        when (type)
        {
            Feature_10W -> { repeat(count) {
                Thread.sleep(300)
                val result = FeatureRequest.postCostGold10W()
                if (result)
                    println ("成功增加 10w  [第 $it 次]")
            }
                root.loop()
            }

            Feature_100W -> { repeat(count) {
                Thread.sleep(300)
                val result = FeatureRequest.postCostGold100W()
                if (result)
                    println ("成功增加 100w  [第 $it 次]")
            }
                root.loop()
            }

            Feature_Item -> {
                next.passed(count)
            }
            null -> TODO()
        }
    }

}