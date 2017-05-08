package speedata.com.eoms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 张明_ on 2017/4/27.
 */

public class GetTimeUtils {

    public static String getTimeStyle1() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getTimeStyle2() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
    public static String getTimeStyle3() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }


    // 将一种时间类型转为另一种
    public static String getStrinTimeToAnotherStringTime(String startDate,
                                                 String formatstr,String newformatstr) {
        SimpleDateFormat format = new SimpleDateFormat(formatstr);
        Date date = null;
        if (!StringUtil.isBlank(startDate)) {
            try {
                date = format.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
                date = new Date();
            }
        } else {
            date = new Date();
        }

        SimpleDateFormat newFormat=new SimpleDateFormat(newformatstr);
        String result = newFormat.format(date);

        return result;
    }

}
