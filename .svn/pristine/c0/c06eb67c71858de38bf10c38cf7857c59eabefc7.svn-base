package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private String name;
    private String no;
    private String address;
    private String code;
    private String msg;
    private Object con;
    private int meterCount;//表具数
    private int onLineCount;//在线数
    private int lineCount;//离线数
    private double readNumber;//读数

    public Position(String name, String no, String address, String code) {
        this.name = name;
        this.no = no;
        this.address = address;
        this.code = code;
    }

    public Position(String msg, Object con) {
        this.msg = msg;
        this.con = con;
    }
}
