package com.fosu.chatsystem.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.fosu.chatsystem.R;
import com.fosu.chatsystem.base.BaseActivity;
import com.fosu.chatsystem.bean.User;
import com.fosu.chatsystem.event.FinishEvent;
import com.fosu.chatsystem.model.BaseModel;
import com.fosu.chatsystem.model.UserModel;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/3/11.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @OnClick(R.id.btn_register)
    public void onClick() {
        UserModel.getSingleInstance().register(etUsername.getText().toString(),
                etPassword.getText().toString(), etPasswordAgain.getText().toString(),
                new SaveListener() {
            @Override
            public void onSuccess() {
                EventBus.getDefault().post(new FinishEvent());
                User user = UserModel.getSingleInstance().getCurrentUser();
                BmobIM.getInstance().updateUserInfo(
                        new BmobIMUserInfo(user.getObjectId(),
                                user.getUsername(), "avatar"));
                startActivity(MainActivity.class, null, true);
            }

            @Override
            public void onFailure(int i, String s) {
                Logger.i("error code:" + i + ", message:" + s);
                if(i== BaseModel.CODE_NOT_EQUAL){
                    etPasswordAgain.setText("");
                }
                toast(s);
            }
        });
    }
}
