package com.fosu.chatsystem.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fosu.chatsystem.R;

/**
 * Created by Administrator on 2017/3/10.
 */

public class ChatMessageList extends RelativeLayout {
    protected Context context;
    protected static final String TAG = "EaseChatMessageList";
    protected ListView listView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected boolean showUserNick;
    protected boolean showAvatar;
    protected Drawable myBubbleBg;
    protected Drawable otherBuddleBg;


    public ChatMessageList(Context context) {
        super(context);
        init(context);
    }

    public ChatMessageList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatMessageList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        parseStyle(context, attrs);
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.chatMessageList);
        showAvatar = ta.getBoolean(R.styleable.chatMessageList_isShowUserAvatar, true);
        myBubbleBg = ta.getDrawable(R.styleable.chatMessageList_myBubbleBackground);
        otherBuddleBg = ta.getDrawable(R.styleable.chatMessageList_otherBubbleBackground);
        showUserNick = ta.getBoolean(R.styleable.chatMessageList_isShowUserNick, false);
        ta.recycle();
    }

    private void init(Context context){
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.custom_pull_refresh_list_view, this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.chat_swipe_layout);
        listView = (ListView) findViewById(R.id.list);
    }

    /**
     * refresh
     */
    public void refresh(){
//        if (messageAdapter != null) {
//            messageAdapter.refresh();
//        }
    }

    /**
     * refresh and jump to the last
     */
    public void refreshSelectLast(){
//        if (messageAdapter != null) {
//            messageAdapter.refreshSelectLast();
//        }
    }

    /**
     * refresh and jump to the position
     * @param position
     */
    public void refreshSeekTo(int position){
//        if (messageAdapter != null) {
//            messageAdapter.refreshSeekTo(position);
//        }
    }

    public ListView getListView() {
        return listView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout(){
        return swipeRefreshLayout;
    }
}
