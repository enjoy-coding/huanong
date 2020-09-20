package com.feather.napo.config;

/**
 * @author nothing
 * @date 2020-06-16 17:04
 */
public enum InfoType {
    I_1(1, "吃美食"),
    I_2(2, "住酒店"),
    I_3(3, "买特产"),
    I_4(4,"玩尽兴"),
    I_5(5, "新闻公告"),
    I_6(6, "政策法规"),
    I_7(7, "机构介绍"),
    I_8(8, "旅游资讯"),
    I_9(9, "旅游问答"),
    I_10(10, "旅游投诉"),
    I_11(11, "全景旅游"),
    I_12(12, "景点管理");

    private int code;
    private String name;

    InfoType(int code, String name) {
        this.name = name;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static String getName(int code) {
        InfoType[] infoTypeEnums = values();
        for (InfoType infoTypeEnum : infoTypeEnums) {
            if (infoTypeEnum.code == code) {
                return infoTypeEnum.name;
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

}
