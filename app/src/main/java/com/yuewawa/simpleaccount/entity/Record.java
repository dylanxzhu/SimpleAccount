package com.yuewawa.simpleaccount.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 消费记录类
 * Created by yuewawa on 2016-06-14.
 */
@DatabaseTable(tableName = "t_record")
public class Record {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = true)
    private String name;
    @DatabaseField(canBeNull = true)
    private double amount;
    @DatabaseField(canBeNull = true)
    private String date;
    @DatabaseField(canBeNull = true)
    private String type;
    @DatabaseField(canBeNull = true)
    private String category;
    @DatabaseField(canBeNull = true)
    private String detail;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    private User user;

    public Record(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
