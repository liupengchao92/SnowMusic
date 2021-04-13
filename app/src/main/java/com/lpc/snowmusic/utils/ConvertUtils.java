package com.lpc.snowmusic.utils;

import android.text.TextUtils;
import com.blankj.utilcode.util.LogUtils;
import com.lpc.snowmusic.R;
import com.lpc.snowmusic.application.MusicApplication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: liupengchao
 * Date: 2021/4/13
 * ClassName :ConvertUtils
 * Desc:
 */
public class ConvertUtils {

    /**
     * 音乐名格式化
     *
     * @param title
     * @return
     */
    public static String getTitle(String title) {
        title = stringFilter(title);
        if (TextUtils.isEmpty(title)) {
            title = MusicApplication.Companion.getContext().getString(R.string.unknown);
        }
        return title;
    }

    /**
     * 歌手名格式化
     *
     * @param artist
     * @return
     */
    public static String getArtist(String artist) {
        artist = stringFilter(artist);
        if (TextUtils.isEmpty(artist)) {
            artist = MusicApplication.Companion.getContext().getString(R.string.unknown);
        }
        return artist;
    }


    /**
     * 歌手专辑格式化
     *
     * @param artist
     * @param album
     * @return
     */
    public static String getArtistAndAlbum(String artist, String album) {
        artist = stringFilter(artist);
        album = stringFilter(album);
        if (TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            return "";
        } else if (!TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            return artist;
        } else if (TextUtils.isEmpty(artist) && !TextUtils.isEmpty(album)) {
            return album;
        } else {
            return artist + " - " + album;
        }
    }


    /**
     * 过滤特殊字符(\/:*?"<>|)
     */
    private static String stringFilter(String str) {
        if (str == null) {
            return null;
        }
        String regEx = "<[^>]+>";
//        String regEx = "[\\/:*?\"<>|]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static float b2mb(int b) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        String MB = decimalFormat.format((float) b / 1024 / 1024);
        return Float.valueOf(MB);
    }

    /**
     * string转inputStream按编码
     *
     * @param string      字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    public static InputStream string2InputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return new ByteArrayInputStream(string.getBytes());
        } catch (Exception e) {
            LogUtils.e("--", "UnsupportedEncodingException");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断字符串是否为null或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空白字符<br> {@code false}: 不为null且不全空白字符
     */
    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
