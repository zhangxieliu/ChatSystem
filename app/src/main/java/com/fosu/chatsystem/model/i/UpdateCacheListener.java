package com.fosu.chatsystem.model.i;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobListener;

/**
 * Created by Administrator on 2017/3/9.
 */

public abstract class UpdateCacheListener extends BmobListener {
    public abstract void done(BmobException e);

    @Override
    protected void postDone(Object o, BmobException e) {
        done(e);
    }
}
