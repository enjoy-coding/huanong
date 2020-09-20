package com.feather.smart.service.impl;

import com.feather.common.core.text.Convert;
import com.feather.smart.domain.ZhsqZc;
import com.feather.smart.mapper.ZhsqZcMapper;
import com.feather.smart.service.IZhsqZcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区资产Service业务层处理
 *
 * @author fancy
 * @date 2020-05-14
 */
@Service
public class ZhsqZcServiceImpl implements IZhsqZcService
{
    @Autowired
    private ZhsqZcMapper zhsqZcMapper;

    /**
     * 查询社区资产
     *
     * @param zcid 社区资产ID
     * @return 社区资产
     */
    @Override
    public ZhsqZc selectZhsqZcById(String zcid)
    {
        return zhsqZcMapper.selectZhsqZcById(zcid);
    }

    /**
     * 查询社区资产列表
     *
     * @param zhsqZc 社区资产
     * @return 社区资产
     */
    @Override
    public List<ZhsqZc> selectZhsqZcList(ZhsqZc zhsqZc)
    {
        return zhsqZcMapper.selectZhsqZcList(zhsqZc);
    }

    /**
     * 新增社区资产
     *
     * @param zhsqZc 社区资产
     * @return 结果
     */
    @Override
    public int insertZhsqZc(ZhsqZc zhsqZc)
    {
        return zhsqZcMapper.insertZhsqZc(zhsqZc);
    }

    /**
     * 修改社区资产
     *
     * @param zhsqZc 社区资产
     * @return 结果
     */
    @Override
    public int updateZhsqZc(ZhsqZc zhsqZc)
    {
        return zhsqZcMapper.updateZhsqZc(zhsqZc);
    }

    /**
     * 删除社区资产对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZhsqZcByIds(String ids)
    {
        return zhsqZcMapper.deleteZhsqZcByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除社区资产信息
     *
     * @param zcid 社区资产ID
     * @return 结果
     */
    @Override
    public int deleteZhsqZcById(String zcid)
    {
        return zhsqZcMapper.deleteZhsqZcById(zcid);
    }

    @Override
    public List<Map> getSbJc(String type,String id) {
        switch (type){
            case "SXT":
                return zhsqZcMapper.getSbJc("ZHSQ_SXTJC","SXTID",id);   //摄像头
            case "LD":
                return zhsqZcMapper.getSbJc("ZHSQ_LDJC","LDID",id);    //路灯
            case "MJ":
                return zhsqZcMapper.getSbJcAndJm("ZHSQ_MJJC","MJID",id);    //门禁
            case "DG":
                return zhsqZcMapper.getSbJcAndJm("ZHSQ_DGJC","DGID",id);    //车辆  倒杆
            case "ZJ":
                return zhsqZcMapper.getSbJcAndJm("ZHSQ_ZJJC","ZJID",id);    //人员 闸机
            case "LJT":
                return zhsqZcMapper.getSbJc("ZHSQ_LJTJC","LJTID",id);   //垃圾桶
            case "YG":
                return zhsqZcMapper.getSbJc("ZHSQ_YGJC","YGID",id);    //烟感
            case "CDZ":
                return zhsqZcMapper.getSbJc("ZHSQ_CDZJC","CDZID",id);   //充电桩
            case "XFS":
                return zhsqZcMapper.getSbJc("ZHSQ_XFSJC","XFSID",id);   //消防栓


        }
      return null;
    }

    public List<Map> getAreaTree(String sqid){
        return zhsqZcMapper.getAreaTree(sqid);
    }
    public List<Map> getSqAreaTree(){
        return zhsqZcMapper.getSqAreaTree();
    }

    @Override
    public List<Map> getSbxxCount(String sqid,String xqid){
        List<Map> sbxxCount = zhsqZcMapper.getSbxxCount(sqid, xqid);
        List<Map> sxxx = new ArrayList<>();
        Map map1 = new HashMap();
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        Map map4 = new HashMap();
        map1.put("sblx","摄像头");
        map2.put("sblx","闸机");
        map3.put("sblx","门禁");
        map4.put("sblx","烟感");
        map1.put("zx","0");
        map2.put("zx","0");
        map3.put("zx","0");
        map4.put("zx","0");
        map1.put("lx","0");
        map2.put("lx","0");
        map3.put("lx","0");
        map4.put("lx","0");
        int sum1=0,sum2=0,sum3=0,sum4=0;
        for(Map map:sbxxCount){
           String sblx = map.get("sblx").toString();
           String sbzt = map.get("sbzt").toString();
           String num = map.get("num").toString();
            if("摄像头".equals(sblx)){
               if("在线".equals(sbzt)){
                   map1.put("zx",num);
               }else if("离线".equals(sbzt)){
                   map1.put("lx",num);
               }
               sum1+=Integer.valueOf(num);
            }else if("闸机".equals(sblx)){
                if("在线".equals(sbzt)){
                    map2.put("zx",num);
                }else if("离线".equals(sbzt)){
                    map2.put("lx",num);
                }
                sum2+=Integer.valueOf(num);
            }else if("门禁".equals(sblx)){
                if("在线".equals(sbzt)){
                    map3.put("zx",num);
                }else if("离线".equals(sbzt)){
                    map3.put("lx",num);
                }
                sum3+=Integer.valueOf(num);
            }else if("烟感".equals(sblx)){
                if("在线".equals(sbzt)){
                    map4.put("zx",num);
                }else if("离线".equals(sbzt)){
                    map4.put("lx",num);
                }
                sum4+=Integer.valueOf(num);
            }

        }
        map1.put("sum",sum1);
        map2.put("sum",sum2);
        map3.put("sum",sum3);
        map4.put("sum",sum4);
        sxxx.add(map1);
        sxxx.add(map2);
        sxxx.add(map3);
        sxxx.add(map4);
        return sxxx;
    }

    @Override
    public List<Map> getSblxYcCount(String sqid, String xqid) {
       return zhsqZcMapper.getSblxYcCount(sqid, xqid);
    }

    @Override
    public List<Map> getListSb(String sblx, String sbmc, String zcid, String sqid, String xqid) {
        return zhsqZcMapper.getListSb(sblx,sbmc,zcid,sqid,xqid);
    }

    @Override
    public List<Map> getCljcList(String sqid, String xqid, String clhm, String id,String cllx) {
        return zhsqZcMapper.getCljcList(sqid,xqid,clhm,id,cllx);
    }

    @Override
    public List<Map> getClList(String sqid, String xqid, String clhm, String id,String cx) {
        return zhsqZcMapper.getClList(sqid,xqid,clhm,id,cx);
    }


}