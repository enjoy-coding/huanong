package com.feather.aupipes.domain.query;

/**
 * @author tql
 * @description 管线段实体类
 * @date 2019年12月18日17:38:57
 */
public class Line {

    private int oid;                    //主键
    private String e_point;             //连接方向
    private String s_point;             //起点物探点号
    private String diming;              //地名

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getE_point() {
        return e_point;
    }

    public void setE_point(String e_point) {
        this.e_point = e_point;
    }

    public String getS_point() {
        return s_point;
    }

    public void setS_point(String s_point) {
        this.s_point = s_point;
    }

    public String getDiming() {
        return diming;
    }

    public void setDiming(String diming) {
        this.diming = diming;
    }
}
