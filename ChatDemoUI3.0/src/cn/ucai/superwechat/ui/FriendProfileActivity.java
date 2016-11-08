package cn.ucai.superwechat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.User;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
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
    User user =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra(I.User.USER_NAME);
        if (user==null){
            MFGT.finish(this);
        }
        initView();
    }

    private void initView() {
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(getString(R.string.userinfo_txt_profile));
        setUserInfo();
    }

    private void setUserInfo() {
        EaseUserUtils.setAppUserAvatar(this,user.getMUserName(),findfriendIvUserAvatar);
        EaseUserUtils.setAppUserNick(user.getMUserName(),findfriendTvUsernick);
        EaseUserUtils.setAppUserNameWithNo(user.getMUserName(),findfriendTvUsername);

    }

    @OnClick(R.id.img_back)
    public void onClick() {
    }
}
