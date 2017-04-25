package speedata.com.eoms.ui.record;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import speedata.com.eoms.R;
import speedata.com.eoms.mvp.MVPBaseActivity;
import speedata.com.eoms.ui.toup.ToUpFragment;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RecordActivity extends MVPBaseActivity<RecordContract.View, RecordPresenter>
        implements RecordContract.View, View.OnClickListener {

    private FrameLayout frame_main;
    private TextView tv_toup;
    private TextView tv_uped;
    private Button btn_top_left;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_record);
        initView();

        changeToUpImage();
    }

    private void initView() {
        frame_main = (FrameLayout) findViewById(R.id.frame_main);
        tv_toup = (TextView) findViewById(R.id.tv_toup);
        tv_uped = (TextView) findViewById(R.id.tv_uped);

        tv_toup.setOnClickListener(this);
        tv_uped.setOnClickListener(this);
        btn_top_left = (Button) findViewById(R.id.btn_top_left);
        btn_top_left.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("历史记录");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_toup:
                mPresenter.openToUpFragment();
                break;
            case R.id.tv_uped:
                mPresenter.openUpedFragment();
                break;
            case R.id.btn_top_left:
                RecordActivity.this.finish();
                break;
        }
    }

    @Override
    public void changeToUpImage() {
        Drawable blueToUp = getResources().getDrawable(R.drawable.toup_blue);
        blueToUp.setBounds(0, 0, blueToUp.getMinimumWidth(), blueToUp.getMinimumHeight());
        tv_toup.setCompoundDrawables(null, blueToUp, null, null);
        Drawable blackUped = getResources().getDrawable(R.drawable.uped);
        blackUped.setBounds(0, 0, blackUped.getMinimumWidth(), blackUped.getMinimumHeight());
        tv_uped.setCompoundDrawables(null, blackUped, null, null);
        openFragment(new ToUpFragment());

    }

    @Override
    public void changeUpedImage() {
        Drawable blackToUp = getResources().getDrawable(R.drawable.toup);
        blackToUp.setBounds(0, 0, blackToUp.getMinimumWidth(), blackToUp.getMinimumHeight());
        tv_toup.setCompoundDrawables(null, blackToUp, null, null);
        Drawable blueUped = getResources().getDrawable(R.drawable.uped_blue);
        blueUped.setBounds(0, 0, blueUped.getMinimumWidth(), blueUped.getMinimumHeight());
        tv_uped.setCompoundDrawables(null, blueUped, null, null);
    }
}
