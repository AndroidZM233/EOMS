package speedata.com.eoms.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by 张明_ on 2017/5/15.
 */

public class BanBottomBarUtils {
    private static final String RECENT_SWITCH="com.speedata.recent";//最近任务
    private static final String BACK_SWITCH="com.speedata.back";//返回键
    private static final String HOME_SWITCH="com.speedata.home";//禁用启用HOME键
    private static final String UPMENU_SWITCH="com.speedata.upmenu";//下拉通知栏

    /**
     * 禁用启用最近任务
     * @param enable
     * @param context
     */

    public static void recent(Boolean enable,Context context){//禁用启用最近任务

        Intent intent = new Intent(RECENT_SWITCH);
        intent.putExtra("enablerecent",enable);
        context.sendBroadcast(intent);
    }

    /**
     * 禁用启用返回键
     * @param enable
     * @param context
     */
    public static void back(Boolean enable,Context context){//禁用启用返回键
        if(context ==null){
            return;
        }
        Intent intent = new Intent(BACK_SWITCH);
        intent.putExtra("enableback", enable);
        context.sendBroadcast(intent);
    }
    /**
     * 禁用或启用Home键，包括长按、短按
     * @param enable
     * @param context
     */
    public static void home(Boolean enable,Context context){//禁用或启用Home键，包括长按、短按
        if(context ==null){
            return;
        }
        Intent intent = new Intent(HOME_SWITCH);
        intent.putExtra("enablehome", enable);
        context.sendBroadcast(intent);

    }
    /**
     * 禁用或启用下拉通知栏
     * @param enable
     * @param context
     */
    public static void upmenu(Boolean enable,Context context){//禁用或启用下拉通知栏
        if(context ==null){
            return;
        }
        Intent intent = new Intent(UPMENU_SWITCH);
        intent.putExtra("enableupmenu", enable);
        context.sendBroadcast(intent);

    }

}
