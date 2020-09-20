package com.feather.aupipes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.AupPower;
import com.feather.aupipes.mapper.AupPowerMapper;
import com.feather.aupipes.service.IAupPowerService;
import com.feather.common.core.domain.LayuiTreeResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.utils.bean.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 配电房Service业务层处理
 *
 * @author fancy
 * @date 2020-01-07
 */
@Service
public class AupPowerServiceImpl implements IAupPowerService {
    @Autowired
    private AupPowerMapper aupPowerMapper;

    /**
     * 查询配电房
     *
     * @param id
     *            配电房ID
     * @return 配电房
     */
    @Override
    public AupPower selectAupPowerById(Long id) {
        return aupPowerMapper.selectAupPowerById(id);
    }

    /**
     * 查询配电房列表
     *
     * @param aupPower
     *            配电房
     * @return 配电房
     */
    @Override
    public List<AupPower> selectAupPowerList(AupPower aupPower) {
        return aupPowerMapper.selectAupPowerList(aupPower);
    }

    /**
     * 新增配电房
     *
     * @param aupPower
     *            配电房
     * @return 结果
     */
    @Override
    public int insertAupPower(AupPower aupPower) {
        AupPower aupPowerParent = this.selectAupPowerById(aupPower.getPid());
        aupPower.setFjbs(aupPowerParent.getBsm());
        aupPower.setHasChildren(0);
        aupPower.setLxbh(String.valueOf(setPowerLxbhByType(aupPower.getType())));
        return aupPowerMapper.insertAupPower(aupPower);
    }

    /**
     * 根据类型获取等级
     * @param type 类型
     * @return 结果
     */
    private int setPowerLxbhByType(String type){
        int lxbh = 0;
        switch (type){
            case "一级配电房":
                lxbh = 1;
                break;
            case "次级配电房":
                lxbh = 2;
                break;
            case "箱变":
                lxbh = 3;
                break;
                default:
        }
        return lxbh;
    }

    /**
     * 修改配电房
     *
     * @param aupPower
     *            配电房
     * @return 结果
     */
    @Override
    public int updateAupPower(AupPower aupPower) {
        //判断更改的当前节点下是否存在子节点
        if(aupPowerMapper.checkAupPowerHasChildren(aupPower.getId())>0){
            aupPower.setHasChildren(1);
        }else{
            aupPower.setHasChildren(0);
        }
        //设置类型
        aupPower.setLxbh(String.valueOf(this.setPowerLxbhByType(aupPower.getType())));
        return aupPowerMapper.updateAupPower(aupPower);
    }


    /**
     * 删除配电房后修改父级节点信息
     * @param aupPower 对象
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAupPower(AupPower aupPower){
        int count = aupPowerMapper.deleteAupPowerById(aupPower.getId());
        if(count > 0 ){
            //判断父级是否存在其他子节点
            if(!this.selectAupPowerCountByPid(aupPower.getPid())){
                AupPower aupPowerParent = this.selectAupPowerById(aupPower.getPid());
                aupPowerParent.setHasChildren(0);
                this.updateAupPower(aupPowerParent);
            }
        }
        return count;
    }
    @Override
    public List<Ztree> selectAupPowerByPid(Long pid){
        AupPower aupPower = new AupPower();
        aupPower.setPid(pid);
        List<AupPower> powerList = this.selectAupPowerList(aupPower);
        return initZtree(powerList);
    }
    /**
     * 初始化树
     *
     * @param id
     *            id
     * @return 结果
     */
    @Override
    public List<Ztree> initZtreePower(Long id) {
        Long pid = id;
        if (id == null) {
            pid = 0L;
        }
        AupPower power = new AupPower();
        power.setPid(pid);
        List<AupPower> powerList = this.selectAupPowerList(power);

        return this.initZtree(powerList);
    }

    /**
     * 初始化树结构
     *
     * @param areaList 对象集合
     * @return 结果
     */
    private List<Ztree> initZtree(List<AupPower> areaList) {
        List<Ztree> ztrees = new ArrayList<>();
        for (AupPower power : areaList) {
            Ztree ztree = new Ztree();
            ztree.setId(power.getId());
            ztree.setpId(power.getPid());
            ztree.setName(power.getName());
            ztree.setTitle(power.getName());
            ztree.setMaps(BeanUtils.beanToMap(power));
            if(power.getHasChildren() == 0){
                ztree.setIsParent(false);
            }else{
                ztree.setIsParent(true);
            }
            ztrees.add(ztree);

        }
        return ztrees;
    }

    /**
     * 判断是否存在子节点
     *
     * @param pid 父id
     * @return true.false
     */
    @Override
    public boolean selectAupPowerCountByPid(Long pid) {
        return aupPowerMapper.selectAupPowerCountByPid(pid)>0;
    }

    /**
     * 设置对象为layuiTree
     * 
     * @param power 对象
     * @return 结果
     */
    private LayuiTreeResult selectLayuiTreeAupPower(AupPower power) {
        LayuiTreeResult treeResult = new LayuiTreeResult();
        treeResult.setName(power.getName());
        treeResult.setId(power.getId());
        treeResult.setPid(power.getPid());
        treeResult.setParams(BeanUtils.beanToMap(power));
        return treeResult;
    }

    @Override
    public List<LayuiTreeResult> selectAupPowerListForLayuiTree(AupPower aupPower) {
        List<AupPower> aupPowerList = aupPowerMapper.selectAupPowerList(aupPower);

        // 构造返回数据结构LayuiTreeResult
        List<LayuiTreeResult> layuiTreeResultList = new ArrayList<>();
        for (AupPower p:aupPowerList) {
            LayuiTreeResult layuiTreeResult = selectLayuiTreeAupPower(p);
            layuiTreeResultList.add(layuiTreeResult);
        }

        aupPower.setPid(0L);
        // 构造树
        List<LayuiTreeResult> returnList = new ArrayList<>();
        if (layuiTreeResultList.size() > 0) {
            for (LayuiTreeResult label : layuiTreeResultList) {
                if (label.getPid().longValue() == aupPower.getPid().longValue()) {
                    LayuiTreeResult labelData = new LayuiTreeResult(label.getId(), label.getName(), label.getPid(), "0",
                            label.getParams());
                    labelData = LayuiTreeResult.setChildren(labelData, layuiTreeResultList);
                    returnList.add(labelData);
                }
            }
        }
        // 设置展开折叠节点
        return LayuiTreeResult.isHaveChildren(returnList);
    }

    @Override
    public List<LayuiTreeResult> selectAupPowerChildrenByIdList(AupPower aupPower) {
        // 根据pid获取当前pid下面的节点
        List<LayuiTreeResult> layuiTreeResultList = this.selectAupPowerListForLayuiTree(new AupPower());
        return LayuiTreeResult.getChildrensInt(aupPower.getPid(), layuiTreeResultList, new ArrayList<>());
    }

    /**
     * 判断标识码是否唯一
     * @param power 对象
     * @return 结果
     */
    @Override
    public boolean checkAupPowerBsmNoUnque(AupPower power){
        String bsm = power.getBsm()== null ? "" : power.getBsm() ;
        AupPower aupPower = aupPowerMapper.checkAupPowerBsmNoUnque(power.getBsm());
        return aupPower != null && !aupPower.getBsm().equals(bsm);
    }

}