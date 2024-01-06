package cn.gionrose.util

import java.util.Scanner


val scanner = Scanner(System.`in`)

fun readLine (): String
{
    return scanner.nextLine()
}

fun readLineToInt (): Int
{
    return scanner.nextLine().toInt()
}

