package com.yuewawa.simpleaccount.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.yuewawa.simpleaccount.db.DatabaseHelper;
import com.yuewawa.simpleaccount.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yuewawa on 2016-06-08.
 */
public class UserDao {

    private DatabaseHelper helper;
    private Dao<User, Integer> dao;

    public UserDao(Context context){
        helper = DatabaseHelper.getInstance(context);
        try {
            dao = helper.getEntityDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(User user) throws SQLException {
        dao.create(user);
    }

    public List<User> get(String username) throws SQLException {
        return dao.queryForEq("username", username);
    }

}
