package com.yuewawa.simpleaccount.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuewawa.simpleaccount.R;
import com.yuewawa.simpleaccount.activity.base.BaseActivity;
import com.yuewawa.simpleaccount.dao.UserDao;
import com.yuewawa.simpleaccount.entity.User;
import com.yuewawa.simpleaccount.util.MD5Util;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yuewawa on 2016-06-07.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";

    private TextView usernameTxt, passwordTxt;
    private Button loginBtn;
    private Intent intent;
    private Bundle bundle;
    private String username, password;
    private UserDao userDao;
    private Context context;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initHeadBar(R.string.login);

        context = LoginActivity.this;
        userDao = new UserDao(context);
        pref = getSharedPreferences("PREF", MODE_PRIVATE);
        initIconFont();
        initViews();
        setViewListener();
    }

    private void initIconFont(){
        Typeface tf = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");
    }

    private void initViews(){
        loginBtn = (Button) findViewById(R.id.login_btn);

        usernameTxt = (TextView) findViewById(R.id.username_edt);
        passwordTxt = (TextView) findViewById(R.id.password_edt);
    }

    private void setViewListener(){
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                username = usernameTxt.getText().toString();
                password = passwordTxt.getText().toString();
                if (username.trim().equals("") || password.trim().equals("")){
                    Toast.makeText(context, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    List<User> users = userDao.get(username);
                    if (users!=null && users.size()>0){
                        User u = users.get(0);
                        Log.i(TAG, "username="+u.getUsername()+", password="+u.getPassword());
                        Log.i(TAG, "input password="+ MD5Util.encryptByMD5(password));
                        if (u.getPassword().equals(MD5Util.encryptByMD5(password))){
                            pref.edit()
                                    .putBoolean("isLogin", true)
                                    .putString("username", u.getUsername())
                                    .putInt("id", u.getId())
                                    .commit();
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            bundle = new Bundle();
                            bundle.putSerializable("user", u);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(context, "用户名不存在", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ic_back:
                goBack(LoginActivity.this, SplashActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        goBack(this, SplashActivity.class);
    }
}
