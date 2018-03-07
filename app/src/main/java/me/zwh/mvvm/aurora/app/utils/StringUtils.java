package me.zwh.mvvm.aurora.app.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Collection;

import me.zwh.mvvm.aurora.mvvm.model.entry.VideoListInfo;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class StringUtils {
    public static boolean isEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static <V> boolean isEmpty(Collection<V> collection) {
        return (collection == null || collection.size() == 0);
    }

    public static String replaceNull(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    public static <V> boolean isEmpty(V[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }

    public static String getUrlDecodePath(String urlencode) {
        String path = "";
        try {
            path = java.net.URLDecoder.decode(urlencode, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
        String[] s = path.split("&");
        path = s[1].replace("url=http://baobab.kaiyanapp.com/api/v4/video/", "");
        return path;
    }

    public static boolean isMobile(String number) {
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    public static String getPrintSize(long size, boolean withUnit) {
        DecimalFormat df = new DecimalFormat("#.0");
        String fileSizeString = "";
        if (size < 1024) {
            if (withUnit){
                fileSizeString = df.format((double) size) + "B";
            }else {
                fileSizeString =0.0 + "";
            }
        } else if (size < 1048576) {
            if (withUnit){
                fileSizeString = df.format((double) size / 1024) + "KB";
            }else {
                fileSizeString =0.0 + "";
            }
        } else if (size < 1073741824) {
            if (withUnit){
                fileSizeString = df.format((double) size / 1048576) + "MB";
            }else {
                fileSizeString = df.format((double) size / 1048576);
            }
        } else {
            if (withUnit){
                fileSizeString = df.format((double) size / 1073741824) + "GB";
            }else {
                fileSizeString = df.format((double) size / 1073741824);
            }
        }
        return fileSizeString;
    }


    public static String getDetailStr(VideoListInfo.Video item){
        String duration = item.getData().getDuration()+"";
        int seconds = Integer.parseInt(duration);
        int temp=0;
        StringBuffer sb=new StringBuffer();
        temp = seconds/3600;
        sb.append((temp<10)?"0"+temp+":":""+temp+":");

        temp=seconds%3600/60;
        sb.append((temp<10)?"0"+temp+":":""+temp+":");

        temp=seconds%3600%60;
        sb.append((temp<10)?"0"+temp:""+temp);
        String detail = "#"+item.getData().getCategory()+" / "+sb.toString();
        return detail;
    }

}
