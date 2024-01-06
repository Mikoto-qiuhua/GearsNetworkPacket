package cn.gionrose.component

import cn.gionrose.http.character

object RoleChooseScanner: AbstractScanner () {

    override val level = 3

    val roles = mutableMapOf<String, String>()

    override val next = FeaturesComponent

    override fun choose() {
        try{
            val index = answer.toInt() -1
            val roleNames = roles.values.toMutableList()
            if (roleNames.size > index && index >= 0)
            {
                chooseResult = roles.values.toMutableList()[index]
                isEnd = true
            }
        }catch (_: NumberFormatException){

        }

    }

    override fun question() {
        roles.values.forEachIndexed{ index, roleName ->
            println("${index+1}. $roleName")
        }
        print("请选择角色编号: ")
    }

    override fun execute() {
        character = roles.keys.toMutableList()[answer.toInt() -1]
    }

    override fun isOk(input: String): Boolean {
        return true
    }
}