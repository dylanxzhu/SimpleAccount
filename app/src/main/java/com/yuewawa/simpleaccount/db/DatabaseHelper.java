package com.yuewawa.simpleaccount.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yuewawa.simpleaccount.entity.Record;
import com.yuewawa.simpleaccount.entity.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuewawa on 2016-06-08.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private static final String DB_NAME = "simpleaccount.db";
    private static final int DB_VERSION = 1;
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Record.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Record.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context){
        if (instance==null){
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    public synchronized Dao getEntityDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)){
            dao = daos.get(className);
        }
        if (dao==null){
            try {
                dao = super.getDao(clazz);
            } catch (Exception e){
                e.printStackTrace();
            }
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()){
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
