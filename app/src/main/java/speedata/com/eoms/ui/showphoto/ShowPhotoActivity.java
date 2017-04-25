package speedata.com.eoms.ui.showphoto;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import speedata.com.eoms.R;
import speedata.com.eoms.adapter.MyPageAdapter;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ShowPhotoActivity extends MVPBaseActivity<ShowPhotoContract.View, ShowPhotoPresenter>
        implements ShowPhotoContract.View, View.OnClickListener {

    private ViewPager viewpager;
    private Button photo_bt_exit;
    private Button photo_bt_del;
    private RelativeLayout photo_relativeLayout;
    private String orderNumber = null;
    private ArrayList<View> listViews = null;
    private MyPageAdapter adapter;
    private int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_photo);
        initView();
    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
        photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
        photo_relativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);

        photo_bt_exit.setOnClickListener(this);
        photo_bt_del.setOnClickListener(this);
        photo_relativeLayout.setBackgroundColor(0x70000000);

        Intent intent = getIntent();
        if (intent != null) {
            orderNumber = intent.getStringExtra("orderNumber");
        }

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setOnPageChangeListener(pageChangeListener);
        for (int i = 0; i < MyApplication.getInstance().selectBitmap.size(); i++) {
            initListViews(MyApplication.getInstance().selectBitmap.get(i).getBitmap(),
                    MyApplication.getInstance().selectBitmap.get(i).getImagePath());
        }

        adapter = new MyPageAdapter(listViews);
        viewpager.setAdapter(adapter);
        int id = intent.getIntExtra("ID", 0);
        viewpager.setCurrentItem(id);
    }

    private void initListViews(Bitmap bm, String path) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        ImageView img = new ImageView(this);
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        img.setTag(path);
        listViews.add(img);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_bt_exit:
                finishAct();
                break;
            case R.id.photo_bt_del:
                mPresenter.delPhoto(orderNumber,listViews,count);
                break;
        }
    }

    @Override
    public void finishAct() {
        ShowPhotoActivity.this.finish();
    }

    @Override
    public void notifyDataSetChanged(ArrayList<android.view.View> listViews) {
        viewpager.removeAllViews();
        adapter.setListViews(listViews);
        adapter.notifyDataSetChanged();
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            count = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };
}
