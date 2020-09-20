package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupWaterSide;
import com.feather.aupipes.mapper.AupWaterSideMapper;
import com.feather.aupipes.service.IAupWaterSideService;
import com.feather.common.config.UidWorker;
import com.feather.common.core.domain.LayuiTreeResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

/**
 * 水分区Service业务层处理
 *
 * @author fancy
 * @date 2020-05-18
 */
@Service
public class AupWaterSideServiceImpl implements IAupWaterSideService
{
    private  static final Integer LEVEL_NUM = 4;

    @Autowired
    private AupWaterSideMapper aupWaterSideMapper;

    @Autowired
    private UidWorker uidworker;

    /**
     * 查询水分区
     *
     * @param id 水分区ID
     * @return 水分区
     */
    @Override
    public AupWaterSide selectAupWaterSideById(Long id)
    {
        return aupWaterSideMapper.selectAupWaterSideById(id);
    }

    /**
     * 查询水分区列表
     *
     * @param aupWaterSide 水分区
     * @return 水分区
     */
    @Override
    public List<AupWaterSide> selectAupWaterSideList(AupWaterSide aupWaterSide)
    {
        return aupWaterSideMapper.selectAupWaterSideList(aupWaterSide);
    }

    /**
     * 判断当前节点的父节点在不包含当前节点的情况下是否还存在其他子节点
     */
    @Override
    public AupWaterSide checkAupWaterParentHasChildren(AupWaterSide aupWaterSide){
            return aupWaterSideMapper.checkAupWaterParentHasChildren(aupWaterSide);
    }
    /**
     * 检验行政编码是否唯一
     */
    @Override
    public boolean checkAupWaterSideTypeNoUnque(AupWaterSide aupWaterSide){
        String typeNo = aupWaterSide.getTypeno() == null ? "" : aupWaterSide.getTypeno() ;
        AupWaterSide aupWaterSide1 = aupWaterSideMapper.checkAupWaterSideTypeNoUnque(aupWaterSide.getTypeno());
        return aupWaterSide1 != null && !aupWaterSide1.getTypeno().equals(typeNo);
    }

    /**
     * 判断当前节点是否存在字节的
     */
    @Override
    public boolean checkAupWaterSideHasChildren(Long id){
       return aupWaterSideMapper.checkAupWaterSideHasChildren(id)>0;
    }

    @Override
    public List<Ztree> selectZtreeAupWaterSide(Long pid,int level) {
        AupWaterSide aupWaterSide = new AupWaterSide();
        aupWaterSide.setPid(pid);
        aupWaterSide.setLevel(4);
        aupWaterSide.setHasChildren(100);
        List<Ztree> ztreeList = new ArrayList<>();
        //如果选中的当前等级为4级，则第五级另外查询
        if(level==LEVEL_NUM){
            List<AupWaterSide> aupWaterSideList = this.selectHouseTree(pid);
            for (AupWaterSide ss : aupWaterSideList) {
                Ztree ztreeHouse = new Ztree();
                ztreeHouse.setIsParent(false);
                ztreeHouse.setName(ss.getName());
                ztreeHouse.setId(Long.parseLong(String.valueOf(ss.getId())));
                ztreeHouse.setpId(Long.parseLong(String.valueOf(ss.getPid())));
                ztreeHouse.setCode(ss.getPCode());
                ztreeHouse.setLevel(5);
                ztreeHouse.setMaps(BeanUtils.beanToMap(ss));
                ztreeList.add(ztreeHouse);
            }
        }else {
            List<AupWaterSide> aupWaterSideList = this.selectAupWaterSideList(aupWaterSide);
            for (AupWaterSide s : aupWaterSideList) {
                Ztree ztree = new Ztree();
                if (s.getHasChildren() == 0) {
                    ztree.setIsParent(false);
                } else {
                    ztree.setIsParent(true);
                }
                ztree.setName(s.getName());
                ztree.setId(Long.parseLong(String.valueOf(s.getId())));
                ztree.setpId(Long.parseLong(String.valueOf(s.getPid())));
                ztree.setLevel(s.getLevel());
                ztree.setCode(s.getTypeno());
                ztree.setMaps(BeanUtils.beanToMap(s));
                ztreeList.add(ztree);
            }
        }
        return ztreeList;
    }

    @Override
    public List<AupWaterSide> selectHouseTree(Long pid){
            return aupWaterSideMapper.selectHouseTree(pid);
    }

    /**
     * 根据类型获取等级
     */
    private int getLevelForType(String type){
        String[] types = new String[]{"rootw","area","zone","building","house"};
        int[] levels = new int[]{1,2,3,4,5};
        int num = 0;
        for (int i = 0; i < types.length; i++) {
           if(type.equals(types[i])){
               num = levels[i];
           }
       }
       return num;
    }
     /**
     * 新增水分区
     * 
     * @param aupWaterSide 水分区
     * @return 结果
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int insertAupWaterSide(AupWaterSide aupWaterSide)
    {

        AupWaterSide parentAupWaterSide = this.selectAupWaterSideById(aupWaterSide.getPid());
        aupWaterSide.setId(uidworker.getNextId());
        String typeCode = parentAupWaterSide.getCacheId() + "-" + aupWaterSide.getTypeno();
        aupWaterSide.setCacheId(typeCode);
        aupWaterSide.setTypecode(typeCode);
        int level = getLevelForType(aupWaterSide.getType());
        aupWaterSide.setLevel(level);
        aupWaterSide.setCacheLevel(level);
        aupWaterSide.setCacheName(aupWaterSide.getName());
        aupWaterSide.setHasChildren(0);
        aupWaterSide.setWaterCount(0);
        aupWaterSide.setSortcode("");
        aupWaterSide.setTableid(aupWaterSide.getTypeno());
        int count = aupWaterSideMapper.insertAupWaterSide(aupWaterSide);
        if(count > 0) {
            //修改父节点hasChildren为1
            parentAupWaterSide.setHasChildren(1);
            aupWaterSideMapper.updateAupWaterSide(parentAupWaterSide);
        }

        return count;
    }

    /**
     * 修改水分区
     * 
     * @param aupWaterSide 水分区
     * @return 结果
     */
    @Override
    public int updateAupWaterSide(AupWaterSide aupWaterSide)
    {
        int level = getLevelForType(aupWaterSide.getType());
        aupWaterSide.setLevel(level);
        return aupWaterSideMapper.updateAupWaterSide(aupWaterSide);
    }

    /**
     * 删除水分区信息
     * 
     * @param id 水分区ID
     * @return 结果
     */
    @Override
    public int deleteAupWaterSideById(Long id)
    {
        return aupWaterSideMapper.deleteAupWaterSideById(id);
    }

    /**
     * 删除水分区信息
     * 
     * @param aupWaterSide 水分区ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteAupWaterSide(AupWaterSide aupWaterSide)
    {
        //删除当前节点
        int count = this.deleteAupWaterSideById(aupWaterSide.getId());
        if (count>0){
            //判断当前删除的id的父节点是否还存在子节点
            if(!this.checkAupWaterSideHasChildren(aupWaterSide.getPid())){
                //不存在就修改hasChildren = 0
                AupWaterSide parentAupWaterSide = this.selectAupWaterSideById(aupWaterSide.getPid());
                parentAupWaterSide.setHasChildren(0);
                aupWaterSideMapper.updateAupWaterSide(parentAupWaterSide);
            }
        }
        return count;
    }
    /**
     * 构造异步加载树
     */
    @Override
    public List<LayuiTreeResult> selectAupWaterSideByTree(AupWaterSide aupWaterSide){
        aupWaterSide.setPid(aupWaterSide.getPid()==null?0L:aupWaterSide.getPid());
        List<AupWaterSide> aupWaterSides = aupWaterSideMapper.selectAupWaterSideTreeList(aupWaterSide);
        List<LayuiTreeResult> layuiResults = new ArrayList<>();
        boolean isHasParams = true;
        if(aupWaterSide.getParams().get("type")!=null){
            isHasParams = false;
        }
        for (AupWaterSide a : aupWaterSides) {
            LayuiTreeResult layuiTreeResult = new LayuiTreeResult();
            layuiTreeResult.setId(a.getId());
            layuiTreeResult.setPid(a.getPid());
            layuiTreeResult.setName(a.getName());
            layuiTreeResult.setParams(BeanUtils.beanToMap(a));
            if(isHasParams){
                layuiTreeResult.setHaveChild(a.getHasChildren()==1L);
            }else{
                layuiTreeResult.setHaveChild(true);
            }
            layuiResults.add(layuiTreeResult);
        }
        return layuiResults;
    }
}