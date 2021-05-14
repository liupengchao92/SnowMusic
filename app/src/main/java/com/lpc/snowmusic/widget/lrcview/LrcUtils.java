package com.lpc.snowmusic.widget.lrcview;

import android.animation.ValueAnimator;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Author: liupengchao
 * Date: 2021/5/14
 * ClassName :LrcUtils
 * Desc:
 */
public class LrcUtils {

    private static final Pattern PATTERN_LINE = Pattern.compile("(\\[\\d\\d:\\d\\d:\\dd\\d{2,3})(.+)\\]");
    private static final Pattern PATTERN_TIME = Pattern.compile("\\[(\\d\\d):(\\d\\d)\\.(\\d{2,3})\\]");

    /**
     * 从文件解析双语歌词
     */
    static List<LrcEntry> parseLrc(File[] lrcFiles) {
        if (lrcFiles == null || lrcFiles.length != 2 || lrcFiles[0] == null) {
            return null;
        }

        File mainLrcFile = lrcFiles[0];
        File secondLrcFile = lrcFiles[1];
        List<LrcEntry> mainEntryList = parseLrc(mainLrcFile);
        List<LrcEntry> secondEntryList = parseLrc(secondLrcFile);

        if (mainEntryList != null && secondEntryList != null) {
            for (LrcEntry mainEntry : mainEntryList) {
                for (LrcEntry secondEntry : secondEntryList) {
                    if (mainEntry.getTime() == secondEntry.getTime()) {
                        mainEntry.setSecondText(secondEntry.getText());
                    }
                }
            }
        }
        return mainEntryList;
    }

    /**
     * 从文件解析歌词
     */
    private static List<LrcEntry> parseLrc(File lrcFile) {
        if (lrcFile == null || !lrcFile.exists()) {
            return null;
        }

        List<LrcEntry> entryList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(lrcFile), "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                List<LrcEntry> list = parseLine(line);
                if (list != null && !list.isEmpty()) {
                    entryList.addAll(list);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(entryList);
        return entryList;
    }

    /**
     * 从文本解析双语歌词
     */
    static List<LrcEntry> parseLrc(String[] lrcTexts) {
        if (lrcTexts == null || lrcTexts.length != 2 || TextUtils.isEmpty(lrcTexts[0])) {
            return null;
        }

        String mainLrcText = lrcTexts[0];
        String secondLrcText = lrcTexts[1];
        List<LrcEntry> mainEntryList = parseLrc(mainLrcText);
        List<LrcEntry> secondEntryList = parseLrc(secondLrcText);

        if (mainEntryList != null && secondEntryList != null) {
            for (LrcEntry mainEntry : mainEntryList) {
                for (LrcEntry secondEntry : secondEntryList) {
                    if (mainEntry.getTime() == secondEntry.getTime()) {
                        mainEntry.setSecondText(secondEntry.getText());
                    }
                }
            }
        }
        return mainEntryList;
    }

    /**
     * 从文本解析歌词
     */
    private static List<LrcEntry> parseLrc(String lrcText) {
        if (TextUtils.isEmpty(lrcText)) {
            return null;
        }

        if (lrcText.startsWith("\uFEFF")) {
            lrcText = lrcText.replace("\uFEFF", "");
        }

        List<LrcEntry> entryList = new ArrayList<>();
        String[] array = lrcText.split("\\n");
        for (String line : array) {
            List<LrcEntry> list = parseLine(line);
            if (list != null && !list.isEmpty()) {
                entryList.addAll(list);
            }
        }

        Collections.sort(entryList);
        return entryList;
    }

    /**
     * 获取网络文本，需要在工作线程中执行
     */
    static String getContentFromNetwork(String url, String charset) {
        String lrcText = null;
        try {
            URL _url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                is.close();
                bos.close();
                lrcText = bos.toString(charset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lrcText;
    }

    /**
     * 解析一行歌词
     */
    private static List<LrcEntry> parseLine(String line) {
        List<LrcEntry> entryList = new ArrayList<>();

       /* if (TextUtils.isEmpty(line)) {
            return null;
        }

        line = line.trim();
        // [00:17.65]让我掉下眼泪的
        Matcher lineMatcher = PATTERN_LINE.matcher(line);
        if (!lineMatcher.matches()) {
            return null;
        }

        String times = lineMatcher.group(1);
        String text = lineMatcher.group(3);

        // [00:17.65]
        Matcher timeMatcher = PATTERN_TIME.matcher(times);
        while (timeMatcher.find()) {
            long min = Long.parseLong(timeMatcher.group(1));
            long sec = Long.parseLong(timeMatcher.group(2));
            String milString = timeMatcher.group(3);
            long mil = Long.parseLong(milString);
            // 如果毫秒是两位数，需要乘以10
            if (milString.length() == 2) {
                mil = mil * 10;
            }
            long time = min * DateUtils.MINUTE_IN_MILLIS + sec * DateUtils.SECOND_IN_MILLIS + mil;
            entryList.add(new LrcEntry(time, text));
        }*/


        try {
            int index = line.indexOf("]");
            if (line.startsWith("[offset:")) {
                // 时间偏移量
                String string = line.substring(8, index).trim();

            }
            if (line.startsWith("[ti:")) {
                // title 标题
                String string = line.substring(4, index).trim();

            }
            if (line.startsWith("[ar:")) {
                // artist 作者
                String string = line.substring(4, index).trim();
            }
            if (line.startsWith("[al:")) {
                // album 所属专辑
                String string = line.substring(4, index).trim();
            }
            if (line.startsWith("[by:")) {
                return entryList;
            }
            if (line.startsWith("[total:")) {
                return entryList;
            }
            if (line.startsWith("[0") && line.endsWith("]")) {
                String test = line.replace("]", "").replaceFirst(", ", "]");
                if (test.contains("]")) {
                    line = test;
                }
            }
            if (line.startsWith("[0") && !line.endsWith("]")) {
                // 歌词内容,需要考虑一行歌词有多个时间戳的情况
                int lastIndexOfRightBracket = line.lastIndexOf("]");
                String content = line.substring(lastIndexOfRightBracket + 1, line.length());
                //去除trc歌词中每个字的时间长
                content = content.replaceAll("<[0-9]{1,5}>", "");
                if (content.length() == 0) {
                    content = "......";
                }
                String times = line.substring(0, lastIndexOfRightBracket + 1).replace("[", "-").replace("]", "-");
                String arrTimes[] = times.split("-");
                for (String temp : arrTimes) {
                    if (temp.trim().length() == 0) {
                        continue;
                    }
                    /** [02:34.14][01:07.00]当你我不小心又想起她
                     *
                     上面的歌词的就可以拆分为下面两句歌词了
                     [02:34.14]当你我不小心又想起她
                     [01:07.00]当你我不小心又想起她
                     */
                    LrcEntry lrcEntry = new LrcEntry(measureStartTimeMillis(temp), content.trim());
                    entryList.add(lrcEntry);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entryList;
    }

    /**
     * 将解析得到的表示时间的字符转化为Long型
     */
    private static long measureStartTimeMillis(String timeString) {
        //因为给如的字符串的时间格式为XX:XX.XX,返回的long要求是以毫秒为单位
        //将字符串 XX:XX.XX 转换为 XX:XX:XX
        timeString = timeString.replace('.', ':');
        //将字符串 XX:XX:XX 拆分
        String[] times = timeString.split(":");
        // mm:ss:SS
        return Integer.valueOf(times[0]) * 60 * 1000 +//分
                Integer.valueOf(times[1]) * 1000 +//秒
                Integer.valueOf(times[2]);//毫秒
    }

    /**
     * 转为[分:秒]
     */
    static String formatTime(long milli) {
        int m = (int) (milli / DateUtils.MINUTE_IN_MILLIS);
        int s = (int) ((milli / DateUtils.SECOND_IN_MILLIS) % 60);
        String mm = String.format(Locale.getDefault(), "%02d", m);
        String ss = String.format(Locale.getDefault(), "%02d", s);
        return mm + ":" + ss;
    }

    /**
     * 强制开启动画
     * Android 10 以后无法使用
     */
    static void resetDurationScale() {
        try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                Field mField = ValueAnimator.class.getDeclaredField("sDurationScale");
                mField.setAccessible(true);
                mField.setFloat(null, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
