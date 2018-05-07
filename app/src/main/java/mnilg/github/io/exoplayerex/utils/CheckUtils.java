package mnilg.github.io.exoplayerex.utils;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.regex.Pattern;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/7 17:36
 */
public class CheckUtils {
    private CheckUtils() {

    }

    /**
     * 是否包含汉字<br>
     * 根据汉字编码范围进行判断<br>
     * CJK统一汉字（不包含中文的，。《》（）“‘’”、！￥等符号）<br>
     *
     * @param str
     * @return
     */
    public static boolean hasChineseByReg(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str).find();
    }

    /**
     * 是否全是汉字<br>
     * 根据汉字编码范围进行判断<br>
     * CJK统一汉字（不包含中文的，。《》（）“‘’”、！￥等符号）<br>
     *
     * @param str
     * @return
     */
    public static boolean isChineseByReg(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否是中文字符<br>
     * 包含中文标点符号<br>
     *
     * @param c
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D) {
            return true;
        } else if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 是否全是中文字符<br>
     * 包含中文标点符号<br>
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        if (str == null) {
            return false;
        }
        char[] ch = str.toCharArray();
        for (char c : ch) {
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否包含汉字<br>
     * 根据汉字编码范围进行判断<br>
     * CJK统一汉字（不包含中文的，。《》（）“‘’”、！￥等符号）<br>
     *
     * @param str
     * @return
     */
    public static boolean hasChineseByRange(String str) {
        if (str == null) {
            return false;
        }
        char[] ch = str.toCharArray();
        for (char c : ch) {
            if (c >= 0x4E00 && c <= 0x9FBF) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否全是汉字<br>
     * 根据汉字编码范围进行判断<br>
     * CJK统一汉字（不包含中文的，。《》（）“‘’”、！￥等符号）<br>
     *
     * @param str
     * @return
     */
    public static boolean isChineseByRange(String str) {
        if (str == null) {
            return false;
        }
        char[] ch = str.toCharArray();
        for (char c : ch) {
            if (c < 0x4E00 || c > 0x9FBF) {
                return false;
            }
        }
        return true;
    }
}
