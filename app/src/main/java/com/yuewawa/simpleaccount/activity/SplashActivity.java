package com.yuewawa.simpleaccount.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.yuewawa.simpleaccount.R;

/**
 * Created by yuewawa on 2016-06-15.
 */
public class SplashActivity extends Activity implements View.OnClickListener{

    private SharedPreferences pref;
    private Intent intent;
    private Button goLoginBtn, goRegisterBtn;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pref = getSharedPreferences("PREF", MODE_PRIVATE);
        flag = pref.getBoolean("isLogin", false);
        if (flag){
            isLogin();
        }
        else {
            setContentView(R.layout.splash);
            initViews();
        }
    }

    private void isLogin() {
        intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initViews(){
        goLoginBtn = (Button) findViewById(R.id.go_login_btn);
        goRegisterBtn = (Button) findViewById(R.id.go_register_btn);

        goLoginBtn.setOnClickListener(this);
        goRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_login_btn:
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.go_register_btn:
                intent = new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
