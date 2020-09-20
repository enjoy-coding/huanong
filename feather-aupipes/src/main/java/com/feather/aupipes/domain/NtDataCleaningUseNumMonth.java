package com.feather.aupipes.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.feather.common.core.domain.BaseEntity;

public class NtDataCleaningUseNumMonth extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private BigDecimal sortcode;

    private Integer deletemark;

    private Integer enabledmark;

    private Integer canwrite;

    private String description;

    private Date createdate;

    private String createuserid;

    private String createusername;

    private Date modifydate;

    private String modifyuserid;

    private String modifyusername;

    private String accountid;

    private String userid;

    private String accountname;

    private String jobnumber;

    private Date opendate;

    private String accountcategoryid;

    /**
     * 账户类型
     */
    private String accountcategoryname;

    private String areaid;

    /**
     * 区域名
     */
    private String areaname;

    /**
     * 地址
     */
    private String address;

    private String joblevel;

    private String joblevelname;

    private String branch;

    private String branchname;

    private String metertypeid;

    /**
     * 表具类型
     */
    private String metertypename;

    /**
     * 本年度用量
     */
    private BigDecimal usenumber;

    /**
     * 上年度用量
     */
    private BigDecimal lastusenumber;

    /**
     * 本年度统计时间
     */
    private String readdate;

    /**
     * 本年度统计时间
     */
    private String endtime;

    /**
     * 上年度统计时间
     */
    private String lastreaddate;

    /**
     * 上年度统计时间
     */
    private String lastendtime;

    private String metersn;

    private String fullaccountcategoryid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getSortcode() {
        return sortcode;
    }

    public void setSortcode(BigDecimal sortcode) {
        this.sortcode = sortcode;
    }

    public Integer getDeletemark() {
        return deletemark;
    }

    public void setDeletemark(Integer deletemark) {
        this.deletemark = deletemark;
    }

    public Integer getEnabledmark() {
        return enabledmark;
    }

    public void setEnabledmark(Integer enabledmark) {
        this.enabledmark = enabledmark;
    }

    public Integer getCanwrite() {
        return canwrite;
    }

    public void setCanwrite(Integer canwrite) {
        this.canwrite = canwrite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public String getModifyuserid() {
        return modifyuserid;
    }

    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid;
    }

    public String getModifyusername() {
        return modifyusername;
    }

    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public Date getOpendate() {
        return opendate;
    }

    public void setOpendate(Date opendate) {
        this.opendate = opendate;
    }

    public String getAccountcategoryid() {
        return accountcategoryid;
    }

    public void setAccountcategoryid(String accountcategoryid) {
        this.accountcategoryid = accountcategoryid;
    }

    public String getAccountcategoryname() {
        return accountcategoryname;
    }

    public void setAccountcategoryname(String accountcategoryname) {
        this.accountcategoryname = accountcategoryname;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJoblevel() {
        return joblevel;
    }

    public void setJoblevel(String joblevel) {
        this.joblevel = joblevel;
    }

    public String getJoblevelname() {
        return joblevelname;
    }

    public void setJoblevelname(String joblevelname) {
        this.joblevelname = joblevelname;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getMetertypeid() {
        return metertypeid;
    }

    public void setMetertypeid(String metertypeid) {
        this.metertypeid = metertypeid;
    }

    public String getMetertypename() {
        return metertypename;
    }

    public void setMetertypename(String metertypename) {
        this.metertypename = metertypename;
    }

    public BigDecimal getUsenumber() {
        return usenumber;
    }

    public void setUsenumber(BigDecimal usenumber) {
        this.usenumber = usenumber;
    }

    public String getReaddate() {
        return readdate;
    }

    public void setReaddate(String readdate) {
        this.readdate = readdate;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public BigDecimal getLastusenumber() {
        return lastusenumber;
    }

    public void setLastusenumber(BigDecimal lastusenumber) {
        this.lastusenumber = lastusenumber;
    }

    public String getLastreaddate() {
        return lastreaddate;
    }

    public void setLastreaddate(String lastreaddate) {
        this.lastreaddate = lastreaddate;
    }

    public String getLastendtime() {
        return lastendtime;
    }

    public void setLastendtime(String lastendtime) {
        this.lastendtime = lastendtime;
    }

    public String getMetersn() {
        return metersn;
    }

    public void setMetersn(String metersn) {
        this.metersn = metersn;
    }

    public String getFullaccountcategoryid() {
        return fullaccountcategoryid;
    }

    public void setFullaccountcategoryid(String fullaccountcategoryid) {
        this.fullaccountcategoryid = fullaccountcategoryid;
    }

    /**
     * 重写hashCode方法
     *
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        // 这里的code需要改成你要判断如重复的属性名称
        result = prime * result + ((readdate == null) ? 0 : readdate.hashCode());
        return result;
    }

    /**
     * 重写equals方法
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        // Parts是我的实体类名称
        NtDataCleaningUseNumMonth parts = (NtDataCleaningUseNumMonth) obj;
        // code改成你需要的属性名
        if (readdate == null) {
            if (parts.readdate != null) {
                return false;
            }
        } else if (!readdate.equals(parts.readdate)) {
            return false;
        }
        return true;
    }

    // 构造方法
    public NtDataCleaningUseNumMonth(String readdate, BigDecimal usenumber) {
        this.readdate = readdate;
        this.usenumber = usenumber;
    }

    public NtDataCleaningUseNumMonth() {
    }

    // 把这个对象传进来
    public static NtDataCleaningUseNumMonth merge(NtDataCleaningUseNumMonth parts1, NtDataCleaningUseNumMonth parts2) {
        // 这里做下判断
        if (!parts1.equals(parts2)) {
            throw new IllegalArgumentException();
        }
        return new NtDataCleaningUseNumMonth(parts1.readdate, parts1.usenumber.add(parts2.usenumber));
    }

}