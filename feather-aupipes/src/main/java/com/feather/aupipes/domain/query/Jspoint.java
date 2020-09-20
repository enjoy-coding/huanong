package com.feather.aupipes.domain.query;

/**
 * @author tql
 * @description 给水管线点 实体类
 * @date 2020年5月8日10:03:43
 */
public class Jspoint {

    private String ysdm;        //要素代码
    private String bmbsm;       //要素标识码
    private String exp_no;      //物探点号
    private String subsid;      //附属物
    private double surf_h;      //地面高程
    private double x;           //x坐标
    private double y;           //y坐标
    private String r_name;      //道路名称
    private double b_depth;     //埋深
    private double pipep_h;     //管点高程
    private String pipep_type;  //管点类型
    private double well_depth;  //井深
    private String pipep_state; //管点状态

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public String getBmbsm() {
        return bmbsm;
    }

    public void setBmbsm(String bmbsm) {
        this.bmbsm = bmbsm;
    }

    public String getExp_no() {
        return exp_no;
    }

    public void setExp_no(String exp_no) {
        this.exp_no = exp_no;
    }

    public String getSubsid() {
        return subsid;
    }

    public void setSubsid(String subsid) {
        this.subsid = subsid;
    }

    public double getSurf_h() {
        return surf_h;
    }

    public void setSurf_h(double surf_h) {
        this.surf_h = surf_h;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public double getB_depth() {
        return b_depth;
    }

    public void setB_depth(double b_depth) {
        this.b_depth = b_depth;
    }

    public double getPipep_h() {
        return pipep_h;
    }

    public void setPipep_h(double pipep_h) {
        this.pipep_h = pipep_h;
    }

    public String getPipep_type() {
        return pipep_type;
    }

    public void setPipep_type(String pipep_type) {
        this.pipep_type = pipep_type;
    }

    public double getWell_depth() {
        return well_depth;
    }

    public void setWell_depth(double well_depth) {
        this.well_depth = well_depth;
    }

    public String getPipep_state() {
        return pipep_state;
    }

    public void setPipep_state(String pipep_state) {
        this.pipep_state = pipep_state;
    }
}
