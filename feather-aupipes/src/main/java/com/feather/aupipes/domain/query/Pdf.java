package com.feather.aupipes.domain.query;

/**
 * @author tql
 * @description 配电房信息实体类
 * @date 2020年2月13日11:02:50
 */
public class Pdf {

    private String pdfid;           //配电房id
    private String mc;              //名称
    private String lxbh;            //类型编号
    private String lx;              //类型
    private String sjbs;            //上级标识
    private String sbqk;            //设备情况
    private double x;               //经度
    private double y;               //纬度
    private double z;               //高程
    private String bz;              //备注
    private String cxid;            //配电房出线id

    public String getPdfid() {
        return pdfid;
    }

    public void setPdfid(String pdfid) {
        this.pdfid = pdfid;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getLxbh() {
        return lxbh;
    }

    public void setLxbh(String lxbh) {
        this.lxbh = lxbh;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getSjbs() {
        return sjbs;
    }

    public void setSjbs(String sjbs) {
        this.sjbs = sjbs;
    }

    public String getSbqk() {
        return sbqk;
    }

    public void setSbqk(String sbqk) {
        this.sbqk = sbqk;
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCxid() {
        return cxid;
    }

    public void setCxid(String cxid) {
        this.cxid = cxid;
    }
}
