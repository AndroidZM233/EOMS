package speedata.com.eoms.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import speedata.com.eoms.utils.BimpUtil;
import speedata.com.eoms.utils.SharedXmlUtil;

/**
 * Created by 张明_ on 2017/5/8.
 */

public class CleanCacheService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        int time = SharedXmlUtil.getInstance(this).read("cacheTime", 100);
        long cacheDate = SharedXmlUtil.getInstance(this).read("cacheDate", 1494296840234L);
        long timeMillis = System.currentTimeMillis();
        //换算成天数
        int result = (int) ((timeMillis - cacheDate) / 1000 / 60 / 60 / 24);
        if (result > time) {
            BimpUtil.delCache("/storage/emulated/0/data/HTYL/Out","_ok.log");
            SharedXmlUtil.getInstance(this).write("cacheDate",System.currentTimeMillis());
        }
        Intent intent=new Intent(this,CleanCacheService.class);
        stopService(intent);
    }

}
