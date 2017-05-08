package speedata.com.eoms.ui.changepwd;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import speedata.com.eoms.R;
import speedata.com.eoms.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ChangePwdActivity extends MVPBaseActivity<ChangePwdContract.View, ChangePwdPresenter> implements ChangePwdContract.View, View.OnClickListener {
    private Button btn_top_left;
    private TextView tv_title;
    private EditText old_pwd;
    private EditText new_pwd;
    private Button btn_commit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_changepwd);
        initView();
    }

    private void initView() {
        btn_top_left = (Button) findViewById(R.id.btn_top_left);
        tv_title = (TextView) findViewById(R.id.tv_title);
        old_pwd = (EditText) findViewById(R.id.old_pwd);
        new_pwd = (EditText) findViewById(R.id.new_pwd);
        btn_commit = (Button) findViewById(R.id.btn_commit);

        btn_top_left.setOnClickListener(this);
        btn_commit.setOnClickListener(this);

        tv_title.setText("修改密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_top_left:
                ChangePwdActivity.this.finish();
                break;
            case R.id.btn_commit:
                submit();
                mPresenter.changePwd(old_pwd.getText().toString(),new_pwd.getText().toString());
                //隐藏软键盘
                InputMethodManager imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btn_commit.getWindowToken(),0);
                break;
        }
    }

    private void submit() {
        // validate
        String oldPwd = old_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)) {
            Toast.makeText(this, "原密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String newPwd = new_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(newPwd)) {
            Toast.makeText(this, "新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void changeFailed(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeSuccess(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        finish();
    }
}
