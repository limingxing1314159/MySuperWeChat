package cn.ucai.superwechat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.User;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatHelper;
import cn.ucai.superwechat.bean.Result;
import cn.ucai.superwechat.data.NetDao;
import cn.ucai.superwechat.data.OkHttpUtils;
import cn.ucai.superwechat.utils.MFGT;
import cn.ucai.superwechat.utils.ResultUtils;

/**
 * Created by Administrator on 2016/11/8.
 */
public class FriendProfileActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.findfriend_iv_userAvatar)
    ImageView findfriendIvUserAvatar;
    @BindView(R.id.findfriend_tv_usernick)
    TextView findfriendTvUsernick;
    @BindView(R.id.findfriend_tv_username)
    TextView findfriendTvUsername;
    String username = null;
    User user = null;
    @BindView(R.id.findfriend_btn_add)
    Button findfriendBtnAdd;
    @BindView(R.id.findfriend_btn_sendmessage)
    Button findfriendBtnSendmessage;
    @BindView(R.id.findfriend_btn_videocat)
    Button findfriendBtnVideocat;
    boolean isFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        ButterKnife.bind(this);
        username = getIntent().getStringExtra(I.User.USER_NAME);
        if (username == null) {
            MFGT.finish(this);
            return;
        }
        initView();
        user = SuperWeChatHelper.getInstance().getAppContactList().get(username);
        if (user==null){
            isFriend = false;
        }else {
            setUserInfo();
            isFriend = true;
        }
        isFriend(isFriend);
        syncUserInfo();
    }

    private void syncFail(){
        if (isFriend) {
            MFGT.finish(this);
            return;
        }
    }
    private void syncUserInfo() {
        NetDao.syncUserInfo(this, username, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s!=null){
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result!=null && result.isRetMsg()){
                        user = (User) result.getRetData();
                        if (user!=null){
                            setUserInfo();
                            if (isFriend){
                                SuperWeChatHelper.getInstance().saveAppContact(user);
                            }
                        }else {
                            syncFail();
                        }
                    }else {
                        syncFail();
                    }
                }else {
                    syncFail();
                }
            }

            @Override
            public void onError(String error) {
                syncFail();
            }
        });

    }

    private void initView() {
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(getString(R.string.userinfo_txt_profile));
    }

    public void isFriend(boolean isFriend) {
        if (isFriend) {
            findfriendBtnSendmessage.setVisibility(View.VISIBLE);
            findfriendBtnVideocat.setVisibility(View.VISIBLE);
        } else {
            findfriendBtnAdd.setVisibility(View.VISIBLE);
        }
    }

    private void setUserInfo() {
        EaseUserUtils.setAppUserAvatar(this, user.getMUserName(), findfriendIvUserAvatar);
        EaseUserUtils.setAppUserNick(user.getMUserNick(), findfriendTvUsernick);
        EaseUserUtils.setAppUserNameWithNo(user.getMUserName(), findfriendTvUsername);

    }


    @OnClick({R.id.img_back, R.id.findfriend_btn_add, R.id.findfriend_btn_sendmessage, R.id.findfriend_btn_videocat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                MFGT.finish(this);
                break;
            case R.id.findfriend_btn_add:
                MFGT.gotoAddFriendMsg(this,user.getMUserName());
                break;
            case R.id.findfriend_btn_sendmessage:
                MFGT.gotoChat(this,user.getMUserName());
                break;
            case R.id.findfriend_btn_videocat:
                if (!EMClient.getInstance().isConnected()) {
                    Toast.makeText(this, R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(this, VoiceCallActivity.class).putExtra("username", user.getMUserName())
                            .putExtra("isComingCall", false));
                    // voiceCallBtn.setEnabled(false);
                }
                break;
        }
    }
}
