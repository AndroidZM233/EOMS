package speedata.com.eoms.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import speedata.com.eoms.service.AutoImportService;

/**
 * Created by 张明_ on 2017/5/8.
 */

public class USBBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.hardware.usb.action.USB_STATE")){
            Intent mIntent=new Intent();
            mIntent.setClass(context, AutoImportService.class);
            if (intent.getExtras().getBoolean("connected")){
                // usb 插入
                context.startService(mIntent);
            }else{
                //   usb 拔出
                context.stopService(mIntent);
            }
        }
    }
}
