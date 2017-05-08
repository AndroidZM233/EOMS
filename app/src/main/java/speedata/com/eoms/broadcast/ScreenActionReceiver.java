package speedata.com.eoms.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import speedata.com.eoms.service.CleanCacheService;

/**
 * Created by 张明_ on 2017/5/8.
 */

public class ScreenActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mIntent = new Intent(context, CleanCacheService.class);
        context.startService(mIntent);
    }
}
