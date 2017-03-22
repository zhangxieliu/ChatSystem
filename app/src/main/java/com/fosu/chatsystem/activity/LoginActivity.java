package com.fosu.chatsystem.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fosu.chatsystem.R;
import com.fosu.chatsystem.base.BaseActivity;
import com.fosu.chatsystem.bean.User;
import com.fosu.chatsystem.event.FinishEvent;
import com.fosu.chatsystem.model.UserModel;
import com.fosu.chatsystem.widget.ShareBottomDialog;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;
import me.shaohui.shareutil.LoginUtil;
import me.shaohui.shareutil.login.LoginListener;
import me.shaohui.shareutil.login.LoginPlatform;
import me.shaohui.shareutil.login.LoginResult;

/**
 * Created by Administrator on 2017/3/11.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.text_password)
    TextInputLayout textPassword;
    @BindView(R.id.text_username)
    TextInputLayout textUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Bmob.initialize(this, "53cd5abd1c22bcf54c7f7042ecd26731");
    }

    @Subscribe
    public void onEventMainThread(FinishEvent event) {
        finish();
    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onClick(View view) {
        final String username = etUsername.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            textUsername.setError("用户名不能为空");
        }
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            textPassword.setError("用户密码不能为空");
        }
        switch (view.getId()) {
            case R.id.btn_login:
                ShareBottomDialog dialog = new ShareBottomDialog();
                dialog.show(getSupportFragmentManager());
//                UserModel.getSingleInstance().login(username, password, new SaveListener() {
//                    @Override
//                    public void onSuccess() {
//                        User user = UserModel.getSingleInstance().getCurrentUser();
//                        BmobIM.getInstance().updateUserInfo(
//                                new BmobIMUserInfo(user.getObjectId(),
//                                        username, "avatar"));
//                        startActivity(MainActivity.class, null, true);
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        Logger.e("failure code:" + i + ", Message:" + s);
//                        toast(s);
//                    }
//                });
                break;
            case R.id.tv_register:
//                startActivity(RegisterActivity.class, null, false);

                final LoginListener listener = new LoginListener() {
                    @Override
                    public void loginSuccess(LoginResult result) {
                        //登录成功， 如果你选择了获取用户信息，可以通过
                        Logger.i(result.getUserInfo().getNickname() + "===" +
                        result.getUserInfo().getHeadImageUrlLarge());
                    }

                    @Override
                    public void loginFailure(Exception e) {
                        Logger.i("TAG", "登录失败");
                    }

                    @Override
                    public void loginCancel() {
                        Logger.i("TAG", "登录取消");
                    }
                };
                LoginUtil.login(LoginActivity.this, LoginPlatform.QQ,
                        listener, true);
                break;
        }
    }
}
