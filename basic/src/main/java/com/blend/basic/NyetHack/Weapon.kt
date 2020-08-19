package com.blend.basic.NyetHack

/**
 * 重写equals方法和hashCode方法,按照自己决定如何按结构比较自定义类
 * 这些都是自动生成的功能
 */
open class Weapon(val name: String, val type: String) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Weapon) return false

        if (name != other.name) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

}