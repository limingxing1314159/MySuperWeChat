package cn.ucai.superwechat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.ucai.superwechat.R;
import cn.ucai.superwechat.ui.AddContactActivity;
import cn.ucai.superwechat.ui.LoginActivity;
import cn.ucai.superwechat.ui.RegisterActivity;
import cn.ucai.superwechat.ui.SettingsActivity;
import cn.ucai.superwechat.ui.UserProfileActivity;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context,intent);
    }

    public static void startActivity(Context context,Intent intent){
       context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_bottom_out);
    }

    public static void startActivityForResult(Activity context,Intent intent,int requestCode){
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_bottom_out);
    }
//    修改SettingsFragment为SettingsActivity
    public static void gotoLogin(Activity context){
        startActivity(context,LoginActivity.class);
    }

    public static void gotoSettings(Activity context){
        startActivity(context, SettingsActivity.class);
    }

    public static void gotoUserProfile(Activity context){
        startActivity(context, UserProfileActivity.class);
    }
//    注册
    public static void gotoRegister(Activity context){
        startActivity(context,RegisterActivity.class);
    }

    public static void gotoAddFirent(Activity context){
        startActivity(context,AddContactActivity.class);
    }
}
