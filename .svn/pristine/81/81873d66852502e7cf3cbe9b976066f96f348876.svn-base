package com.feather.aupipes.domain;

import com.feather.common.core.domain.ZtreeNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2020-01-20 10:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AupRegionCamera {
    public static final String MINUS_ONE_STR = "-1";

    private Integer id;
    /**
     * 类型 0为区域，1为摄像头
     */
    private Integer type;
    /**
     * 摄像头名称
     */
    private String cameraName;
    /**
     * 摄像头编码
     */
    private String cameraIndexCode;
    /**
     * 摄像头类型
     */
    private Integer cameraType;
    /**
     * 摄像头类型中文名称
     */
    private String cameraTypeName;
    /**
     * 摄像头能力集合
     */
    private String capabilitySet;
    /**
     * 区域名称
     */
    private String regionName;
    /**
     * 区域编码
     */
    private String regionIndexCode;
    /**
     * 上级区域编码
     */
    private String parentIndexCode;
    /**
     * bimId
     */
    private String bimId;

    /**
     * 将摄像头信息转换为树形结构 当type为0的时候代表摄像头的区域，
     * 当type为1的时候代表摄像头本身，结构这样设计的原因是需要将区域和摄像头关联起来，原来 数据库中这两个表是独立的
     *
     * @return
     */
    public ZtreeNode convertToZtree() {
        ZtreeNode ztreeNode = null;
        if (type == 0) {
            ztreeNode = new ZtreeNode(regionIndexCode, parentIndexCode, regionName, regionName);
            if (MINUS_ONE_STR.equals(parentIndexCode)) {
                ztreeNode.setOpen(true);
            }
        } else if (type == 1) {
            ztreeNode = new ZtreeNode(cameraIndexCode, regionIndexCode, cameraName, cameraName);
        }
        return ztreeNode;
    }
}
