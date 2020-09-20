package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回路 房屋 配电房等信息 Create by NieCheng Time 2020/3/25 17:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupHlInfo {

    private int id;

    private String guId;

    private String fwId;

    private String hlId;

    private String pdfId;

    private String bz;

    private String cxId;

    private String jxId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId;
    }

    public String getFwId() {
        return fwId;
    }

    public void setFwId(String fwId) {
        this.fwId = fwId;
    }

    public String getHlId() {
        return hlId;
    }

    public void setHlId(String hlId) {
        this.hlId = hlId;
    }

    public String getPdfId() {
        return pdfId;
    }

    public void setPdfId(String pdfId) {
        this.pdfId = pdfId;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCxId() {
        return cxId;
    }

    public void setCxId(String cxId) {
        this.cxId = cxId;
    }

    public String getJxId() {
        return jxId;
    }

    public void setJxId(String jxId) {
        this.jxId = jxId;
    }

    @Override
    public String toString() {
        return "AupHlInfo{" + "id=" + id + ", guId='" + guId + '\'' + ", fwId=" + fwId + ", hlId='" + hlId + '\''
                + ", pdfId='" + pdfId + '\'' + ", bz='" + bz + '\'' + ", cxId='" + cxId + '\'' + ", jxId='" + jxId
                + '\'' + '}';
    }
}
