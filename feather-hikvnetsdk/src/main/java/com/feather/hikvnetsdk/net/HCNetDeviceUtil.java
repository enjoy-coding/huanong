package com.feather.hikvnetsdk.net;

import java.io.ByteArrayInputStream;

import com.sun.jna.Pointer;

public class HCNetDeviceUtil {
    HCNetSDK hcNetSDK = HCNetSDK.INSTANCE;
    HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();// 设备登录信息
    HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();// 设备信息
    static FMSGCallBack_V31 fMSFCallBack_V31;// 报警回调函数实现类
    FRemoteCfgCallBackCardGet fRemoteCfgCallBackCardGet;// 获取卡参数回调函数实现类
    FRemoteCfgCallBackCardSet fRemoteCfgCallBackCardSet;// 下发卡回调函数实现类
    FRemoteCfgCallBackFaceGet fRemoteCfgCallBackFaceGet;// 获取人脸回调函数实现类
    FRemoteCfgCallBackFaceSet fRemoteCfgCallBackFaceSet;// 下发人脸回调函数实现类

    private int userId;

    public int getUserId() {
        return userId;
    }

    // 登录设备，设备会返回，唯一用户ID：lUserID
    public int loginDevice(String ip, String port, String userName, String password) {
        // 初始化SDK
        boolean status = hcNetSDK.NET_DVR_Init();
        if (status != true) {
            System.out.println("初始化失败：" + hcNetSDK.NET_DVR_GetCardLastError_Card());
            System.out.println("初始化失败：" + hcNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("初始化成功！");
        }
        // 设置报警回调函数，这个函数将会上次人脸识别比对结果
        if (fMSFCallBack_V31 == null) {
            fMSFCallBack_V31 = new FMSGCallBack_V31();
            fMSFCallBack_V31.setHCNetDeviceUtil(this);
            Pointer pUser = null;
            if (!hcNetSDK.NET_DVR_SetDVRMessageCallBack_V31(fMSFCallBack_V31, pUser)) {
                System.out.println("设置回调函数失败!");
            } else {
                System.out.println("设置回调函数成功!");
            }
        }

        // 注册
        String m_sDeviceIP = ip;// 设备ip地址
        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(m_sDeviceIP.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, m_sDeviceIP.length());

        String m_sUsername = userName;// 设备用户名
        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(m_sUsername.getBytes(), 0, m_strLoginInfo.sUserName, 0, m_sUsername.length());

        String m_sPassword = password;// 设备密码
        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(m_sPassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, m_sPassword.length());

        m_strLoginInfo.wPort = (short) Integer.parseInt(port);

        m_strLoginInfo.bUseAsynLogin = false; // 是否异步登录：0- 否，1- 是

        m_strLoginInfo.write();
        int lUserID = hcNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);

        if (lUserID == -1) {
            System.out.println("注册失败，错误号:" + hcNetSDK.NET_DVR_GetLastError());
            // 这里自己做错误处理
        } else {
            HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();

            hcNetSDK.NET_DVR_SetupAlarmChan_V41(lUserID, m_strAlarmInfo);
        }
        userId = lUserID;
        return lUserID;
    }

    // 获取卡参数
    public void GetCardInfo(int lUserID) {
        int iErr = 0;
        HCNetSDK.NET_DVR_CARD_CFG_COND m_struCardInputParam = new HCNetSDK.NET_DVR_CARD_CFG_COND();
        m_struCardInputParam.dwSize = m_struCardInputParam.size();
        m_struCardInputParam.dwCardNum = 0xffffffff; // 查找全部
        m_struCardInputParam.byCheckCardNo = 1;

        Pointer lpInBuffer = m_struCardInputParam.getPointer();
        fRemoteCfgCallBackCardGet = new FRemoteCfgCallBackCardGet();
        m_struCardInputParam.write();

        HCNetSDK.MY_USER_DATA userData = new HCNetSDK.MY_USER_DATA();
        userData.dwSize = userData.size();
        userData.byteData = "1234567".getBytes();
        Pointer pUserData = userData.getPointer();
        userData.write();

        int lHandle = hcNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_GET_CARD_CFG_V50, lpInBuffer,
                m_struCardInputParam.size(), fRemoteCfgCallBackCardGet, pUserData);
        if (lHandle < 0) {
            iErr = hcNetSDK.NET_DVR_GetLastError();
            System.out.println("建立长连接失败：" + hcNetSDK.NET_DVR_GetCardLastError_Card());
            return;
        }
        System.out.println("建立长连接成功!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!hcNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
            iErr = hcNetSDK.NET_DVR_GetLastError();
            System.out.println("断开长连接失败，错误号：" + iErr);
            return;
        }
        System.out.println("断开长连接成功!");
    }

    // 下发卡号
    public byte[] setCardInfo(int lUserID, String empCode, String empName) throws Exception {
        // 设置卡参数
        HCNetSDK.NET_DVR_CARD_CFG_COND m_struCardInputParamSet = new HCNetSDK.NET_DVR_CARD_CFG_COND();
        m_struCardInputParamSet.read();
        m_struCardInputParamSet.dwSize = m_struCardInputParamSet.size();
        m_struCardInputParamSet.dwCardNum = 1;
        m_struCardInputParamSet.byCheckCardNo = 1;
        Pointer lpInBuffer = m_struCardInputParamSet.getPointer();
        m_struCardInputParamSet.write();
        Pointer pUserData = null;
        fRemoteCfgCallBackCardSet = new FRemoteCfgCallBackCardSet();

        int lHandle = hcNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_SET_CARD_CFG_V50, lpInBuffer,
                m_struCardInputParamSet.size(), fRemoteCfgCallBackCardSet, pUserData);
        if (lHandle < 0) {
            System.out.println("建立长连接失败，错误号：" + hcNetSDK.NET_DVR_GetLastError());
        }
        System.out.println("建立设置卡参数长连接成功!");

        HCNetSDK.NET_DVR_CARD_CFG_V50 struCardInfo = new HCNetSDK.NET_DVR_CARD_CFG_V50(); // 卡参数
        struCardInfo.read();
        struCardInfo.dwSize = struCardInfo.size();
        struCardInfo.dwModifyParamType = 0x3fff;
        String strCardNo = empCode;
        for (int i = 0; i < HCNetSDK.ACS_CARD_NO_LEN; i++) {
            struCardInfo.byCardNo[i] = 0;
        }
        for (int i = 0; i < strCardNo.length(); i++) {
            struCardInfo.byCardNo[i] = strCardNo.getBytes()[i];
        }
        struCardInfo.byCardValid = 1;
        struCardInfo.byCardType = 1;
        struCardInfo.byLeaderCard = 0;
        struCardInfo.byDoorRight[0] = 1; // 门1有权限
        struCardInfo.wCardRightPlan[0].wRightPlan[0] = 1; // 门1关联卡参数计划模板1

        // 卡有效期
        struCardInfo.struValid.byEnable = 1;
        struCardInfo.struValid.struBeginTime.wYear = 2019;
        struCardInfo.struValid.struBeginTime.byMonth = 12;
        struCardInfo.struValid.struBeginTime.byDay = 1;
        struCardInfo.struValid.struBeginTime.byHour = 0;
        struCardInfo.struValid.struBeginTime.byMinute = 0;
        struCardInfo.struValid.struBeginTime.bySecond = 0;
        struCardInfo.struValid.struEndTime.wYear = 2036;
        struCardInfo.struValid.struEndTime.byMonth = 12;
        struCardInfo.struValid.struEndTime.byDay = 1;
        struCardInfo.struValid.struEndTime.byHour = 0;
        struCardInfo.struValid.struEndTime.byMinute = 0;
        struCardInfo.struValid.struEndTime.bySecond = 0;
        struCardInfo.dwMaxSwipeTime = 0; // 无次数限制
        struCardInfo.dwSwipeTime = 0;
        struCardInfo.byCardPassword = "12345".getBytes();
        struCardInfo.dwEmployeeNo = 22;

        byte[] strCardName = empName.getBytes("GBK");
        for (int i = 0; i < HCNetSDK.NAME_LEN; i++) {
            struCardInfo.byName[i] = 0;
        }
        for (int i = 0; i < strCardName.length; i++) {
            struCardInfo.byName[i] = strCardName[i];
        }

        struCardInfo.write();
        Pointer pSendBufSet = struCardInfo.getPointer();

        if (!hcNetSDK.NET_DVR_SendRemoteConfig(lHandle, 0x3, pSendBufSet, struCardInfo.size())) {
            System.out.println("发送长连接失败：" + hcNetSDK.NET_DVR_GetLastError());
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!hcNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
            System.out.println("断开长连接失败，错误号：" + hcNetSDK.NET_DVR_GetLastError());
        }
        System.out.println("断开长连接成功!");
        return struCardInfo.byCardNo;
    }

    // 获取人脸信息
    public void getFaceInfo(int lUserID) {
        int iErr = 0;
        HCNetSDK.NET_DVR_FACE_PARAM_COND m_struFaceInputParam = new HCNetSDK.NET_DVR_FACE_PARAM_COND();
        m_struFaceInputParam.dwSize = m_struFaceInputParam.size();
        m_struFaceInputParam.byCardNo = "111011".getBytes(); // 人脸关联的卡号
        m_struFaceInputParam.byEnableCardReader[0] = 1;
        m_struFaceInputParam.dwFaceNum = 1;
        m_struFaceInputParam.byFaceID = 1;
        m_struFaceInputParam.write();

        Pointer lpInBuffer = m_struFaceInputParam.getPointer();
        Pointer pUserData = null;
        fRemoteCfgCallBackFaceGet = new FRemoteCfgCallBackFaceGet();
        int lHandle = hcNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_GET_FACE_PARAM_CFG, lpInBuffer,
                m_struFaceInputParam.size(), fRemoteCfgCallBackFaceGet, pUserData);
        if (lHandle < 0) {
            iErr = hcNetSDK.NET_DVR_GetLastError();
            System.out.println("建立长连接失败，错误号：" + iErr);
            return;
        }
        System.out.println("建立获取卡参数长连接成功!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!hcNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
            iErr = hcNetSDK.NET_DVR_GetLastError();
            System.out.println("断开长连接失败，错误号：" + iErr);
            return;
        }
        System.out.println("断开长连接成功!");
    }

    // 下发人脸
    public void setFaceInfo(int lUserID, byte[] cardNo, byte[] faceByte) {
        int iErr = 0;
        // 设置人脸参数
        HCNetSDK.NET_DVR_FACE_PARAM_COND m_struFaceSetParam = new HCNetSDK.NET_DVR_FACE_PARAM_COND();
        m_struFaceSetParam.dwSize = m_struFaceSetParam.size();
        m_struFaceSetParam.byCardNo = cardNo; // 人脸关联的卡号
        m_struFaceSetParam.byEnableCardReader[0] = 1;
        m_struFaceSetParam.dwFaceNum = 1;
        m_struFaceSetParam.byFaceID = 1;
        m_struFaceSetParam.write();
        Pointer lpInBuffer = m_struFaceSetParam.getPointer();
        Pointer pUserData = null;
        fRemoteCfgCallBackFaceSet = new FRemoteCfgCallBackFaceSet();

        int lHandle = hcNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_SET_FACE_PARAM_CFG, lpInBuffer,
                m_struFaceSetParam.size(), fRemoteCfgCallBackFaceSet, pUserData);
        if (lHandle < 0) {
            iErr = hcNetSDK.NET_DVR_GetLastError();
            System.out.println("建立长连接失败，错误号：" + iErr);
            return;
        }
        System.out.println("建立设置卡参数长连接成功!");

        HCNetSDK.NET_DVR_FACE_PARAM_CFG struFaceInfo = new HCNetSDK.NET_DVR_FACE_PARAM_CFG(); // 卡参数
        struFaceInfo.read();
        struFaceInfo.dwSize = struFaceInfo.size();
        struFaceInfo.byCardNo = cardNo;
        struFaceInfo.byEnableCardReader[0] = 1; // 需要下发人脸的读卡器，按数组表示，每位数组表示一个读卡器，数组取值：0-不下发该读卡器，1-下发到该读卡器
        struFaceInfo.byFaceID = 1; // 人脸ID编号，有效取值范围：1~2
        struFaceInfo.byFaceDataType = 1; // 人脸数据类型：0- 模板（默认），1- 图片

        /*****************************************
         * 读取员工照片二进制数据，我的数据库存的是BLOB类型
         *****************************************/
        ByteArrayInputStream picfile = new ByteArrayInputStream(faceByte);
        int picdataLength = picfile.available();
        if (picdataLength < 0) {
            System.out.println("input file dataSize < 0");
            return;
        }
        if (picdataLength > 200 * 1024) {
            System.out.println("input file dataSize >200kb");
            return;
        }
        HCNetSDK.BYTE_ARRAY ptrpicByte = new HCNetSDK.BYTE_ARRAY(picdataLength);
        picfile.read(ptrpicByte.byValue, 0, ptrpicByte.byValue.length);
        ptrpicByte.write();
        /**************************/
        struFaceInfo.dwFaceLen = picdataLength;
        struFaceInfo.pFaceBuffer = ptrpicByte.getPointer();
        struFaceInfo.write();
        Pointer pSendBufSet = struFaceInfo.getPointer();
        // ENUM_ACS_INTELLIGENT_IDENTITY_DATA = 9, //智能身份识别终端数据类型，下发人脸图片数据
        if (!hcNetSDK.NET_DVR_SendRemoteConfig(lHandle, 0x9, pSendBufSet, struFaceInfo.size())) {
            iErr = hcNetSDK.NET_DVR_GetLastError();
            System.out.println("NET_DVR_SendRemoteConfig失败，错误号：" + iErr);
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!hcNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
            iErr = hcNetSDK.NET_DVR_GetLastError();
            System.out.println("断开长连接失败，错误号：" + iErr);
            return;
        }
        System.out.println("断开长连接成功!");
    }

    // 报警布防
    public void SetupAlarmChan(int lUserID) {
        // 初始化SDK
        boolean status = hcNetSDK.NET_DVR_Init();
        if (status != true) {
            System.out.println("初始化失败：" + hcNetSDK.NET_DVR_GetCardLastError_Card());
        } else {
            System.out.println("初始化成功！");
        }
        HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
        m_strAlarmInfo.dwSize = m_strAlarmInfo.size();
        m_strAlarmInfo.byLevel = 1;// 智能交通布防优先级：0- 一等级（高），1- 二等级（中），2- 三等级（低）
        m_strAlarmInfo.byAlarmInfoType = 1;// 智能交通报警信息上传类型：0-
                                           // 老报警信息（NET_DVR_PLATE_RESULT），1-
                                           // 新报警信息(NET_ITS_PLATE_RESULT)
        m_strAlarmInfo.byDeployType = 0; // 布防类型(仅针对门禁主机、人证设备)：0-客户端布防(会断网续传)，1-实时布防(只上传实时数据)
        m_strAlarmInfo.write();
        int lAlarmHandle = hcNetSDK.NET_DVR_SetupAlarmChan_V41(lUserID, m_strAlarmInfo);
        if (lAlarmHandle == -1) {
            System.out.println("布防失败，错误号:" + hcNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("布防成功");
        }
    }

    // 根据卡号删除人脸
    public void delFace(int lUserID, String cardNo) {
        int iErr = 0;
        // 删除人脸数据
        HCNetSDK.NET_DVR_FACE_PARAM_CTRL m_struFaceDel = new HCNetSDK.NET_DVR_FACE_PARAM_CTRL();
        m_struFaceDel.dwSize = m_struFaceDel.size();
        m_struFaceDel.byMode = 0; // 删除方式：0- 按卡号方式删除，1- 按读卡器删除
        m_struFaceDel.struProcessMode.setType(HCNetSDK.NET_DVR_FACE_PARAM_BYCARD.class);
        m_struFaceDel.struProcessMode.struByCard.byCardNo = cardNo.getBytes();// 需要删除人脸关联的卡号
        m_struFaceDel.struProcessMode.struByCard.byEnableCardReader[0] = 1; // 读卡器
        m_struFaceDel.struProcessMode.struByCard.byFaceID[0] = 1; // 人脸ID
        m_struFaceDel.write();
        Pointer lpInBuffer = m_struFaceDel.getPointer();
        boolean lRemoteCtrl = hcNetSDK.NET_DVR_RemoteControl(lUserID, HCNetSDK.NET_DVR_DEL_FACE_PARAM_CFG, lpInBuffer,
                m_struFaceDel.size());
        if (!lRemoteCtrl) {
            iErr = hcNetSDK.NET_DVR_GetLastError();
            System.out.println("NET_DVR_DEL_FACE_PARAM_CFG删除人脸图片失败，错误号：" + iErr);
        } else {
            System.out.println("NET_DVR_DEL_FACE_PARAM_CFG成功!");
        }
    }

    // 删除卡号
    public void delCardInfo(int lUserID, String cardNo) {
        // 设置卡参数
        HCNetSDK.NET_DVR_CARD_CFG_COND m_struCardInputParamSet = new HCNetSDK.NET_DVR_CARD_CFG_COND();
        m_struCardInputParamSet.read();
        m_struCardInputParamSet.byCheckCardNo = 1;
        Pointer lpInBuffer = m_struCardInputParamSet.getPointer();
        m_struCardInputParamSet.write();
        Pointer pUserData = null;
        fRemoteCfgCallBackCardSet = new FRemoteCfgCallBackCardSet();
        int lHandle = hcNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_SET_CARD_CFG_V50, lpInBuffer,
                m_struCardInputParamSet.size(), fRemoteCfgCallBackCardSet, pUserData);
        if (lHandle < 0) {
            System.out.println("建立长连接失败，错误号：" + hcNetSDK.NET_DVR_GetLastError());
        }
        System.out.println("建立设置卡参数长连接成功!");

        HCNetSDK.NET_DVR_CARD_CFG_V50 struCardInfo = new HCNetSDK.NET_DVR_CARD_CFG_V50(); // 卡参数
        struCardInfo.read();
        struCardInfo.dwSize = struCardInfo.size();
        struCardInfo.dwModifyParamType = 0x3fff;
        struCardInfo.byCardNo = cardNo.getBytes();
        struCardInfo.byCardValid = 0;// 卡是否有效，0-无效，1-有效
        struCardInfo.write();
        Pointer pSendBufSet = struCardInfo.getPointer();
        if (!hcNetSDK.NET_DVR_SendRemoteConfig(lHandle, 0x3, pSendBufSet, struCardInfo.size())) {
            System.out.println("发送长连接失败：" + hcNetSDK.NET_DVR_GetLastError());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!hcNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
            System.out.println("断开长连接失败，错误号：" + hcNetSDK.NET_DVR_GetLastError());
        }
        System.out.println("断开长连接成功!");
    }

}
