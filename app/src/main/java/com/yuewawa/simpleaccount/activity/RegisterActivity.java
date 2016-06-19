package com.yuewawa.simpleaccount.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuewawa.simpleaccount.R;
import com.yuewawa.simpleaccount.activity.base.BaseActivity;
import com.yuewawa.simpleaccount.dao.UserDao;
import com.yuewawa.simpleaccount.entity.User;
import com.yuewawa.simpleaccount.util.FileUtil;
import com.yuewawa.simpleaccount.util.MD5Util;
import com.yuewawa.simpleaccount.util.StringUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yuewawa on 2016-06-07.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText usernameEdt, passwordEdt;
    private ImageView headImg;
    private Button registerBtn;
    private String username, password;
    private byte[] head;
    private Context context;
    private UserDao userDao;
    private String usernameReg = "^[A-Za-z0-9]+$";
    private String passwordReg = "^[\\w@_]{7,12}$"; //8-12位密码
    private boolean hasHeadImg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initHeadBar(R.string.register);
        initIconFont();
        initViews();
        setViewListener();

        context = RegisterActivity.this;
    }

    private void initIconFont(){
        Typeface tf = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");
    }

    private void initViews(){
        usernameEdt = (EditText) findViewById(R.id.username_edt);
        passwordEdt = (EditText) findViewById(R.id.password_edt);
        registerBtn = (Button) findViewById(R.id.register_btn);
        headImg = (ImageView) findViewById(R.id.head_img);
    }

    private void setViewListener(){
        registerBtn.setOnClickListener(this);
        headImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                register();
                break;
            case R.id.ic_back:
                goBack(RegisterActivity.this, SplashActivity.class);
                break;
            case R.id.head_img:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);// 裁剪框比例
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 100);// 输出图片大小
                intent.putExtra("outputY", 100);
                intent.putExtra("return-data", true);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
                startActivityForResult(intent, 200);
                break;
        }
    }

    private void register() {
        userDao = new UserDao(context);
        username = usernameEdt.getText().toString();
        password = passwordEdt.getText().toString();
        if (username.trim().equals("") || password.trim().equals("")){
            Toast.makeText(RegisterActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            List<User> list = userDao.get(username);
            if (list!=null && list.size()>0){
                Toast.makeText(RegisterActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!StringUtil.validateString(username, usernameReg)){
            Toast.makeText(RegisterActivity.this, "字母，数字组合的用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtil.validateString(password, passwordReg)){
            Toast.makeText(RegisterActivity.this, "字母数字下划线组合8-12位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        password = MD5Util.encryptByMD5(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        if (hasHeadImg){
            user.setHead(head);
        }
        else {
            user.setHead(FileUtil.getFileFromAssets(context, "image/default_head.png"));
        }
        try {
            userDao.add(user);
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==200){
            if (resultCode==RESULT_OK){
                Bitmap bitmap = data.getParcelableExtra("data");
                headImg.setImageBitmap(bitmap);
                head = FileUtil.bitmapToByteArray(bitmap);
                hasHeadImg = true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        goBack(this, SplashActivity.class);
    }
}
