package com.bpj.rxandroid.rxbinding;



/**
 * Created by Ray on 2018/3/12 .
 */

public class SearchResultBean {


    public  String msg;

    public SearchResultBean(String msg){
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "msg=" + msg+":";
    }
}
