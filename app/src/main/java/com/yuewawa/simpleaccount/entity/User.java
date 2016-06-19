package com.yuewawa.simpleaccount.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by yuewawa on 2016-06-07.
 */
@DatabaseTable(tableName = "t_user")
public class User implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = true)
    private String username;
    @DatabaseField(canBeNull = true)
    private String password;
    @DatabaseField(canBeNull = true, dataType = DataType.BYTE_ARRAY)
    private byte[] head;

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getHead() {
        return head;
    }

    public void setHead(byte[] head) {
        this.head = head;
    }
}
