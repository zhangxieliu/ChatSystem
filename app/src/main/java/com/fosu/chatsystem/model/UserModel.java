package com.fosu.chatsystem.model;

import android.text.TextUtils;

import com.fosu.chatsystem.bean.Friend;
import com.fosu.chatsystem.bean.User;
import com.fosu.chatsystem.model.i.UpdateCacheListener;
import com.orhanobut.logger.Logger;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.newim.core.BmobIMClient.getContext;

/**
 * Created by Administrator on 2017/3/9.
 */

public class UserModel extends BaseModel {
    private static UserModel userModel = null;

    private UserModel() {}

    public static UserModel getSingleInstance() {
        if (userModel == null) {
            userModel = new UserModel();
        }
        return userModel;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param listener
     */
    public void login(String username, String password, SaveListener listener) {
        if (TextUtils.isEmpty(username)) {
            listener.postOnFailure(CODE_NULL, "请填写用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.postOnFailure(CODE_NULL, "请填写密码");
            return;
        }
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(getContext(), listener);
    }

    public User getCurrentUser() {
        return BmobUser.getCurrentUser(getContext(), User.class);
    }

    /**
     * 退出登录
     */
    public void logOut() {
        BmobUser.logOut(getContext());
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param pwdagain
     * @param listener
     */
    public void register(String username, String password, String pwdagain, SaveListener listener) {
        if (TextUtils.isEmpty(username)) {
            listener.postOnFailure(CODE_NULL, "请填写用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.postOnFailure(CODE_NULL, "请填写密码");
            return;
        }
        if (TextUtils.isEmpty(pwdagain)) {
            listener.postOnFailure(CODE_NULL, "请填写确认密码");
            return;
        }
        if (!password.equals(pwdagain)) {
            listener.postOnFailure(CODE_NOT_EQUAL, "两次输入的密码不一致，请重新输入");
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        user.signUp(getContext(), listener);
    }

    /**
     * 查询用户
     *
     * @param username
     * @param limit
     * @param listener
     */
    public void queryUsers(String username, int limit, final FindListener<User> listener) {
        BmobQuery<User> query = new BmobQuery<>();
        //去掉当前用户
        try {
            BmobUser user = BmobUser.getCurrentUser(getContext());
            query.addWhereNotEqualTo("username", user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        query.addWhereContains("username", username);
        query.setLimit(limit);
        query.order("-createdAt");
        query.findObjects(getContext(), new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list != null && list.size() > 0) {
                    listener.onSuccess(list);
                } else {
                    listener.onError(CODE_NULL, "查无此人");
                }
            }

            @Override
            public void onError(int i, String s) {
                listener.onError(i, s);
            }
        });
    }

    /**
     * 查询用户信息
     *
     * @param objectId
     * @param listener
     */
    public void queryUserInfo(String objectId, final GetListener<User> listener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.getObject(getContext(), objectId, listener);
    }

    /**
     * 更新用户资料和会话资料
     *
     * @param event
     * @param listener
     */
    public void updateUserInfo(MessageEvent event, final UpdateCacheListener listener) {
        final BmobIMConversation conversation = event.getConversation();
        final BmobIMUserInfo info = event.getFromUserInfo();
        final BmobIMMessage msg = event.getMessage();
        String username = info.getName();
        String title = conversation.getConversationTitle();
        Logger.i("username:" + username + ", title:" + title);
        //sdk内部，将新会话的会话标题用objectId表示，因此需要比对用户名和会话标题--单聊，后续会根据会话类型进行判断
        if (!username.equals(title)) {
            this.queryUserInfo(info.getUserId(), new GetListener<User>() {
                @Override
                public void onSuccess(User user) {
                    String name =user.getUsername();
                    String avatar = user.getAvatar().getUrl();
                    Logger.i("query success："+name+","+avatar);
                    conversation.setConversationIcon(avatar);
                    conversation.setConversationTitle(name);
                    info.setName(name);
                    info.setAvatar(avatar);
                    //更新用户资料
                    BmobIM.getInstance().updateUserInfo(info);
                    //更新会话资料-如果消息是暂态消息，则不更新会话资料
                    if(!msg.isTransient()){
                        BmobIM.getInstance().updateConversation(conversation);
                    }
                }

                @Override
                public void onFailure(int i, String s) {
                    Logger.e("error code:" + i + ",message:" + s);
                    listener.done(new BmobException(000, "查无此人"));
                }
            });
        } else {
            listener.internalDone(null);
        }
    }

    /**
     * 同意添加好友：1、发送同意添加的请求，2、添加对方到自己的好友列表中
     */
    public void agreeAddFriend(User friend, SaveListener listener) {
        Friend f = new Friend();
        User user = BmobUser.getCurrentUser(getContext(), User.class);
        f.setUser(user);
        f.setFriendUser(friend);
        f.save(getContext(), listener);
    }

    /**
     * 查询好友
     *
     * @param listener
     */
    public void queryFriends(final FindListener<Friend> listener) {
        BmobQuery<Friend> query = new BmobQuery<>();
        User user = BmobUser.getCurrentUser(getContext(), User.class);
        query.addWhereEqualTo("user", user);
        query.include("friendUser");
        query.order("-updatedAt");
        query.findObjects(getContext(), new FindListener<Friend>() {
            @Override
            public void onSuccess(List<Friend> list) {
                if (list != null && list.size() > 0) {
                    listener.onSuccess(list);
                } else {
                    listener.onError(0, "暂无联系人");
                }
            }

            @Override
            public void onError(int i, String s) {
                listener.onError(i, s);
            }
        });
    }

    /**
     * 删除好友
     *
     * @param f
     * @param listener
     */
    public void deleteFriend(Friend f, DeleteListener listener) {
        Friend friend = new Friend();
        friend.delete(getContext(), f.getObjectId(), listener);
    }
}
