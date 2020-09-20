package com.feather.hikvision.config;

/**
 * @author nothing
 * @date 2020-05-29 13:47
 */
public enum ResourceType {
    REGION("region", "地区"),
    ORG("org", "组织"),
    ACSDEVICE("acsDevice", "门禁控制器"),
    DOOR("door", "门禁点"),
    ENCODEDEVICE("encodeDevice", "编码设备"),
    CAMERA("camera", "监控点"),
    IOLN("ioIn", "报警输入"),
    IOOUT("ioOut", "报警输出"),
    IASDEVICE("iasDevice", "入侵报警-报警主机"),
    SUBSYS("subSys", "入侵报警-子系统通道"),
    DEFENCE("defence", "入侵报警-防区通道"),
    VISDEVICEINDOOR("visDeviceInDoor", "可视对讲-室内机"),
    VISDEVICEOUTDOOR("visDeviceOutDoor", "可视对讲-门口机"),
    VISDEVICEWALLDOOR("visDeviceWallDoor", "可视对讲-围墙机"),
    VISDEVICEMANAGER("visDeviceManager", "可视对讲-管理机"),
    ECSDEVICE("ecsDevice", "梯控-控制器"),
    READER("reader", "梯控-读卡器"),
    FLOOR("floor", "梯控-楼层");

    private String code;
    private String name;

    private ResourceType(String code, String name) {
        this.name = name;
        this.code = code;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(String code) {
        ResourceType[] resourceTypeEnums = values();
        for (ResourceType resourceTypeEnum : resourceTypeEnums) {
            if (resourceTypeEnum.code.equals(code)) {
                return resourceTypeEnum.name;
            }
        }
        return null;
    }

    public static void main(String[] args) {
       String str = ResourceType.getName("camera");
        System.out.println(str);
    }
}
