package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupBuildingSide;
import com.feather.aupipes.mapper.AupBuildingSideMapper;
import com.feather.aupipes.mapper.AupCountMapper;
import com.feather.aupipes.service.IAupBuildingSideService;
import com.feather.common.core.domain.Ztree;
import com.feather.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 楼栋分区Service业务层处理
 *
 * @author fancy
 * @date 2020-03-25
 */
@Service
public class AupBuildingSideServiceImpl implements IAupBuildingSideService
{
    private final String WATER = "water";
    private final  String ELECTRICITY = "electricity";

    @Autowired
    private AupBuildingSideMapper aupBuildingSideMapper;

    @Autowired
    private AupCountMapper aupCountMapper;

    /**
     * 查询楼栋分区列表
     *
     * @param aupBuildingSide 楼栋分区
     * @return 楼栋分区
     */
    @Override
    public List<AupBuildingSide> selectAupBuildingSideList(AupBuildingSide aupBuildingSide)
    {
        return aupBuildingSideMapper.selectAupBuildingSideList(aupBuildingSide);
    }
    /**
     * 查询重点建筑楼栋分区列表
     *
     * @param aupBuildingSide 楼栋分区
     * @return 楼栋分区
     */
    @Override
    public List<AupBuildingSide> selectAupBuildingSideListLdjd(AupBuildingSide aupBuildingSide)
    {
        return aupBuildingSideMapper.selectAupBuildingSideListLdjd(aupBuildingSide);
    }


    /**
     * 重点建筑分区
     * @param pid 父id
     * @return ztree树
     */
    @Override
    public List<Ztree> initWaterAndElectricityZtrees(int pid,int level,String important,String elementById){
        List<AupBuildingSide> aupBuildingSides = this.initWaterAndElectricityZd(pid, level, important, elementById);
        List<Ztree> ztreeList = new ArrayList<>();
        for (AupBuildingSide s:aupBuildingSides) {
            Ztree ztree = new Ztree();
            if(s.getLevel_()>=4){
                ztree.setIsParent(false);
            }else {
                ztree.setIsParent(true);
            }
            ztree.setName(s.getName());
            ztree.setId(Long.parseLong(String.valueOf(s.getId())));
            ztree.setpId(Long.parseLong(String.valueOf(s.getPid())));
            ztree.setCode(s.getAreano());
            ztree.setLevel(s.getLevel_());
            ztree.setMaps(BeanUtils.beanToMap(s));
            ztreeList.add(ztree);
        }
        return ztreeList;
    }


    private List<AupBuildingSide> initWaterAndElectricityZd(int pid,int level,String important,String elementById){
        AupBuildingSide aupBuildingSide = new AupBuildingSide();
        if (WATER.equals(elementById)){
            aupBuildingSide.setType("WaterLine");
        }else {
            aupBuildingSide.setType("ElectricLine");
        }
        aupBuildingSide.setPid(pid);
        List<AupBuildingSide> aupBuildingSideListZd = new ArrayList<>();
        if (WATER.equals(elementById)&&level==2){
            aupBuildingSideListZd = this.selectAupBuildingSideListLdjd(aupBuildingSide);
        }else if (ELECTRICITY.equals(elementById)&&level==1){
            aupBuildingSideListZd = this.selectAupBuildingSideListLdjd(aupBuildingSide);
        }else {
            aupBuildingSideListZd = this.selectAupBuildingSideList(aupBuildingSide);
        }
        return aupBuildingSideListZd;
    }

    /**
     * 用电总量和重点建筑用电量
     * @return Map<String,Object>
     */
    @Override
    public Map<String,Object> queryTotalUseNumber(Map<String,Object> params){
        return aupCountMapper.queryTotalUseNumber(params);
    }

    @Override
    public List<Map> getBuildSide(String areano) {
        return aupBuildingSideMapper.getBuildSide(areano);
    }

}