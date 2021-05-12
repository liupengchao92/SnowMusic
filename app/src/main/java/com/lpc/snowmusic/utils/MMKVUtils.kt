package com.lpc.snowmusic.utils

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import java.util.*

/**
 * Author: liupengchao
 * Date: 2021/5/12
 * ClassName :MMKVUtils
 * Desc:
 */
object MMKVUtils {

    //MMKV 实例
    val mmkv: MMKV? by lazy { MMKV.defaultMMKV() }

    fun putValue(key: String, value: Any): Boolean {
        mmkv?.let {
            return when (value) {
                is String -> {
                    it.encode(key, value)
                }
                is Boolean -> {
                    it.encode(key, value)
                }
                is Int -> {
                    it.encode(key, value)
                }
                is Long -> {
                    it.encode(key, value)
                }
                is Float -> {
                    it.encode(key, value)
                }
                is Double -> {
                    it.encode(key, value)
                }
                is ByteArray -> {
                    it.encode(key, value)
                }
                else -> {
                    return false
                }
            }
        }

        return false
    }

    fun <T : Parcelable> putValue(key: String, t: T?): Boolean {
        if (t == null) {
            return false
        }
        return mmkv?.encode(key, t)!!
    }

    fun putValue(key: String, sets: Set<String>?): Boolean {
        if (sets == null) {
            return false
        }
        return mmkv?.encode(key, sets)!!
    }

    fun getInt(key: String): Int? {
        return mmkv?.decodeInt(key, 0)
    }

    fun getDouble(key: String): Double? {
        return mmkv?.decodeDouble(key, 0.00)
    }

    fun getLong(key: String): Long? {
        return mmkv?.decodeLong(key, 0L)
    }

    fun getBoolean(key: String): Boolean? {
        return mmkv?.decodeBool(key, false)
    }

    fun getFloat(key: String): Float? {
        return mmkv?.decodeFloat(key, 0F)
    }

    fun getByteArray(key: String): ByteArray? {
        return mmkv?.decodeBytes(key)
    }

    fun getString(key: String): String? {
        return mmkv?.decodeString(key, "")
    }

    /**
     * SpUtils.getParcelable<Class>("")
     */
    inline fun <reified T : Parcelable> getParcelable(key: String): T? {
        return mmkv?.decodeParcelable(key, T::class.java)
    }

    fun getStringSet(key: String): Set<String>? {
        return mmkv?.decodeStringSet(key, Collections.emptySet())
    }

    fun removeKey(key: String) {
        mmkv?.removeValueForKey(key)
    }

    fun clearAll() {
        mmkv?.clearAll()
    }

}