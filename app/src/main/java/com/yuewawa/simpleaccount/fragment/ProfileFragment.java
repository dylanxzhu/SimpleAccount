package com.yuewawa.simpleaccount.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.yuewawa.simpleaccount.R;
import com.yuewawa.simpleaccount.activity.MainActivity;
import com.yuewawa.simpleaccount.activity.SplashActivity;
import com.yuewawa.simpleaccount.constant.Constants;
import com.yuewawa.simpleaccount.dao.UserDao;
import com.yuewawa.simpleaccount.entity.User;
import com.yuewawa.simpleaccount.fragment.base.BaseFragment;
import com.yuewawa.simpleaccount.util.FileUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yuewawa on 2016-06-07.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = "ProfileFragment";
    private Context context;
    private View view;

    private TextView icQRCode, icSetting, icAbout;
    private TextView usernameTxt, settingTxt, aboutTxt;
    private ImageView headImg;
    private Button logoutBtn;
    private SharedPreferences pref;
    private UserDao userDao;
    private String username;
    private User user;

    private AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getActivity();
        pref = context.getSharedPreferences("PREF", Context.MODE_PRIVATE);
        userDao = new UserDao(context);

        initIconFont();
        initViews();

        username = pref.getString("username", "");
        usernameTxt.setText(username);

        try {
            List<User> users = userDao.get(username);
            if (users!=null && users.size()>0){
                user = users.get(0);
                Bitmap bitmap = FileUtil.byteArrayToBitmap(user.getHead());
                headImg.setImageBitmap(bitmap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setViewListener();
        return view;
    }

    private void initIconFont(){
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "iconfont/iconfont.ttf");
        icQRCode = (TextView) view.findViewById(R.id.ic_qr_code);
        icSetting = (TextView) view.findViewById(R.id.ic_setting);
        icAbout = (TextView) view.findViewById(R.id.ic_about);

        icQRCode.setTypeface(typeface);
        icSetting.setTypeface(typeface);
        icAbout.setTypeface(typeface);
    }

    private void initViews(){
        logoutBtn = (Button) view.findViewById(R.id.logout_btn);
        usernameTxt = (TextView) view.findViewById(R.id.username_txt);
        headImg = (ImageView) view.findViewById(R.id.head_img);

        settingTxt = (TextView) view.findViewById(R.id.setting_txt);
        aboutTxt = (TextView) view.findViewById(R.id.about_txt);
    }

    private void setViewListener(){
        logoutBtn.setOnClickListener(this);
        icQRCode.setOnClickListener(this);
        settingTxt.setOnClickListener(this);
        aboutTxt.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.currentTag = Constants.PROFILE_FRAGMENT;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout_btn:
                pref.edit().putBoolean("isLogin", false).commit();
                Intent intent = new Intent(context, SplashActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.ic_qr_code:
                builder = new AlertDialog.Builder(context);
                builder.setTitle("提示")
                        .setMessage("功能开发中...")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.setting_txt:
                builder = new AlertDialog.Builder(context);
                builder.setTitle("提示")
                        .setMessage("功能开发中...")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.about_txt:
                builder = new AlertDialog.Builder(context);
                builder.setTitle("关于")
                        .setMessage("小账本\r\n开发者：yuewawa\r\n版本：v1.0-beta")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
        }
    }
}
