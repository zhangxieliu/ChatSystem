package com.fosu.chatsystem.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.fosu.chatsystem.R;
import com.fosu.chatsystem.base.BaseActivity;
import com.fosu.chatsystem.bean.User;
import com.fosu.chatsystem.model.UserModel;

/**
 * Created by Administrator on 2017/3/10.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler =new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UserModel.getSingleInstance().logOut();
                User user = UserModel.getSingleInstance().getCurrentUser();
                if (user == null) {
                    startActivity(LoginActivity.class,null,true);
                }else{
                    startActivity(MainActivity.class,null,true);
                }
            }
        },1000);
    }
}
