package speedata.com.eoms.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import speedata.com.eoms.R;
import speedata.com.eoms.mvp.MVPBaseActivity;
import speedata.com.eoms.ui.main.MainActivity;
import speedata.com.eoms.utils.BanBottomBarUtils;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter>
        implements LoginContract.View, View.OnClickListener {

    private EditText tel_et;
    private ImageView tel_et_clearbtn;
    private EditText pwd_et;
    private ImageView pwd_et_clearbtn;
    private Button login_btn;
    private Button exit_btn;
    private String et_str;
    private String pwd_str;
    private TextView app_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BanBottomBarUtils.back(false,getApplicationContext());
        BanBottomBarUtils.home(false,getApplicationContext());
        BanBottomBarUtils.recent(false,getApplicationContext());
        BanBottomBarUtils.upmenu(false,getApplicationContext());
        pwd_et.setText("");
    }

    private void initView() {
        app_name= (TextView) findViewById(R.id.app_name);
        tel_et = (EditText) findViewById(R.id.tel_et);
        tel_et_clearbtn = (ImageView) findViewById(R.id.tel_et_clearbtn);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        pwd_et_clearbtn = (ImageView) findViewById(R.id.pwd_et_clearbtn);
        login_btn = (Button) findViewById(R.id.login_btn);
        exit_btn = (Button) findViewById(R.id.exit_btn);

        app_name.getBackground().setAlpha(80);
        login_btn.setOnClickListener(this);
        exit_btn.setOnClickListener(this);
        setClearBtnListener(tel_et,tel_et_clearbtn);
        setClearBtnListener(pwd_et,pwd_et_clearbtn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                submit();
                mPresenter.login(et_str,pwd_str);
                break;
            case R.id.exit_btn:
                mPresenter.exit();
                break;
        }
    }

    private void submit() {
        // validate
        et_str = tel_et.getText().toString().trim();
        if (TextUtils.isEmpty(et_str)) {
            showToast("账号不能为空");
            return;
        }

        pwd_str = pwd_et.getText().toString().trim();
        if (TextUtils.isEmpty(pwd_str)) {
            showToast("密码不能为空");
            return;
       }
    }

    @Override
    public void loginSuccess() {
        showToast("登陆成功");
        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginUserFailed() {
        showToast("用户名错误");
    }

    @Override
    public void loginPwdFailed() {
        showToast("密码错误");
    }
}
