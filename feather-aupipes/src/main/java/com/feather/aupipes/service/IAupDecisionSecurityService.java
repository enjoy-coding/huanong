package com.feather.aupipes.service;

import com.feather.aupipes.domain.LayuiTree1;
import com.feather.common.core.domain.AjaxResult;

import java.util.List;
import java.util.Map;

/**
 * 决策保障拉闸限电service接口
 * Create by NieCheng
 * Time   2019/12/16 9:09
 */
public interface IAupDecisionSecurityService {


    /**
     * 拉闸限电模块
     * @param type
     * @return
     */
    AjaxResult selectDecisionListTree(String type);

    AjaxResult selectLzxdRightBox(String oid, String lzxdType);

    Map selectLzfxList(String type,String modelTypeName,String oid,String bhType);

    Map selectYyfwList(String type,String modelTypeName,String oid,String bhType);

    Map selectDbxqBoxData(String type,String modelTypeName,String oid,String bhType);

    /**
     * 关阀停水
     * @param oid
     * @param gftsBhType
     * @return
     */
    AjaxResult selectGftsRightBox(String oid,String gftsBhType);

    //智能排管模块
    //AjaxResult selectZnpgListTree(String type);

    Map selectZnpgRightBox(String type);

    /**
     * 预案类型查询
     *
     * @param type 预案类型
     * @return 预案列表
     */
    List<Map<String, Object>> getYaglList(String type);
    /**
     * 文件读取
     *
     * @param type 路径
     * @return 读取内容
     */
    AjaxResult readFileContent(String type);

    AjaxResult selectLzxdUserTree(String type);

    List<Map> selectAllUser(String code);

    /**
     * 查询拉闸限电信息推送的信息内容
     */
    AjaxResult selectLzxdXxts(LayuiTree1 layuiTree);

    /**
    * 根据回路ID查 房屋和配电房信息
     */
    AjaxResult getHlInfo(String hlId, String modelTypeName);

    AjaxResult getHlAttriBute(String hlId);

    AjaxResult getLdNumList(String type,String bhType);

    AjaxResult appLzxd(String fwId);

    AjaxResult selectLdPersonNum(String ldNum);
}
