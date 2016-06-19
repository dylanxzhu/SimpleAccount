package com.yuewawa.simpleaccount.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yuewawa.simpleaccount.R;
import com.yuewawa.simpleaccount.activity.MainActivity;
import com.yuewawa.simpleaccount.activity.RecordAddActivity;
import com.yuewawa.simpleaccount.adapter.RecordListAdapter;
import com.yuewawa.simpleaccount.constant.Constants;
import com.yuewawa.simpleaccount.dao.RecordDao;
import com.yuewawa.simpleaccount.entity.Record;
import com.yuewawa.simpleaccount.fragment.base.BaseFragment;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yuewawa on 2016-06-07.
 */
public class RecordsFragment extends BaseFragment {

    private static final String TAG = "RECORDS_FRAGMENT";
    private View view;
    private TextView icSearch, icAdd, noDataTxt;
    private ListView recordsList;

    private Context context;
    private SharedPreferences pref;
    private RecordDao recordDao;
//    private BaseListAdapter<Record> recordAdapter;
    private RecordListAdapter recordListAdapter;
    private Typeface typeface;
    public static Handler mHandler;
    private List<Record> records;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        view = inflater.inflate(R.layout.fragment_records, container, false);
        context = getActivity();
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "iconfont/iconfont.ttf");
        pref = context.getSharedPreferences("PREF", Context.MODE_PRIVATE);
        recordDao = new RecordDao(context);

        initIconFont();
        initViews();

        records = getAllRecords(pref.getInt("id", 1));
        if (records!=null && records.size()>0){
            noDataTxt.setVisibility(View.GONE);
        }
        recordListAdapter = new RecordListAdapter(records, context);
        recordsList.setAdapter(recordListAdapter);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0x001:
                        recordListAdapter.add((Record) msg.obj);
                        noDataTxt.setVisibility(View.GONE);
                        break;
                }
            }
        };
        return view;
    }

    private void initViews(){
        recordsList = (ListView) view.findViewById(R.id.records_list);
        noDataTxt = (TextView) view.findViewById(R.id.no_data_txt);
    }

    private void initIconFont() {

        icSearch = (TextView) view.findViewById(R.id.ic_search);
        icAdd = (TextView) view.findViewById(R.id.ic_add_txt);

        icSearch.setTypeface(typeface);
        icAdd.setTypeface(typeface);


        icAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecordAddActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Record> getAllRecords(int id){

        try {
            return recordDao.getAllById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        MainActivity.currentTag = Constants.RECORDS_FRAGMENT;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }
}
