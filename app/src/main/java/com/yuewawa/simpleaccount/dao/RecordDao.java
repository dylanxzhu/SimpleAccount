package com.yuewawa.simpleaccount.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.yuewawa.simpleaccount.db.DatabaseHelper;
import com.yuewawa.simpleaccount.entity.Record;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yuewawa on 2016-06-14.
 */
public class RecordDao {

    private DatabaseHelper helper;
    private Dao<Record, Integer> dao;

    public RecordDao(Context context){
        helper = new DatabaseHelper(context);
        try {
            dao = helper.getEntityDao(Record.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Record record) throws SQLException {
        dao.create(record);
    }

    public List<Record> getAll() throws SQLException {
        return dao.queryForAll();
    }

    public List<Record> getAllById(int id) throws SQLException {
        return dao.queryBuilder().where().eq("user_id", id).query();
    }

    public GenericRawResults<String[]> getAmount(int id, String type, int month) throws SQLException {
        return dao.queryRaw("SELECT SUM(amount) FROM t_record WHERE user_id="+id+" and type='"+type+"'" +
                " and rMonth>="+(month-2)+" and rMonth<="+month+
                " GROUP BY rMonth");
    }

    public List<Record> getRMonth(int id, int month) throws SQLException {
        return dao.queryBuilder().selectColumns("rMonth").groupBy("rMonth").where().eq("user_id", id).and().ge("rMonth", month-2).and().le("rMonth", month).query();
    }
}
