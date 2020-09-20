package com.feather.hikvnetsdk.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * 
 */

public class Test1 {

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    static int lUserID = -1;// 用户句柄
    static int m_lSetCardCfgHandle = -1; // 下发卡长连接句柄
    static int m_lSetFaceCfgHandle = -1; // 下发人脸长连接句柄

    static int dwState = -1; // 下发卡数据状态
    static int dwFaceState = -1; // 下发人脸数据状态

    /**
     * @param args
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
        Test1 test = new Test1();
        hCNetSDK.NET_DVR_Init();

        test.Login(); // 登陆

        // test.GetAllCard(); // 查询所有卡参数

        // String strCardNo = "7654321";
        // test.SetOneCard(strCardNo); // 下发1张卡号

        // Thread.sleep(1000);
        // test.GetOneCard(strCardNo); // 查询指定卡参数

        // Thread.sleep(1000);
        // test.SetOneFace(strCardNo); // 下发1张人脸

        // Thread.sleep(5000);

        // test.DelOneFace(strCardNo); // 删除人脸
        // Thread.sleep(1000);
        // test.DelOneCard(strCardNo); // 删除卡号
    }

    public void Login() {
        HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        // FMSGCallBack_V31 m_strDeviceInfo = new FMSGCallBack_V31();
        lUserID = hCNetSDK.NET_DVR_Login_V30("192.168.3.204", (short) 8000, "admin", "bjx123456", m_strDeviceInfo);
        if (lUserID == -1) {
            System.out.println("登录失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            System.out.println("登录成功！");
        }
    }

    public void GetOneCard(String strCardNo) {
        HCNetSDK.NET_DVR_CARD_COND struCardCond = new HCNetSDK.NET_DVR_CARD_COND();
        struCardCond.read();
        struCardCond.dwSize = struCardCond.size();
        struCardCond.dwCardNum = 1; // 查询一个卡参数
        struCardCond.write();
        Pointer ptrStruCond = struCardCond.getPointer();

        m_lSetCardCfgHandle = hCNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_GET_CARD, ptrStruCond,
                struCardCond.size(), null, null);
        if (m_lSetCardCfgHandle == -1) {
            System.out.println("建立查询卡参数长连接失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            System.out.println("建立查询卡参数长连接成功！");
        }

        // 查找指定卡号的参数，需要下发查找的卡号条件
        HCNetSDK.NET_DVR_CARD_SEND_DATA struCardNo = new HCNetSDK.NET_DVR_CARD_SEND_DATA();
        struCardNo.read();
        struCardNo.dwSize = struCardNo.size();

        for (int i = 0; i < HCNetSDK.ACS_CARD_NO_LEN; i++) {
            struCardNo.byCardNo[i] = 0;
        }
        for (int i = 0; i < strCardNo.length(); i++) {
            struCardNo.byCardNo[i] = strCardNo.getBytes()[i];
        }
        struCardNo.write();

        HCNetSDK.NET_DVR_CARD_RECORD struCardRecord = new HCNetSDK.NET_DVR_CARD_RECORD();
        struCardRecord.read();

        IntByReference pInt = new IntByReference(0);

        while (true) {
            dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(m_lSetCardCfgHandle, struCardNo.getPointer(),
                    struCardNo.size(), struCardRecord.getPointer(), struCardRecord.size(), pInt);
            struCardRecord.read();
            if (dwState == -1) {
                System.out.println("NET_DVR_SendWithRecvRemoteConfig查询卡参数调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEEDWAIT) {
                System.out.println("配置等待");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                continue;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                System.out.println("获取卡参数失败, 卡号: " + strCardNo);
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                System.out.println("获取卡参数异常, 卡号: " + strCardNo);
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                try {
                    System.out.println("获取卡参数成功, 卡号: " + new String(struCardRecord.byCardNo).trim() + ", 卡类型："
                            + struCardRecord.byCardType + ", 姓名：" + new String(struCardRecord.byName, "GBK").trim());
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                continue;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                System.out.println("获取卡参数完成");
                break;
            }
        }

        if (!hCNetSDK.NET_DVR_StopRemoteConfig(m_lSetCardCfgHandle)) {
            System.out.println("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("NET_DVR_StopRemoteConfig接口成功");
        }
    }

    public void GetAllCard() {
        HCNetSDK.NET_DVR_CARD_COND struCardCond = new HCNetSDK.NET_DVR_CARD_COND();
        struCardCond.read();
        struCardCond.dwSize = struCardCond.size();
        struCardCond.dwCardNum = 0xffffffff; // 查询所有
        struCardCond.write();
        Pointer ptrStruCond = struCardCond.getPointer();

        m_lSetCardCfgHandle = hCNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_GET_CARD, ptrStruCond,
                struCardCond.size(), null, null);
        if (m_lSetCardCfgHandle == -1) {
            System.out.println("建立下发卡长连接失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            System.out.println("建立下发卡长连接成功！");
        }

        HCNetSDK.NET_DVR_CARD_RECORD struCardRecord = new HCNetSDK.NET_DVR_CARD_RECORD();
        struCardRecord.read();
        struCardRecord.dwSize = struCardRecord.size();
        struCardRecord.write();

        IntByReference pInt = new IntByReference(0);

        while (true) {
            dwState = hCNetSDK.NET_DVR_GetNextRemoteConfig(m_lSetCardCfgHandle, struCardRecord.getPointer(),
                    struCardRecord.size());
            struCardRecord.read();
            if (dwState == -1) {
                System.out.println("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEEDWAIT) {
                System.out.println("配置等待");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                continue;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                System.out.println("获取卡参数失败");
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                System.out.println("获取卡参数异常");
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                try {
                    System.out.println("获取卡参数成功, 卡号: " + new String(struCardRecord.byCardNo).trim() + ", 卡类型："
                            + struCardRecord.byCardType + ", 姓名：" + new String(struCardRecord.byName, "GBK").trim());
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                continue;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                System.out.println("获取卡参数完成");
                break;
            }
        }

        if (!hCNetSDK.NET_DVR_StopRemoteConfig(m_lSetCardCfgHandle)) {
            System.out.println("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("NET_DVR_StopRemoteConfig接口成功");
        }

    }

    public void SetOneCard(String strCardNo) throws UnsupportedEncodingException, InterruptedException {
        HCNetSDK.NET_DVR_CARD_COND struCardCond = new HCNetSDK.NET_DVR_CARD_COND();
        struCardCond.read();
        struCardCond.dwSize = struCardCond.size();
        struCardCond.dwCardNum = 1; // 下发一张
        struCardCond.write();
        Pointer ptrStruCond = struCardCond.getPointer();

        m_lSetCardCfgHandle = hCNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_SET_CARD, ptrStruCond,
                struCardCond.size(), null, null);
        if (m_lSetCardCfgHandle == -1) {
            System.out.println("建立下发卡长连接失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            System.out.println("建立下发卡长连接成功！");
        }

        HCNetSDK.NET_DVR_CARD_RECORD struCardRecord = new HCNetSDK.NET_DVR_CARD_RECORD();
        struCardRecord.read();
        struCardRecord.dwSize = struCardRecord.size();

        for (int i = 0; i < HCNetSDK.ACS_CARD_NO_LEN; i++) {
            struCardRecord.byCardNo[i] = 0;
        }
        for (int i = 0; i < strCardNo.length(); i++) {
            struCardRecord.byCardNo[i] = strCardNo.getBytes()[i];
        }

        struCardRecord.byCardType = 1; // 普通卡
        struCardRecord.byLeaderCard = 0; // 是否为首卡，0-否，1-是
        struCardRecord.byUserType = 0;
        struCardRecord.byDoorRight[0] = 1; // 门1有权限

        struCardRecord.struValid.byEnable = 1; // 卡有效期使能，下面是卡有效期从2000-1-1
                                               // 11:11:11到2030-1-1 11:11:11
        struCardRecord.struValid.struBeginTime.wYear = 2000;
        struCardRecord.struValid.struBeginTime.byMonth = 1;
        struCardRecord.struValid.struBeginTime.byDay = 1;
        struCardRecord.struValid.struBeginTime.byHour = 11;
        struCardRecord.struValid.struBeginTime.byMinute = 11;
        struCardRecord.struValid.struBeginTime.bySecond = 11;
        struCardRecord.struValid.struEndTime.wYear = 2030;
        struCardRecord.struValid.struEndTime.byMonth = 1;
        struCardRecord.struValid.struEndTime.byDay = 1;
        struCardRecord.struValid.struEndTime.byHour = 11;
        struCardRecord.struValid.struEndTime.byMinute = 11;
        struCardRecord.struValid.struEndTime.bySecond = 11;

        struCardRecord.wCardRightPlan[0] = 1;// 卡计划模板1有效
        struCardRecord.dwEmployeeNo = 66611; // 工号

        byte[] strCardName = "测试".getBytes("GBK"); // 姓名
        for (int i = 0; i < HCNetSDK.NAME_LEN; i++) {
            struCardRecord.byName[i] = 0;
        }
        for (int i = 0; i < strCardName.length; i++) {
            struCardRecord.byName[i] = strCardName[i];
        }
        struCardRecord.write();

        HCNetSDK.NET_DVR_CARD_STATUS struCardStatus = new HCNetSDK.NET_DVR_CARD_STATUS();
        struCardStatus.read();
        struCardStatus.dwSize = struCardStatus.size();
        struCardStatus.write();

        IntByReference pInt = new IntByReference(0);

        while (true) {
            dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(m_lSetCardCfgHandle, struCardRecord.getPointer(),
                    struCardRecord.size(), struCardStatus.getPointer(), struCardStatus.size(), pInt);
            struCardStatus.read();
            if (dwState == -1) {
                System.out.println("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEEDWAIT) {
                System.out.println("配置等待");
                Thread.sleep(10);
                continue;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                System.out.println("下发卡失败, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码："
                        + struCardStatus.dwErrorCode);
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                System.out.println("下发卡异常, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码："
                        + struCardStatus.dwErrorCode);
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                if (struCardStatus.dwErrorCode != 0) {
                    System.out.println("下发卡成功,但是错误码" + struCardStatus.dwErrorCode + ", 卡号："
                            + new String(struCardStatus.byCardNo).trim());
                } else {
                    System.out.println("下发卡成功, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 状态："
                            + struCardStatus.byStatus);
                }
                continue;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                System.out.println("下发卡完成");
                break;
            }

        }

        if (!hCNetSDK.NET_DVR_StopRemoteConfig(m_lSetCardCfgHandle)) {
            System.out.println("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("NET_DVR_StopRemoteConfig接口成功");
        }

    }

    public void SetOneFace(String strCardNo) throws InterruptedException {
        HCNetSDK.NET_DVR_FACE_COND struFaceCond = new HCNetSDK.NET_DVR_FACE_COND();
        struFaceCond.read();
        struFaceCond.dwSize = struFaceCond.size();
        struFaceCond.byCardNo = "123456".getBytes();
        struFaceCond.dwFaceNum = 1; // 下发一张
        struFaceCond.dwEnableReaderNo = 1;// 人脸读卡器编号
        struFaceCond.write();
        Pointer ptrStruFaceCond = struFaceCond.getPointer();

        m_lSetFaceCfgHandle = hCNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_SET_FACE, ptrStruFaceCond,
                struFaceCond.size(), null, null);
        if (m_lSetFaceCfgHandle == -1) {
            System.out.println("建立下发人脸长连接失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            System.out.println("建立下发人脸长连接成功！");
        }

        HCNetSDK.NET_DVR_FACE_RECORD struFaceRecord = new HCNetSDK.NET_DVR_FACE_RECORD();
        struFaceRecord.read();
        struFaceRecord.dwSize = struFaceRecord.size();

        for (int i = 0; i < HCNetSDK.ACS_CARD_NO_LEN; i++) {
            struFaceRecord.byCardNo[i] = 0;
        }
        for (int i = 0; i < strCardNo.length(); i++) {
            struFaceRecord.byCardNo[i] = strCardNo.getBytes()[i];
        }

        /*****************************************
         * 从本地文件里面读取JPEG图片二进制数据
         *****************************************/
        FileInputStream picfile = null;
        int picdataLength = 0;
        try {
            picfile = new FileInputStream(new File(System.getProperty("user.dir") + ".\\lib\\face.jpg"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            picdataLength = picfile.available();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (picdataLength < 0) {
            System.out.println("input file dataSize < 0");
            return;
        }

        HCNetSDK.BYTE_ARRAY ptrpicByte = new HCNetSDK.BYTE_ARRAY(picdataLength);
        try {
            picfile.read(ptrpicByte.byValue);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        ptrpicByte.write();
        struFaceRecord.dwFaceLen = picdataLength;
        struFaceRecord.pFaceBuffer = ptrpicByte.getPointer();

        struFaceRecord.write();

        HCNetSDK.NET_DVR_FACE_STATUS struFaceStatus = new HCNetSDK.NET_DVR_FACE_STATUS();
        struFaceStatus.read();
        struFaceStatus.dwSize = struFaceStatus.size();
        struFaceStatus.write();

        IntByReference pInt = new IntByReference(0);

        while (true) {
            dwFaceState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(m_lSetFaceCfgHandle, struFaceRecord.getPointer(),
                    struFaceRecord.size(), struFaceStatus.getPointer(), struFaceStatus.size(), pInt);
            struFaceStatus.read();
            if (dwFaceState == -1) {
                System.out.println("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                break;
            } else if (dwFaceState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEEDWAIT) {
                System.out.println("配置等待");
                Thread.sleep(10);
                continue;
            } else if (dwFaceState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                System.out.println("下发人脸失败, 卡号: " + new String(struFaceStatus.byCardNo).trim() + ", 错误码："
                        + hCNetSDK.NET_DVR_GetLastError());
                break;
            } else if (dwFaceState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                System.out.println("下发卡异常, 卡号: " + new String(struFaceStatus.byCardNo).trim() + ", 错误码："
                        + hCNetSDK.NET_DVR_GetLastError());
                break;
            } else if (dwFaceState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                if (struFaceStatus.byRecvStatus != 1) {
                    System.out.println("下发卡失败，人脸读卡器状态" + struFaceStatus.byRecvStatus + ", 卡号："
                            + new String(struFaceStatus.byCardNo).trim());
                    break;
                } else {
                    System.out.println("下发卡成功, 卡号: " + new String(struFaceStatus.byCardNo).trim() + ", 状态："
                            + struFaceStatus.byRecvStatus);
                }
                continue;
            } else if (dwFaceState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                System.out.println("下发人脸完成");
                break;
            }

        }

        if (!hCNetSDK.NET_DVR_StopRemoteConfig(m_lSetFaceCfgHandle)) {
            System.out.println("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("NET_DVR_StopRemoteConfig接口成功");
        }

    }

    public void DelOneFace(String strCardNo) throws UnsupportedEncodingException, InterruptedException {
        HCNetSDK.NET_DVR_FACE_PARAM_CTRL struFaceDelCond = new HCNetSDK.NET_DVR_FACE_PARAM_CTRL();
        struFaceDelCond.dwSize = struFaceDelCond.size();
        struFaceDelCond.byMode = 0; // 删除方式：0- 按卡号方式删除，1- 按读卡器删除

        struFaceDelCond.struProcessMode.setType(HCNetSDK.NET_DVR_FACE_PARAM_BYCARD.class);

        // 需要删除人脸关联的卡号
        for (int i = 0; i < HCNetSDK.ACS_CARD_NO_LEN; i++) {
            struFaceDelCond.struProcessMode.struByCard.byCardNo[i] = 0;
        }
        System.arraycopy(strCardNo.getBytes(), 0, struFaceDelCond.struProcessMode.struByCard.byCardNo, 0,
                strCardNo.length());

        struFaceDelCond.struProcessMode.struByCard.byEnableCardReader[0] = 1; // 读卡器
        struFaceDelCond.struProcessMode.struByCard.byFaceID[0] = 1; // 人脸ID
        struFaceDelCond.write();

        Pointer ptrFaceDelCond = struFaceDelCond.getPointer();

        boolean bRet = hCNetSDK.NET_DVR_RemoteControl(lUserID, HCNetSDK.NET_DVR_DEL_FACE_PARAM_CFG, ptrFaceDelCond,
                struFaceDelCond.size());
        if (!bRet) {
            System.out.println("删除人脸失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            System.out.println("删除人脸成功！");
        }
    }

    public void DelOneCard(String strCardNo) throws UnsupportedEncodingException, InterruptedException {
        HCNetSDK.NET_DVR_CARD_COND struCardCond = new HCNetSDK.NET_DVR_CARD_COND();
        struCardCond.read();
        struCardCond.dwSize = struCardCond.size();
        struCardCond.dwCardNum = 1; // 下发一张
        struCardCond.write();
        Pointer ptrStruCond = struCardCond.getPointer();

        m_lSetCardCfgHandle = hCNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_DEL_CARD, ptrStruCond,
                struCardCond.size(), null, null);
        if (m_lSetCardCfgHandle == -1) {
            System.out.println("建立删除卡长连接失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            System.out.println("建立删除卡长连接成功！");
        }

        HCNetSDK.NET_DVR_CARD_SEND_DATA struCardData = new HCNetSDK.NET_DVR_CARD_SEND_DATA();
        struCardData.read();
        struCardData.dwSize = struCardData.size();

        for (int i = 0; i < HCNetSDK.ACS_CARD_NO_LEN; i++) {
            struCardData.byCardNo[i] = 0;
        }
        for (int i = 0; i < strCardNo.length(); i++) {
            struCardData.byCardNo[i] = strCardNo.getBytes()[i];
        }
        struCardData.write();

        HCNetSDK.NET_DVR_CARD_STATUS struCardStatus = new HCNetSDK.NET_DVR_CARD_STATUS();
        struCardStatus.read();
        struCardStatus.dwSize = struCardStatus.size();
        struCardStatus.write();

        IntByReference pInt = new IntByReference(0);

        while (true) {
            dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(m_lSetCardCfgHandle, struCardData.getPointer(),
                    struCardData.size(), struCardStatus.getPointer(), struCardStatus.size(), pInt);
            struCardStatus.read();
            if (dwState == -1) {
                System.out.println("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEEDWAIT) {
                System.out.println("配置等待");
                Thread.sleep(10);
                continue;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                System.out.println("删除卡失败, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码："
                        + struCardStatus.dwErrorCode);
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                System.out.println("删除卡异常, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码："
                        + struCardStatus.dwErrorCode);
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                if (struCardStatus.dwErrorCode != 0) {
                    System.out.println("删除卡成功,但是错误码" + struCardStatus.dwErrorCode + ", 卡号："
                            + new String(struCardStatus.byCardNo).trim());
                } else {
                    System.out.println("删除卡成功, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 状态："
                            + struCardStatus.byStatus);
                }
                continue;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                System.out.println("删除卡完成");
                break;
            }

        }

        if (!hCNetSDK.NET_DVR_StopRemoteConfig(m_lSetCardCfgHandle)) {
            System.out.println("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("NET_DVR_StopRemoteConfig接口成功");
        }
    }

    /**
     * 调用设备事件
     */
    public int getDeviceCase() {
        Map<String, String> eventMap;// 储存事件信息的map
        // 调用获取设备事件长连接
        HCNetSDK.NET_DVR_ACS_EVENT_COND_V50 struAcsEventCond = new HCNetSDK.NET_DVR_ACS_EVENT_COND_V50();
        struAcsEventCond.read();
        struAcsEventCond.dwSize = struAcsEventCond.size();
        struAcsEventCond.dwMajor = 5;// 5代表event事件
        struAcsEventCond.dwMinor = 0x4b;// 代表刷脸成功
        // struAcsEventCond.struStartTime =
        // getDvrStruTime(StruTimeEnum.STRAT.toString());// ****必须要
        // struAcsEventCond.struEndTime =
        // getDvrStruTime(StruTimeEnum.END.toString());// ***必须要
        struAcsEventCond.write();
        Pointer ptrStruEventCond = struAcsEventCond.getPointer();
        int lHandle = hCNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_GET_ACS_EVENT, ptrStruEventCond,
                struAcsEventCond.size(), null, null);
        if (lHandle == -1) {
            System.out.println("建立获取设备事件长连接失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return -1;
        } else {
            System.out.println("建立获取设备事件连接成功！" + lHandle);
        }
        // 获取设备详细事件开始
        HCNetSDK.NET_DVR_ACS_EVENT_CFG_V50 struEventCFG = new HCNetSDK.NET_DVR_ACS_EVENT_CFG_V50();
        struEventCFG.read();
        struEventCFG.dwSize = struEventCFG.size();
        struEventCFG.write();
        while (true) {
            dwState = hCNetSDK.NET_DVR_GetNextRemoteConfig(lHandle, struEventCFG.getPointer(), struEventCFG.size());
            struEventCFG.read();
            if (dwState == -1) {
                System.out.println("NET_DVR_GetNextRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                continue;
            } else if (dwState == hCNetSDK.NET_SDK_GET_NEXT_STATUS_NEED_WAIT) {
                // 配置等待
                System.out.println("==========配置等待==============");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            } else if (dwState == hCNetSDK.NET_SDK_GET_NEXT_STATUS_FAILED) {
                // 获取事件失败
                System.out.println("===============获取设备事件失败=======================");
                break;

            } else if (dwState == hCNetSDK.NET_SDK_GET_NEXT_STATUS_EXCEPTION) {
                // 获取事件异常
                System.out.println("===============获取设备事件异常=======================");
                break;
            } else if (dwState == hCNetSDK.NET_SDK_GET_NEXT_STATUS_SUCCESS) {
                // 获取设备事件成功
                eventMap = new HashMap<>(16);
                // eventMap.put("employeeNo", new
                // String(struEventCFG.struAcsEventInfo.byEmployeeNo));
                // eventMap.put("cardNo", new
                // String(struEventCFG.struAcsEventInfo.byCardNo));
                // eventMap.put("struTime",
                // changeTimeFormat(struEventCFG.struTime));
                // eventList.add(eventMap);
                System.out.println("employeeNo:" + new String(struEventCFG.struAcsEventInfo.byEmployeeNo));
                System.out.println("employeeNo:" + new String(struEventCFG.struAcsEventInfo.byCardNo));
                System.out.println("struTime:" + struEventCFG.struTime.toString());
                continue;
            } else if (dwState == hCNetSDK.NET_SDK_GET_NEXT_STATUS_FINISH) {
                // 获取设备事件信息完成
                System.out.println("=============获取设备事件完成================");
                break;
            }
        }
        return 0;
    }

    /**
     * 停止长连接
     */
    public void stopRemote(int lHandle) {

        if (!hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
            System.out.println("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("NET_DVR_StopRemoteConfig接口成功");
        }
        boolean cleanUp = hCNetSDK.NET_DVR_Cleanup();
        if (cleanUp) {
            System.out.println("释放SDK成功");
        } else {
            System.out.println("释放SDK失败");
        }
    }
}// Test1
 // Class结束
