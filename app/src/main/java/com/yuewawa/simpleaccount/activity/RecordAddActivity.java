package com.yuewawa.simpleaccount.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yuewawa.simpleaccount.R;
import com.yuewawa.simpleaccount.activity.base.BaseActivity;
import com.yuewawa.simpleaccount.adapter.base.BaseListAdapter;
import com.yuewawa.simpleaccount.constant.Constants;
import com.yuewawa.simpleaccount.dao.RecordDao;
import com.yuewawa.simpleaccount.entity.Record;
import com.yuewawa.simpleaccount.entity.RecordCategory;
import com.yuewawa.simpleaccount.entity.User;
import com.yuewawa.simpleaccount.fragment.RecordsFragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yuewawa on 2016-06-15.
 */
public class RecordAddActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener{

    private Context context;
    private EditText rcdNameEdt, rcdAmountEdt, rcdDateEdt, rcdDetailEdt;
    private RadioGroup rcdTypeRG;
    private RadioButton typeInRB, typeOutRB;
    private Spinner rcdCategorySpi;
    private Button rcdAddBtn;
    private String name, date, detail, type, category;
    private double amount;
    private BaseAdapter categoryListAdapter;
    private List<RecordCategory> categories;
    private int rYear, rMonth, rDay;
    private Calendar calendar;
    private RecordDao recordDao;
    private SharedPreferences pref;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_add);
        initHeadBar(R.string.record_add);
        initViews();
        setCategorySpi();

        context = RecordAddActivity.this;
        calendar = Calendar.getInstance();
        recordDao = new RecordDao(context);
        pref = getSharedPreferences("PREF", MODE_PRIVATE);
    }

    private void initViews(){
        rcdNameEdt = (EditText) findViewById(R.id.record_name_edt);
        rcdAmountEdt = (EditText) findViewById(R.id.record_amount_edt);
        rcdDateEdt = (EditText) findViewById(R.id.record_date_edt);
        rcdDetailEdt = (EditText) findViewById(R.id.record_detail_edt);
        rcdTypeRG = (RadioGroup) findViewById(R.id.record_type_rg);
        typeInRB = (RadioButton) findViewById(R.id.type_in_rb);
        typeOutRB = (RadioButton) findViewById(R.id.type_out_rb);
        rcdCategorySpi = (Spinner) findViewById(R.id.record_category_spi);
        rcdAddBtn = (Button) findViewById(R.id.record_add_btn);

        rcdDateEdt.setOnClickListener(this);
        rcdCategorySpi.setOnItemSelectedListener(this);
        rcdAddBtn.setOnClickListener(this);
        rcdTypeRG.setOnCheckedChangeListener(this);
    }

    private void setCategorySpi(){
        categories = new ArrayList<>();
        for (int i = 0; i< Constants.RECORD_CATEGORY_IN.length; i++){
            categories.add(new RecordCategory(Constants.RECORD_CATEGORY_IN[i]));
        }
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (categories!=null){
                    categories.clear();
                }
                switch (msg.what){
                    case 0x001:
                        if ((int)msg.obj==0){
                            for (int i=0; i< Constants.RECORD_CATEGORY_IN.length; i++){
                                categories.add(new RecordCategory(Constants.RECORD_CATEGORY_IN[i]));
                            }
                        }
                        else if ((int)msg.obj==1){
                            for (int i=0; i< Constants.RECORD_CATEGORY_OUT.length; i++){
                                categories.add(new RecordCategory(Constants.RECORD_CATEGORY_OUT[i]));
                            }
                        }
                        categoryListAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };

        categoryListAdapter = new BaseListAdapter<RecordCategory>(categories, R.layout.category_list_item) {
            @Override
            public void bindView(ViewHolder holder, RecordCategory obj) {
                holder.setText(R.id.category_item_txt, obj.getCategoryName());
            }
        };

        rcdCategorySpi.setAdapter(categoryListAdapter);
    }

    private void addRecord(){
        name = rcdNameEdt.getText().toString();
        if (!rcdAmountEdt.getText().toString().trim().equals("") && rcdAmountEdt.getText().toString().trim()!=null){
            amount = Double.parseDouble(rcdAmountEdt.getText().toString());
        }
        else {
            amount = 0;
        }
        date = rcdDateEdt.getText().toString();
        detail = rcdDetailEdt.getText().toString();
        for (int i=0; i<rcdTypeRG.getChildCount(); i++){
            RadioButton radioButton = (RadioButton) rcdTypeRG.getChildAt(i);
            if (radioButton.isChecked()){
                type = radioButton.getText().toString();
            }
        }
        if (name.equals("")||amount==0||date.equals("")||detail.equals("")){
            Toast.makeText(context, "请填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        Record record = new Record();
        User user = new User();
        user.setId(pref.getInt("id", 1));
        record.setName(name);
        record.setAmount(amount);
        record.setRYear(rYear);
        record.setRMonth(rMonth);
        record.setRDay(rDay);
        record.setDetail(detail);
        record.setType(type);
        record.setCategory(category);
        record.setUser(user);

        try {
            recordDao.add(record);
            goBack(context, MainActivity.class);
            Message msg = new Message();
            msg.what = 0x001;
            msg.obj = record;
            RecordsFragment.mHandler.sendMessage(msg);
            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ic_back:
                goBack(RecordAddActivity.this, MainActivity.class);
                break;
            case R.id.record_add_btn:
                addRecord();
                break;
            case R.id.record_date_edt:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        rYear = year;
                        rMonth = monthOfYear+1;
                        rDay = dayOfMonth;
                        StringBuffer date = new StringBuffer();
                        if ((monthOfYear+1)<10){
                            date.append(rYear+"-0"+rMonth);
                        }
                        else {
                            date.append(rYear+"-"+rMonth);
                        }
                        if (dayOfMonth<10){
                            date.append("-0"+rDay);
                        }
                        else {
                            date.append("-"+rDay);
                        }
                        rcdDateEdt.setText(date.toString());
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.record_category_spi:
                TextView txtView = (TextView) view.findViewById(R.id.category_item_txt);
                category = txtView.getText().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Message msg = new Message();
        msg.what = 0x001;
        switch (checkedId){
            case R.id.type_in_rb:
                msg.obj = 0;
                break;
            case R.id.type_out_rb:
                msg.obj = 1;
                break;
        }
        mHandler.sendMessage(msg);
    }
}
