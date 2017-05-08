package speedata.com.eoms.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import speedata.com.eoms.utils.SharedXmlUtil;

/**
 * Created by 张明_ on 2017/5/5.
 */

public class AutoImportService extends Service {
    private Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int time = SharedXmlUtil.getInstance(AutoImportService.this).read("scheduleTime", 10);
        int scheduleTime = time * 60 * 1000;
        startTimer(scheduleTime);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void stopTimer() {
        timer.cancel();
        timer = null;
    }

    private void startTimer(int time) {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new ImportTimerTask(), 0, time);

    }

    private class ImportTimerTask extends TimerTask {

        @Override
        public void run() {

        }
    }
}
