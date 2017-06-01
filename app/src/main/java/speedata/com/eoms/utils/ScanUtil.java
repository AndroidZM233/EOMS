package speedata.com.eoms.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Timer;
import java.util.TimerTask;

public class ScanUtil {

	public interface OnScanListener {
		public void getBarcode(String data);
	}

	private OnScanListener listener;

	// 解码广播
	private String RECE_DATA_ACTION = "com.se4500.onDecodeComplete";
	// 调用扫描广播
	private String START_SCAN_ACTION = "com.geomobile.se4500barcode";
	//停止
	private String STOP_SCAN_ACTION = "com.geomobile.se4500barcodestop";

	/**
	 * 是否为自动扫描
	 */
	private boolean isRepeat = false;
	private Context context;
	private Timer timer = new Timer();

	public class MyTask extends TimerTask {
		@Override
		public void run() {
			startScan();
		}
	}

	public ScanUtil(Context context) {
		this.context = context;
		// 注册系统广播 接受扫描到的数据
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(RECE_DATA_ACTION);
		context.registerReceiver(receiver, iFilter);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context,
				Intent intent) {
			String action = intent.getAction();
			if (action.equals(RECE_DATA_ACTION)) {
				String data = intent.getStringExtra("se4500");
				if (listener != null) {
					data = data.replace("\n\r", "");
					data = data.replace("\n", "");
					data = data.replace("\r", "");
					data = data.replace("\u0000", "");
					listener.getBarcode(data);
				}
//				if (isRepeat) {
//					cancelRepeat();
//					repeatScan();
//				}
			}
		}
	};

	/**
	 * 获取条码监听
	 * @param listener
	 */
	public void setOnScanListener(OnScanListener listener) {
		this.listener = listener;
	}

	/**
	 * 发送广播 调用系统扫描
	 */
	public void startScan() {
//		SystemProperties.set("persist.sys.scanstopimme","false");
		Intent intent = new Intent();
		intent.setAction(START_SCAN_ACTION);
		context.sendBroadcast(intent, null);
	}

	/**
	 * 打开自动扫描
	 */
	public void repeatScan() {
		if (!isRepeat) {
			isRepeat = true;
			if (timer == null) {
				timer = new Timer();
			}
			timer.schedule(new MyTask(), 100, 4 * 1000);
		}

	}

	/**
	 * 关闭自动扫描
	 */
	public void cancelRepeat() {
		if (isRepeat) {
			isRepeat = false;
			if (timer != null) {
				timer.cancel();
				timer = null;
			}
		}
	}

	public void stopScan() {
		Intent intent = new Intent();
		intent.setAction(STOP_SCAN_ACTION);
		context.sendBroadcast(intent);
		context.unregisterReceiver(receiver);
	}

	public void stopReceData(){
		context.unregisterReceiver(receiver);
	}
}
