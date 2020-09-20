package com.feather.napo.domain;

import java.io.IOException;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import org.apache.logging.log4j.util.Strings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feather.common.core.domain.BaseEntity;
import com.feather.prd.domain.PrdAttachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2020-06-16 15:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NpInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     **/
    @ApiModelProperty(value = "消息id", notes = "自动生成，不需要填写")
    private Long infoId;

    /**
     * 信息类型，具体类型在InfoType枚举类中做比对
     */
    @ApiModelProperty(value = "消息类型", notes = "根据InfoType枚举类中进行识别(必填)", required = true, position = 1)
    private Integer infoType;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", position = 2)
    private String title;

    /**
     * 副标题
     */
    @ApiModelProperty(value = "副标题", position = 3)
    private String subTitle;

    /**
     * 主图片
     */
    @ApiModelProperty(value = "主图片", position = 4)
    private String mainPic;

    /**
     * 从图片，多张图片用分号隔开
     */
    @ApiModelProperty(value = "图片列表", notes = "多张之间用;隔开，传上传后返回的对象", position = 5)
    private String subPics;

    /**
     * 作者
     */
    @ApiModelProperty(value = "文章作者", notes = "暂时未用到", position = 6)
    private String author;

    /**
     * 内容
     */
    @ApiModelProperty(value = "文章内容", position = 7)
    private String content;

    /**
     * 热度
     */
    @ApiModelProperty(value = "热度", position = 8)
    private Integer hot;

    /**
     * 查看次数
     */
    @ApiModelProperty(value = "浏览次数", notes = "自动计算，插入的时候给0即可", position = 9)
    private Integer viewCount;

    /**
     * 回复（信息类型为9,10d的时候需要用的字段）
     */
    @ApiModelProperty(value = "回复", notes = "仅在infoType为9旅游资讯，10旅游问答的时候需要被填写", position = 11)
    private String answer;

    /**
     * 信息序号，默认录入时间的数字类型
     */
    @ApiModelProperty(value = "序号", notes = "暂时未用到", position = 12)
    private Long orderNum;

    @ApiModelProperty(value = "坐标x", notes = "仅仅在需要填入坐标的时候需被填写", position = 13)
    private String x;

    @ApiModelProperty(value = "坐标y", notes = "仅仅在需要填入坐标的时候需被填写", position = 14)
    private String y;

    @ApiModelProperty(value = "额外信息1", notes = "住酒店，即infoType为2时，代表地址;全景旅游，即infoType为11时，为上传文件的路径;景点，即infoType为12时，代表地址", position = 15)
    private String addInfo1;

    @ApiModelProperty(value = "额外信息2", notes = "住酒店，即infoType为2时，代表电话;全景旅游，即infoType为11时，为全景链接地址;景点，即infoType为12时，代表景点类别", position = 16)
    private String addInfo2;

    @ApiModelProperty(value = "额外信息3", notes = "住酒店，即infoType为2时，代表网站", position = 17)
    private String addInfo3;

    @ApiModelProperty(value = "额外信息4")
    private String addInfo4;

    /**
     * 返回前端的时候直接转换
     */
    @ApiModelProperty(value="图片列表字符串", notes = "后端自动拼接")
    private String subPicsStr;

    @ApiModelProperty(value="信息详情", notes = "有信息详情的情况下才被使用")
    private List<NpInfoDetail> npInfoDetails;

    public String getSubPicsStr() {
        StringBuilder sb = new StringBuilder();
        if (Strings.isNotBlank(subPics)) {
            String[] subPicsArr = subPics.split(";");
            for (int i = 0; i < subPicsArr.length; i++) {
                String subPic = subPicsArr[i];
                if (Strings.isNotBlank(subPic)) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        PrdAttachment attachment = mapper.readValue(subPic, PrdAttachment.class);
                        sb.append(attachment.getFilePath()).append(";");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString();
    }
}
