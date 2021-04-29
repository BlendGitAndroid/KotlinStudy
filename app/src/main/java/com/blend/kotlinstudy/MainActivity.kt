package com.blend.kotlinstudy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //git revert
        //在develop上做了一些更改
        //在feature上做了一些更改
        val sss = object : TextWatcher by noOpDelegate<TextWatcher>() {
            override fun afterTextChanged(s: Editable?) {

            }
        }
    }
}

inline fun <reified T : Any> noOpDelegate(): T {
    val javaClass = T::class.java
    val noOpHandler = InvocationHandler { _, _, _ ->
        // no op
    }
    return Proxy.newProxyInstance(
        javaClass.classLoader, arrayOf(javaClass), noOpHandler
    ) as T
}

class MyMap(private val realMap: HashMap<Int, Int>) : MutableMap<Int, Int> by realMap {

    private var lastKey = 0

    override fun put(key: Int, value: Int): Int? {
        lastKey = key
        return realMap.put(key,value)
    }

    fun recover() {
        realMap.remove(lastKey)
    }
}
