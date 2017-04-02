package com.example.ethantien.stockview;

/**
 * Created by ethantien on 4/1/17.
 */

public class vars {


    private static final vars _instance = new vars();

    public static vars getInstance() {
        return _instance;
    }

    private vars() {
        curCompany = "";
    }

    private String curCompany;

    public void setCurCompany(String str) {
        curCompany = str;
    }
    public String getCurCompany() {
        return curCompany;
    }

}