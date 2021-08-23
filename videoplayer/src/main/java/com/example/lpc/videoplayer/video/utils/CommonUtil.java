package com.example.lpc.videoplayer.video.utils;

import java.util.Formatter;
import java.util.Locale;

/**
 * Author: liupengchao
 * Date: 2021/8/23
 * ClassName :CommonUtil
 * Desc:
 */
public class CommonUtil {

    /**
     * 格式化时间
     *
     * @param timeMs
     * @return
     */
    public static String stringForTime(int timeMs) {
        if (timeMs > 0 && timeMs < 86400000) {
            int totalSeconds = timeMs / 1000;
            int seconds = totalSeconds % 60;
            int minutes = totalSeconds / 60 % 60;
            int hours = totalSeconds / 3600;
            StringBuilder stringBuilder = new StringBuilder();
            Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
            return hours > 0 ? mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString() : mFormatter.format("%02d:%02d", minutes, seconds).toString();
        } else {
            return "00:00";
        }
    }
}
