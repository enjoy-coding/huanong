package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupElectricitySide;
import com.feather.aupipes.mapper.AupElectricitySideMapper;
import com.feather.aupipes.service.IAupElectricitySideService;
import com.feather.common.core.domain.Ztree;

import com.feather.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用电分区Service业务层处理
 *
 * @author dufan
 * @date 2020-06-09
 */
@Service
public class AupElectricitySideServiceImpl implements IAupElectricitySideService
{
    @Autowired
    private AupElectricitySideMapper aupElectricitySideMapper;

    /**
     * 查询用电分区
     *
     * @param id 用电分区ID
     * @return 用电分区
     */
    @Override
    public AupElectricitySide selectAupElectricitySideById(Long id)
    {
        return aupElectricitySideMapper.selectAupElectricitySideById(id);
    }

    /**
     * 根据类型获取等级
     */
    private int getLevelForType(String type){
        if (type == null){
            return 1;
        }
        String[] types = new String[]{"变电站","配电房","变压器","楼栋"};
        int[] levels = new int[]{1,2,3,4};
        int num = 0;
        for (int i = 0; i < types.length; i++) {
            if(type.equals(types[i])){
                num = levels[i];
            }
        }
        return num;
    }

    /**
     * 查询用电分区列表
     *
     * @param aupElectricitySide 用电分区
     * @return 用电分区
     */
    @Override
    public List<AupElectricitySide> selectAupElectricitySideList(AupElectricitySide aupElectricitySide)
    {
        return aupElectricitySideMapper.selectAupElectricitySideList(aupElectricitySide);
    }

    /**
     * 新增用电分区
     *
     * @param aupElectricitySide 用电分区
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertAupElectricitySide(AupElectricitySide aupElectricitySide)
    {
        AupElectricitySide parentAupElectricitySide = this.selectAupElectricitySideById(aupElectricitySide.getPid());
        long level = getLevelForType(aupElectricitySide.getType());
        aupElectricitySide.setCachelevel(level);
        aupElectricitySide.setHaschildren(0L);
        int count = aupElectricitySideMapper.insertAupElectricitySide(aupElectricitySide);
        if(count > 0) {
            //修改父节点hasChildren为1
            parentAupElectricitySide.setHaschildren(1L);
            aupElectricitySideMapper.updateAupElectricitySide(parentAupElectricitySide);
            //根据id修改cacheId
            String cacheId = parentAupElectricitySide.getCacheid()+ "-" + aupElectricitySide.getId();
            aupElectricitySide.setCacheid(cacheId);
            aupElectricitySideMapper.updateAupElectricitySide(aupElectricitySide);
        }
        return count;
    }

    /**
     * 修改用电分区
     *
     * @param aupElectricitySide 用电分区
     * @return 结果
     */
    @Override
    public int updateAupElectricitySide(AupElectricitySide aupElectricitySide)
    {
        long level = getLevelForType(aupElectricitySide.getType());
        aupElectricitySide.setCachelevel(level);
        return aupElectricitySideMapper.updateAupElectricitySide(aupElectricitySide);
    }





    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAupElectricitySide(AupElectricitySide aupElectricitySide){
        int count = aupElectricitySideMapper.deleteAupElectricitySideById(aupElectricitySide.getId());
        if(count > 0 ){
            //判断父级是否存在其他子节点
            if(!this.checkAupElectricitySideHasChildren(aupElectricitySide.getPid())){
                AupElectricitySide aupElectricitySideParent = this.selectAupElectricitySideById(aupElectricitySide.getPid());
                aupElectricitySideParent.setHaschildren(0L);
                this.updateAupElectricitySide(aupElectricitySideParent);
            }
        }
        return count;
    }
        /**
     * 查询用电分区列表
     *
     * @param aupElectricitySide 用电分区
     * @return 用电分区集合
     */
    @Override
    public List<AupElectricitySide> selectAupElectricitySideListByPid(AupElectricitySide aupElectricitySide){
        return aupElectricitySideMapper.selectAupElectricitySideList(aupElectricitySide);
    }
    /**
     * 用电分区树结构
     * @param pid pid
     * @return 树集合
     */
    @Override
    public List<Ztree> selectZtreeAupElectricitySide(Long pid) {
        AupElectricitySide aupElectricitySide = new AupElectricitySide();
        aupElectricitySide.setPid(pid);
        List<Ztree> ztreeList = new ArrayList<>();
        //去重y
        List<String> cacheNameList = new ArrayList<>();
        List<AupElectricitySide> electricitySideListNew = new ArrayList<>();
        List<AupElectricitySide> aupElectricitySideList = this.selectAupElectricitySideListByPid(aupElectricitySide);
        for (AupElectricitySide aupElectricitySide2 : aupElectricitySideList) {
            if (aupElectricitySide2.getCachelevel() == 4) {
                if (!cacheNameList.contains(aupElectricitySide2.getCacheid())) {
                    cacheNameList.add(aupElectricitySide2.getCacheid());
                }
            }
        }
        if(cacheNameList.size()==0){
            electricitySideListNew  = aupElectricitySideList;
        }
        for (String s : cacheNameList) {
            AupElectricitySide electricitySideNew = new AupElectricitySide();
            for (AupElectricitySide aupElectricitySide2 : aupElectricitySideList) {
                if(aupElectricitySide2.getCacheid().equals(s)){
                   electricitySideNew = aupElectricitySide2;
                    
                }
            } 
            electricitySideListNew.add(electricitySideNew);
        }

        for (AupElectricitySide e:electricitySideListNew) {
            Ztree ztree = new Ztree();
            ztree.setName(e.getCachename());
            ztree.setId(e.getId());
            ztree.setpId(e.getPid());
            ztree.setLevel(e.getCachelevel().intValue());
            if(ztree.getLevel()==4){
                ztree.setIsParent(false);
            }else {
                ztree.setIsParent(true);
            }
            ztree.setMaps(BeanUtils.beanToMap(e));
            ztreeList.add(ztree);
        }
        return ztreeList;
    }


    /**
     * 检查是否存在子节点
     * @param id id
     * @return true,false
     */
    @Override
    public boolean checkAupElectricitySideHasChildren(Long id){
       return aupElectricitySideMapper.checkAupElectricitySideHasChildren(id)>0;
    }
}