package cn.gionrose.component

import cn.gionrose.http.LoginRequest
import cn.gionrose.util.*
import com.sun.org.apache.xpath.internal.operations.Bool
import org.GearsNetworkPacket.HttprRquest.BasePost

abstract class AbstractScanner {

    abstract val level: Int

    open val next: AbstractScanner? = null

    open val root: AbstractScanner? = null

    var answer: String = ""

    var chooseResult: String = ""

    var isEnd = false

    abstract fun choose ()
    abstract fun question()

    abstract fun execute ()

    open fun afterExecute ()
    {

    }

    private fun answer ()
    {
        answer = readLine()
    }

    open fun isOk (input: String): Boolean
    {
        print ("是否确认你输入 [$input](y/n):")
        return "y" == readLine()
    }


    fun loop ()
    {
       while (true)
       {
           question()
           answer()
           choose()

           if (isEnd && isOk(chooseResult))
            break
       }
        isEnd = false
        execute()
        afterExecute()
        next ()
    }

    private fun next ()
    {
        next?.loop()
    }





}