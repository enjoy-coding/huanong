package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UseMeter {
    private String name;
    private String codeName;
    private String totalNum;//总用量
    private String totalName;
    private String currtNum;//月用量
    private String percent;
    private int value;
    private String quantificat;
    private String ultimoNum;//上月用量
    private String hascount;//所辖水电表
    private String readtime;//抄表时间
    private String sj;

    private String address;
    private String code;

    private String msg;
    private Object con;

    public UseMeter(String totalNum, String currtNum, String percent) {
        this.totalNum = totalNum;
        this.currtNum = currtNum;
        this.percent = percent;
    }

    public UseMeter(String totalNum, String quantificat, String totalName, String currtNum, String percent,String sj,String ultimoNum,String hascount,String readtime,String code) {
        this.totalNum = totalNum;
        this.quantificat = quantificat;
        this.totalName = totalName;
        this.currtNum = currtNum;
        this.percent = percent;
        this.ultimoNum = ultimoNum;
        this.sj = sj;
        this.hascount = hascount;
        this.readtime = readtime;
        this.code = code;
    }

    public UseMeter(String totalNum, String currtNum) {
        this.totalNum = totalNum;
        this.currtNum = currtNum;
    }

    public UseMeter(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public UseMeter(String totalNum) {
        this.totalNum = totalNum;
    }


    public UseMeter(String msg, Object con,String code,String totalNum,String currtNum) {
        this.msg = msg;
        this.con = con;
    }

    public UseMeter(String name, String code,String totalNum,String currtNum) {
        this.name = name;
        this.code = code;
        this.totalNum = totalNum;
        this.currtNum = currtNum;
    }

    public UseMeter(String totalNum, String currtNum,String ultimoNum,String hascount,String readtime,String code,String name) {

        this.totalNum = totalNum;
        this.currtNum = currtNum;
        this.ultimoNum = ultimoNum;
        this.hascount = hascount;
        this.readtime = readtime;
        this.code = code;
        this.name = name;
    }
}
