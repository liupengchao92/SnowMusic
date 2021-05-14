package com.lpc.snowmusic.utils

import android.content.Context
import com.lpc.snowmusic.application.MusicApplication
import java.io.*

/**
 * Author: liupengchao
 * Date: 2021/5/14
 * ClassName :FileUtils
 * Desc:文件工具类 */
object FileUtils {

    /**
     * 获取APP根目录
     *
     * @return
     */
    private fun getAppDir(): String {
        return MusicApplication.context.getExternalFilesDir("/SnowMusic/").toString()
    }

    fun getMusicDir(): String {
        val dir = getAppDir() + "Music/"
        return mkdirs(dir)
    }


    fun getMusicCacheDir(): String {
        val dir = getAppDir() + "MusicCache/"
        return mkdirs(dir)
    }

    fun getImageDir(): String {
        val dir = getAppDir() + "cache/"
        return mkdirs(dir)
    }

    fun getLrcDir(): String {
        val dir = getAppDir() + "Lyric/"
        return mkdirs(dir)
    }

    fun getLogDir(): String {
        val dir = getAppDir() + "Log/"
        return mkdirs(dir)
    }

    fun getSplashDir(context: Context): String {
        val dir = context.filesDir.toString() + "/splash/"
        return mkdirs(dir)
    }

    fun getRelativeMusicDir(): String {
        val dir = "hkMusic/Music/"
        return mkdirs(dir)
    }

    private fun mkdirs(dir: String): String {
        val file = File(dir)
        if (!file.exists()) {
            file.mkdirs()
        }
        return dir
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     */
    fun exists(filePath: String): Boolean {
        return File(filePath).exists()
    }


    /**
     * 文件读取
     * strPath 文件路径
     */
    fun readFile(strPath: String): String? {
        return readFile(File(strPath))
    }


    /**
     * 文件读取
     * filePath 文件路径
     */
    fun readFile(filePath: File): String? {

        var bufferedReader: BufferedReader? = null
        val fileStr = StringBuilder()
        if (!filePath.exists() || filePath.isDirectory) {
            return null
        }
        try {
            bufferedReader = BufferedReader(FileReader(filePath))
            while (bufferedReader.readLine()?.apply {
                    fileStr.append(this)
                    fileStr.append("\n")
                } != null) {
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return fileStr.toString()
    }


    /**
     * 写入数据到文件
     *
     * @param filePath
     * @param content
     * @return
     */
    fun writeText(filePath: File, content: String): Boolean {
        creatFile(filePath)
        var bufferedWriter: BufferedWriter? = null
        try {
            bufferedWriter = BufferedWriter(FileWriter(filePath))
            bufferedWriter.write(content)
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return true
    }

    fun writeText(filePath: String, content: String): Boolean {
        return writeText(File(filePath), content)
    }

    /**
     * 创建文件
     * file 文件
     */
    fun creatFile(file: File): Boolean {
        var newFile = false
        if (!file.exists()) {
            try {
                newFile = file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                newFile = false
            }

        }
        return newFile
    }

}