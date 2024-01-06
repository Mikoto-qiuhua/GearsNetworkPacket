package cn.gionrose.component

import cn.gionrose.http.LoginRequest

object UserNameScanner: AbstractScanner()
{
    override val level = 1

    override val next = PasswordScanner

    override fun choose() {
        chooseResult = answer
        isEnd = true
    }

    override fun question() {
        println()
        print ("请输入用户名: ")
    }

    override fun execute() {
        LoginRequest.username = chooseResult
    }


}