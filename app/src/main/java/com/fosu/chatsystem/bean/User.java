package com.fosu.chatsystem.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/3/9.
 */

public class User extends BmobUser {
    private BmobFile HeadPortrait;

    public BmobFile getHeadPortrait() {
        return HeadPortrait;
    }

    public void setHeadPortrait(BmobFile headPortrait) {
        HeadPortrait = headPortrait;
    }
}
