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
import cn.ucai.superwechat.utils.MFGT;

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
    User user = null;
    @BindView(R.id.findfriend_btn_add)
    Button findfriendBtnAdd;
    @BindView(R.id.findfriend_btn_sendmessage)
    Button findfriendBtnSendmessage;
    @BindView(R.id.findfriend_btn_videocat)
    Button findfriendBtnVideocat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra(I.User.USER_NAME);
        if (user == null) {
            MFGT.finish(this);
            return;
        }
        initView();
    }

    private void initView() {
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(getString(R.string.userinfo_txt_profile));
        setUserInfo();
        isFriend();
    }

    public void isFriend() {
        if (SuperWeChatHelper.getInstance().getAppContactList().containsKey(user.getMUserName())) {
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
