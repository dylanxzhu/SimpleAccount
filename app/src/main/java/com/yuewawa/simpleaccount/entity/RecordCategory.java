package com.yuewawa.simpleaccount.entity;

/**
 * Created by yuewawa on 2016-06-15.
 */
public class RecordCategory {

    private String categoryName;

    public RecordCategory(String categoryName){
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
