package com.feather.common.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayuiTreeResult extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String parentCode;
    private String name;
    private Long pid;
    private String state;
    private Map<String, Object> params;
    private List<LayuiTreeResult> children;
    private boolean haveChild;


    public LayuiTreeResult(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public LayuiTreeResult(Long id, String name, Long pid, String state, Map<String, Object> params) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.state = state;
        this.params = params;
    }

    public LayuiTreeResult(String code, String parentCode,String name, String state, Map<String, Object> params) {
        this.code = code;
        this.name = name;
        this.parentCode = parentCode;
        this.state = state;
        this.params = params;
    }
    /**
     * 构造菜单Children
     *
     * @param labelData
     * @param labelList
     * @return
     */
    public static  LayuiTreeResult setChildren(LayuiTreeResult labelData, List<LayuiTreeResult> labelList) {
        List<LayuiTreeResult> labelDataList = new ArrayList<>();
        for (LayuiTreeResult label : labelList) {
            if(labelData.getId() != null && label.getPid() != null) {
                if (labelData.getId().longValue() == label.getPid().longValue()) {
                    LayuiTreeResult labelDataChildren = new LayuiTreeResult(label.getId(), label.getName(), label.getPid(),
                            "0", label.getParams());
                    if (!isLastChild(label.getId(), labelList)) {
                        setChildren(labelDataChildren, labelList);
                    }
                    labelDataList.add(labelDataChildren);
                }
            }else if(labelData.getCode()!=null){
                if (labelData.getCode().equals(label.getParentCode())) {
                    LayuiTreeResult labelDataChildren = new LayuiTreeResult(label.getCode(),label.getParentCode(),label.getName(),"0",label.getParams());
                    if (!isLastChild(label.getCode(), labelList)) {
                        setChildren(labelDataChildren, labelList);
                    }
                    labelDataList.add(labelDataChildren);
                }
            }
        }
        labelData.setChildren(labelDataList);
        return labelData;
    }

    public static LayuiTreeResult setLayuiTreeResult(LayuiTreeResult label){
        LayuiTreeResult labelDataChildren = new LayuiTreeResult();
        if(label.getId()!=null&&label.getPid()!=null){
             labelDataChildren = new LayuiTreeResult(label.getId(), label.getName(), label.getPid(),
            "0", label.getParams());
        }else if(label.getCode()!=null&&label.getParentCode()!=null){
             labelDataChildren = new LayuiTreeResult(label.getCode(),label.getParentCode(),label.getName(),"0",label.getParams());       
        }
        return labelDataChildren;
    }

    /**
     * 设置是否有子节点展开
     *
     * @param labelList
     * @return
     */
    public static List<LayuiTreeResult> isHaveChildren(List<LayuiTreeResult> labelList) {
        for (int i = 0; i < labelList.size(); i++) {
            if (labelList.get(i).getChildren().size() > 0) {
                labelList.get(i).setHaveChild(true);
                isHaveChildren(labelList.get(i).getChildren());
            }
        }
        return labelList;
    }

    /**
     * 判断是否存在是最后一个子节点
     *
     * @param id
     * @param labelList
     * @return
     */
    public static boolean isLastChild(Long id, List<LayuiTreeResult> labelList) {
        boolean result = true;
        List<LayuiTreeResult> filterResult = labelList.stream().filter(item -> id == item.getId())
                .collect(Collectors.toList());
        if (filterResult.size() > 0) {
            result = false;
        }
        return result;
    }


    /**
     * 判断是否存在是最后一个子节点
     *
     * @param code
     * @param labelList
     * @return
     */
    public static boolean isLastChild(String code, List<LayuiTreeResult> labelList) {
        boolean result = true;
        List<LayuiTreeResult> filterResult = labelList.stream().filter(item -> code.equals(item.getCode()))
                .collect(Collectors.toList());
        if (filterResult.size() > 0) {
            result = false;
        }
        return result;
    }

    /**
     * 获取当前节点下的所有子节点
     * 
     * @param pid
     * @param t
     * @param layuiTreeResult
     * @return
     */
    public static List<LayuiTreeResult> getChildrensInt(Long pid, List<LayuiTreeResult> t,
            List<LayuiTreeResult> layuiTreeResult) {
        if (layuiTreeResult.size() > 0) {
            return layuiTreeResult;
        } else {
            for (int i = 0; i < t.size(); i++) {
                if (t.get(i).getId().longValue() == pid.longValue()) {
                    layuiTreeResult = t.get(i).getChildren();
                    return layuiTreeResult;
                }
            }
            if (layuiTreeResult.size() == 0) {
                for (int i = 0; i < t.size(); i++) {
                    if (layuiTreeResult.size() > 0) {
                        break;
                    }
                    layuiTreeResult = getChildrensInt(pid, t.get(i).getChildren(), layuiTreeResult);
                }
            }
        }
        return layuiTreeResult;
    }
}
