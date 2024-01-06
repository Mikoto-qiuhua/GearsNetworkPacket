package cn.gionrose.component

import cn.gionrose.http.LoginRequest
import cn.gionrose.http.authorization

object PasswordScanner: AbstractScanner()
{
    override val level = 2

    override val root = UserNameScanner

    override val next = RoleChooseScanner
    override fun choose() {
        chooseResult = answer
        isEnd = true
    }

    override fun question() {
        println()
        print ("请输入密码: ")
    }

    override fun execute() {
        LoginRequest.password = chooseResult
        LoginRequest.postLoginId()
    }

    override fun afterExecute() {
        if (authorization != "")
        {
            println ()
            println("================= 登录成功 ================")

            next.roles.putAll(LoginRequest.postRoleId())
        }else {
            println ()
            println("================= 登录失败 ================")
            root.loop()
        }
    }


}