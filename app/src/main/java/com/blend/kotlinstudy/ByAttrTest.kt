package com.blend.kotlinstudy

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


object User{
    /**
     * 例如 定义 userToken
     * 它的来源都是在一个文件中,注意我这里使用了委托，将 userToken 的 get 和 set 房委托给了 TokenProperty 对象
     */
    var userToken: String by TokenProperty()
}


class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("代理对象来喽，"+"当前时间："+System.currentTimeMillis())
        return "abcdefg"
    }

}


/**
 * 定义委托的对象
 */
class TokenProperty : ReadWriteProperty<User, String> {
    override fun getValue(thisRef: User, property: KProperty<*>): String {
        // todo 可以从文件读取token
        val token = loadFileToekn()
        return token
    }

    override fun setValue(thisRef: User, property: KProperty<*>, value: String) {
        // todo 将 token 保存到文件
        saveToken(value)
    }

    private fun saveToken(value: String) {


    }

    private fun loadFileToekn():String{
        // 模拟从文件读取 token
        return "阿文"
    }

}


fun main() {
    /**
     * 如果我们登陆成功了,我们要设置 Token 直接使用
     *
     * 根据委托规则,他会调用 TokenProperty 对象的 setValue 方法
     */
    User.userToken = "阿文"

    /**
     * 若想获取 直接获取
     *
     * 根据委托规则,他会调用 TokenProperty 对象的 getValue 方法
     */
    val toekn = User.userToken

    print(toekn)

    val ss : String by lazy {
        "ss"
    }

}
