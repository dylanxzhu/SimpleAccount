package com.yuewawa.simpleaccount.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.dao.GenericRawResults;
import com.yuewawa.simpleaccount.dao.RecordDao;
import com.yuewawa.simpleaccount.entity.Record;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuewawa on 2016-06-19.
 */
public class RecordService {

    private Context context;
    private RecordDao recordDao;
    private double totalOfIn = 0, totalOfOut = 0;
    private SharedPreferences pref;

    public RecordService(Context context){
        this.context = context;
        recordDao = new RecordDao(context);
        pref = context.getSharedPreferences("PREF", Context.MODE_PRIVATE);
    }

    @JavascriptInterface
    public String getAmount(){
        String json = "";
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        Gson gson = new Gson();
        try {
            List<Record> records = recordDao.getAllById(pref.getInt("id", 1));
            if (totalOfIn!=0)
                totalOfIn = 0;
            if (totalOfOut!=0)
                totalOfOut = 0;
            if (records!=null && records.size()>0){
                for (int i=0; i<records.size(); i++){
                    if (records.get(i).getType().equals("收入")){
                        totalOfIn = records.get(i).getAmount()+totalOfIn;
                    }
                    else {
                        totalOfOut = records.get(i).getAmount()+totalOfOut;
                    }
                }
            }
            map = new HashMap<>();
            map.put("value", totalOfIn);
            map.put("name", "收入");

            list.add(map);
            map = new HashMap<>();
            map.put("value", totalOfOut);
            map.put("name", "支出");

            list.add(map);
            json = gson.toJson(list);
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @JavascriptInterface
    public String getRMonth(){
        List<Integer> list = new ArrayList<>();
        String json = "";
        Gson gson = new Gson();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        Toast.makeText(context, month+"", Toast.LENGTH_SHORT).show();
        try {
            List<Record> records = recordDao.getRMonth(pref.getInt("id", 1),month);
            if (records!=null && records.size()>0){
                for (int i=0; i<records.size(); i++){
                    int rMonth = records.get(i).getRMonth();
                    list.add(rMonth);
                }
                json = gson.toJson(list);
            }
            Toast.makeText(context, json, Toast.LENGTH_SHORT).show();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @JavascriptInterface
    public String getAmountTotalByType(String type){
        List<Double> list = new ArrayList<>();
        String json = "";
        Gson gson = new Gson();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        try {
            GenericRawResults<String[]> rawResults = recordDao.getAmount(pref.getInt("id", 1), type, month);
            if (rawResults!=null){
                List<String[]> results = rawResults.getResults();
                if (results!=null && results.size()>0){
                    for (int i=0; i<results.size(); i++){
                        String[] result = results.get(i);
                        for (int j=0; j<result.length; j++){
                            list.add(Double.parseDouble(result[j]));
                        }
                    }
                }
            }
            json = gson.toJson(list);
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
