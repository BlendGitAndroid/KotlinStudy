package com.blend.basic.NyetHack

/**
 * 重写eruals方法和hashCode方法
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