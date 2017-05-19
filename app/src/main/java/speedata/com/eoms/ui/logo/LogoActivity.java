package speedata.com.eoms.ui.logo;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import speedata.com.eoms.R;
import speedata.com.eoms.mvp.MVPBaseActivity;
import speedata.com.eoms.ui.login.LoginActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LogoActivity extends MVPBaseActivity<LogoContract.View, LogoPresenter> implements LogoContract.View {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_logo);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(LogoActivity.this, LoginActivity.class);
                startActivity(intent);
                LogoActivity.this.finish();
            }
        },1000);
    }
}
