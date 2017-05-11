package speedata.com.eoms.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import speedata.com.eoms.ui.login.LoginActivity;


//开机自启 <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String action_boot = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(action_boot)) {
			Intent ootStartIntent = new Intent(context, LoginActivity.class);
			ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(ootStartIntent);
		}

	}
}
