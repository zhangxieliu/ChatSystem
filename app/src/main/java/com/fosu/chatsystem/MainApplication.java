package com.fosu.chatsystem;

import android.app.Application;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import me.shaohui.shareutil.ShareConfig;
import me.shaohui.shareutil.ShareManager;

/**
 * Created by Administrator on 2017/3/9.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("ChatSystem");
        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            //im初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new MessageHandler(this));
        }

        ShareConfig config = ShareConfig.instance()
                .qqId("1106058290");
                // 下面两个，如果不需要登录功能，可不填写
        ShareManager.init(config);
    }

    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
