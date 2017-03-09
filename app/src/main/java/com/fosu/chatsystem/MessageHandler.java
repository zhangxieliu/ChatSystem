package com.fosu.chatsystem;

import android.content.Context;

import com.orhanobut.logger.Logger;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by Administrator on 2017/3/9.
 */

public class MessageHandler extends BmobIMMessageHandler {
    private Context mContext;

    public MessageHandler(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onMessageReceive(final MessageEvent event) {
        //当接收到服务器发来的消息时，此方法被调用
        Logger.i(event.getConversation().getConversationTitle() + "," + event.getMessage().getMsgType() + "," + event.getMessage().getContent());
        excuteMessage(event);
    }

    /**
     * 处理消息
     * @param event
     */
    private void excuteMessage(MessageEvent event) {

    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
    }
}
