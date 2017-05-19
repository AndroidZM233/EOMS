package speedata.com.eoms.ui.aboutus;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import speedata.com.eoms.R;
import speedata.com.eoms.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AboutUsActivity extends MVPBaseActivity<AboutUsContract.View, AboutUsPresenter> implements AboutUsContract.View {
    private ImageView aboutus_back_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_aboutus);
        initView();
    }

    private void initView() {
        aboutus_back_btn = (ImageView) findViewById(R.id.aboutus_back_btn);
        aboutus_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
