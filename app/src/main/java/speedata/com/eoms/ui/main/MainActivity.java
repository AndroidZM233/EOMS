package speedata.com.eoms.ui.main;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import speedata.com.eoms.R;
import speedata.com.eoms.mvp.MVPBaseActivity;
import speedata.com.eoms.ui.check.CheckFragment;
import speedata.com.eoms.ui.menu.MenuFragment;
import speedata.com.eoms.ui.repair.RepairFragment;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter>
        implements MainContract.View, View.OnClickListener {

    private TextView tv_check;
    private TextView tv_repair;
    private TextView tv_menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        initView();
        changeTvCheckImage();
    }

    private void initView() {
        tv_check = (TextView) findViewById(R.id.tv_check);
        tv_repair = (TextView) findViewById(R.id.tv_repair);
        tv_menu = (TextView) findViewById(R.id.tv_menu);

        tv_check.setOnClickListener(this);
        tv_repair.setOnClickListener(this);
        tv_menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_check:
                mPresenter.openCheckFragment();
                break;
            case R.id.tv_repair:
                mPresenter.openRepairFragment();
                break;
            case R.id.tv_menu:
                mPresenter.openMenuFragment();
                break;
        }
    }

    @Override
    public void changeTvCheckImage() {
//        Drawable blueCheck = getResources().getDrawable(R.drawable.check_blue);
//        blueCheck.setBounds(0, 0, blueCheck.getMinimumWidth(), blueCheck.getMinimumHeight());
//        tv_check.setCompoundDrawables(null, blueCheck, null, null);

        Drawable redCheck = getResources().getDrawable(R.drawable.check_red);
        redCheck.setBounds(0, 0, redCheck.getMinimumWidth(), redCheck.getMinimumHeight());
        tv_check.setCompoundDrawables(null, redCheck, null, null);
        Drawable blackRepair = getResources().getDrawable(R.drawable.repair_black);
        blackRepair.setBounds(0, 0, blackRepair.getMinimumWidth(), blackRepair.getMinimumHeight());
        tv_repair.setCompoundDrawables(null, blackRepair, null, null);
        Drawable blackMenu = getResources().getDrawable(R.drawable.menu_black);
        blackMenu.setBounds(0, 0, blackMenu.getMinimumWidth(), blackMenu.getMinimumHeight());
        tv_menu.setCompoundDrawables(null, blackMenu, null, null);

        closeFragment();
        openFragment(new CheckFragment());
    }

    @Override
    public void changeTvRepairImage() {
        Drawable blackCheck = getResources().getDrawable(R.drawable.check_balck);
        blackCheck.setBounds(0, 0, blackCheck.getMinimumWidth(), blackCheck.getMinimumHeight());
        tv_check.setCompoundDrawables(null, blackCheck, null, null);
//        Drawable blueRepair = getResources().getDrawable(R.drawable.repair_blue);
//        blueRepair.setBounds(0, 0, blueRepair.getMinimumWidth(), blueRepair.getMinimumHeight());
//        tv_repair.setCompoundDrawables(null, blueRepair, null, null);
        Drawable redRepair = getResources().getDrawable(R.drawable.repair_red);
        redRepair.setBounds(0, 0, redRepair.getMinimumWidth(), redRepair.getMinimumHeight());
        tv_repair.setCompoundDrawables(null, redRepair, null, null);
        Drawable blackMenu = getResources().getDrawable(R.drawable.menu_black);
        blackMenu.setBounds(0, 0, blackMenu.getMinimumWidth(), blackMenu.getMinimumHeight());
        tv_menu.setCompoundDrawables(null, blackMenu, null, null);

        closeFragment();
        openFragment(new RepairFragment());
    }

    @Override
    public void changeTvMenuImage() {
        Drawable blackCheck = getResources().getDrawable(R.drawable.check_balck);
        blackCheck.setBounds(0, 0, blackCheck.getMinimumWidth(), blackCheck.getMinimumHeight());
        tv_check.setCompoundDrawables(null, blackCheck, null, null);
        Drawable blackRepair = getResources().getDrawable(R.drawable.repair_black);
        blackRepair.setBounds(0, 0, blackRepair.getMinimumWidth(), blackRepair.getMinimumHeight());
        tv_repair.setCompoundDrawables(null, blackRepair, null, null);
//        Drawable blueMenu = getResources().getDrawable(R.drawable.menu_blue);
//        blueMenu.setBounds(0, 0, blueMenu.getMinimumWidth(), blueMenu.getMinimumHeight());
//        tv_menu.setCompoundDrawables(null, blueMenu, null, null);
        Drawable redMenu = getResources().getDrawable(R.drawable.menu_red);
        redMenu.setBounds(0, 0, redMenu.getMinimumWidth(), redMenu.getMinimumHeight());
        tv_menu.setCompoundDrawables(null, redMenu, null, null);

        closeFragment();
        openFragment(new MenuFragment());
    }

}
