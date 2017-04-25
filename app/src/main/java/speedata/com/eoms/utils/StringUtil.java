/*
 *
 * @author Echo
 * @created 2016.6.3
 * @email bairu.xu@speedatagroup.com
 * @version $version
 *
 */

package speedata.com.eoms.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ThinkPad on 2015/6/22.
 */
public class StringUtil {
    /**
     * 是否为空，包含(""和null)  <br>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     *
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断给定字符串是否空白串。<br>
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串<br>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     *
     * @return boolean
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将int型字符串，转换为int类型 </br>
     * <pre>StringUtils.stringToInt("123")  = 123 </pre>
     *
     * @param str
     * @return
     */
    public static int stringToInt(String str) {
        if (null == str || "".equals(str)) {
            return 0;
        }
        int it = -1;
        try {
            it = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return it;
    }

    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    public static boolean checkCorrect(String num) {
        String str = ValidatorUtil.REGEX_MOBILE;
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(num);
        return m.matches();
    }


    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
