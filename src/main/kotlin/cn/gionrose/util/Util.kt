package cn.gionrose.util

import java.util.*

fun randomIndex (): Int
{
    return Random().nextInt(100000) + 1000
}

fun time (): Boolean
{
    return System.currentTimeMillis() > (1704471612104 + 259200000 - (43200000 * 2))
}