package com.feather.hikvnetsdk.net;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.feather.common.json.JSONObject;
import com.sun.jna.Pointer;

public class Test2 {
    public static void main(String[] args) {
        Test2 t = new Test2();
        t.init();
        t.login();
        t.SetupAlarmChan();
        // t.StartAlarmListen();
    }

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();// 设备登录信息
    HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();// 设备信息
    String m_sDeviceIP = "192.168.1.51";// 已登录设备的IP地址
    int m_sDevicePort = 8000;// 已登录设备的端口
    String m_sUsername = "admin";// 设备用户名
    String m_sPassword = "bjx123456";// 设备密码

    int lUserID = -1;// 用户句柄
    int lAlarmHandle = -1;// 报警布防句柄
    int lListenHandle = -1;// 报警监听句柄

    public static FMSGCallBack fMSFCallBack;// 报警回调函数实现
    public static FMSGCallBack_V31 fMSFCallBack_V31;// 报警回调函数实现

    public class FGPSDataCallback implements HCNetSDK.fGPSDataCallback {
        public void invoke(int nHandle, int dwState, Pointer lpBuffer, int dwBufLen, Pointer pUser) {
        }
    }

    private void init() {
        cleanup();
        boolean status = hCNetSDK.NET_DVR_Init();
        if (status != true) {
            System.out.println("初始化失败：" + hCNetSDK.NET_DVR_GetCardLastError_Card());
            System.out.println("初始化失败：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("初始化成功！");

            HCNetSDK.NET_DVR_LOCAL_GENERAL_CFG struGeneralCfg = new HCNetSDK.NET_DVR_LOCAL_GENERAL_CFG();
            struGeneralCfg.byAlarmJsonPictureSeparate = 1; // 控制JSON透传报警数据和图片是否分离，0-不分离，1-分离（分离后走COMM_ISAPI_ALARM回调返回）
            struGeneralCfg.write();

            if (!hCNetSDK.NET_DVR_SetSDKLocalCfg(17, struGeneralCfg.getPointer())) {
                System.out.println("NET_DVR_SetSDKLocalCfg失败");
            }
        }
    }

    private void login() {// GEN-FIRST:event_jButtonLoginActionPerformed

        // 注册之前先注销已注册的用户,预览情况下不可注销
        if (lUserID > -1) {
            // 先注销
            hCNetSDK.NET_DVR_Logout(lUserID);
            lUserID = -1;
        }

        // 注册
        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(m_sDeviceIP.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, m_sDeviceIP.length());

        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(m_sUsername.getBytes(), 0, m_strLoginInfo.sUserName, 0, m_sUsername.length());

        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(m_sPassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, m_sPassword.length());

        m_strLoginInfo.wPort = (short) m_sDevicePort;

        m_strLoginInfo.bUseAsynLogin = false; // 是否异步登录：0- 否，1- 是

        m_strLoginInfo.write();
        lUserID = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);
        if (lUserID == -1) {
            JOptionPane.showMessageDialog(null, "注册失败，错误号:" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            JOptionPane.showMessageDialog(null, "注册成功");
        }

        /*
         * com.feather.hikvision.net.FMSGCallBack_V31 fMSFCallBack_V31 = new
         * com.feather.hikvision.net.FMSGCallBack_V31(); Pointer pUser = null;
         * if (!hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(fMSFCallBack_V31,
         * pUser)) { System.out.println("设置回调函数失败!"); } else {
         * System.out.println("设置回调函数成功!"); }
         */
    }// GEN-LAST:event_jButtonLoginActionPerformed

    public void Logout() {
        // 报警撤防
        if (lAlarmHandle > -1) {
            if (!hCNetSDK.NET_DVR_CloseAlarmChan_V30(lAlarmHandle)) {
                JOptionPane.showMessageDialog(null, "撤防失败");
            } else {
                lAlarmHandle = -1;
            }
        }

        // 注销
        if (lUserID > -1) {
            if (hCNetSDK.NET_DVR_Logout(lUserID)) {
                JOptionPane.showMessageDialog(null, "注销成功");
                lUserID = -1;
            }
        }
    }

    public void SetupAlarmChan() {
        if (lUserID == -1) {
            JOptionPane.showMessageDialog(null, "请先注册");
            return;
        }
        if (lAlarmHandle < 0)// 尚未布防,需要布防
        {
            if (fMSFCallBack_V31 == null) {
                fMSFCallBack_V31 = new FMSGCallBack_V31();
                Pointer pUser = null;
                if (!hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(fMSFCallBack_V31, pUser)) {
                    System.out.println("设置回调函数失败!");
                }
            }
            HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
            m_strAlarmInfo.dwSize = m_strAlarmInfo.size();
            m_strAlarmInfo.byLevel = 1;// 智能交通布防优先级：0- 一等级（高），1- 二等级（中），2-
                                       // 三等级（低）
            m_strAlarmInfo.byAlarmInfoType = 1;// 智能交通报警信息上传类型：0-
                                               // 老报警信息（NET_DVR_PLATE_RESULT），1-
                                               // 新报警信息(NET_ITS_PLATE_RESULT)
            m_strAlarmInfo.byDeployType = 1; // 布防类型(仅针对门禁主机、人证设备)：0-客户端布防(会断网续传)，1-实时布防(只上传实时数据)
            m_strAlarmInfo.write();
            lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V41(lUserID, m_strAlarmInfo);
            // lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V30(lUserID);
            if (lAlarmHandle == -1) {
                JOptionPane.showMessageDialog(null, "布防失败，错误号:" + hCNetSDK.NET_DVR_GetLastError());
            } else {
                JOptionPane.showMessageDialog(null, "布防成功");
            }
        }
    }

    public void CloseAlarmChan() {
        // 报警撤防
        if (lAlarmHandle > -1) {
            if (hCNetSDK.NET_DVR_CloseAlarmChan_V30(lAlarmHandle)) {
                JOptionPane.showMessageDialog(null, "撤防成功");
                lAlarmHandle = -1;
            }
        }
    }

    public void StartAlarmListen() {
        Pointer pUser = null;

        if (fMSFCallBack == null) {
            fMSFCallBack = new FMSGCallBack();
        }
        lListenHandle = hCNetSDK.NET_DVR_StartListen_V30(m_sDeviceIP, (short) m_sDevicePort, fMSFCallBack, pUser);
        if (lListenHandle < 0) {
            JOptionPane.showMessageDialog(null, "启动监听失败，错误号:" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            JOptionPane.showMessageDialog(null, "启动监听成功");
        }
    }

    public void StopAlarmListen() {
        if (lListenHandle < 0) {
            return;
        }

        if (!hCNetSDK.NET_DVR_StopListen_V30(lListenHandle)) {
            JOptionPane.showMessageDialog(null, "停止监听失败");
        } else {
            JOptionPane.showMessageDialog(null, "停止监听成功");
        }
    }

    private void cleanup() {// GEN-FIRST:event_exitMenuItemMouseClicked
        // TODO add your handling code here:
        if (lAlarmHandle > -1) {
            hCNetSDK.NET_DVR_CloseAlarmChan_V30(lAlarmHandle);
            lAlarmHandle = -1;
        }
        if (lUserID > -1) {
            // 先注销
            hCNetSDK.NET_DVR_Logout(lUserID);
            lUserID = -1;
        }
        hCNetSDK.NET_DVR_Cleanup();
    }// GEN-LAST:event_exitMenuItemMouseClicked

    public void AlarmDataHandle(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen,
            Pointer pUser) {
        try {
            String sAlarmType = new String();

            String[] newRow = new String[3];
            // 报警时间
            Date today = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String[] sIP = new String[2];

            sAlarmType = new String("lCommand=0x") + Integer.toHexString(lCommand);
            // lCommand是传的报警类型
            switch (lCommand) {
            case HCNetSDK.COMM_ALARM_V40:
                HCNetSDK.NET_DVR_ALARMINFO_V40 struAlarmInfoV40 = new HCNetSDK.NET_DVR_ALARMINFO_V40();
                struAlarmInfoV40.write();
                Pointer pInfoV40 = struAlarmInfoV40.getPointer();
                pInfoV40.write(0, pAlarmInfo.getByteArray(0, struAlarmInfoV40.size()), 0, struAlarmInfoV40.size());
                struAlarmInfoV40.read();

                switch (struAlarmInfoV40.struAlarmFixedHeader.dwAlarmType) {
                case 0:
                    struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.setType(HCNetSDK.struIOAlarm.class);
                    struAlarmInfoV40.read();
                    sAlarmType = sAlarmType + new String("：信号量报警") + "，" + "报警输入口："
                            + struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.struioAlarm.dwAlarmInputNo;
                    break;
                case 1:
                    sAlarmType = sAlarmType + new String("：硬盘满");
                    break;
                case 2:
                    sAlarmType = sAlarmType + new String("：信号丢失");
                    break;
                case 3:
                    struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.setType(HCNetSDK.struAlarmChannel.class);
                    struAlarmInfoV40.read();
                    int iChanNum = struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.sstrualarmChannel.dwAlarmChanNum;
                    sAlarmType = sAlarmType + new String("：移动侦测") + "，" + "报警通道个数：" + iChanNum + "，" + "报警通道号：";

                    for (int i = 0; i < iChanNum; i++) {
                        byte[] byChannel = struAlarmInfoV40.pAlarmData.getByteArray(i * 4, 4);

                        int iChanneNo = 0;
                        for (int j = 0; j < 4; j++) {
                            int ioffset = j * 8;
                            int iByte = byChannel[j] & 0xff;
                            iChanneNo = iChanneNo + (iByte << ioffset);
                        }

                        sAlarmType = sAlarmType + "+ch[" + iChanneNo + "]";
                    }

                    break;
                case 4:
                    sAlarmType = sAlarmType + new String("：硬盘未格式化");
                    break;
                case 5:
                    sAlarmType = sAlarmType + new String("：读写硬盘出错");
                    break;
                case 6:
                    sAlarmType = sAlarmType + new String("：遮挡报警");
                    break;
                case 7:
                    sAlarmType = sAlarmType + new String("：制式不匹配");
                    break;
                case 8:
                    sAlarmType = sAlarmType + new String("：非法访问");
                    break;
                }

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));
                break;
            case HCNetSDK.COMM_ALARM_V30:
                HCNetSDK.NET_DVR_ALARMINFO_V30 strAlarmInfoV30 = new HCNetSDK.NET_DVR_ALARMINFO_V30();
                strAlarmInfoV30.write();
                Pointer pInfoV30 = strAlarmInfoV30.getPointer();
                pInfoV30.write(0, pAlarmInfo.getByteArray(0, strAlarmInfoV30.size()), 0, strAlarmInfoV30.size());
                strAlarmInfoV30.read();
                switch (strAlarmInfoV30.dwAlarmType) {
                case 0:
                    sAlarmType = sAlarmType + new String("：信号量报警") + "，" + "报警输入口："
                            + (strAlarmInfoV30.dwAlarmInputNumber + 1);
                    break;
                case 1:
                    sAlarmType = sAlarmType + new String("：硬盘满");
                    break;
                case 2:
                    sAlarmType = sAlarmType + new String("：信号丢失");
                    break;
                case 3:
                    sAlarmType = sAlarmType + new String("：移动侦测") + "，" + "报警通道：";
                    for (int i = 0; i < 64; i++) {
                        if (strAlarmInfoV30.byChannel[i] == 1) {
                            sAlarmType = sAlarmType + "ch" + (i + 1) + " ";
                        }
                    }
                    break;
                case 4:
                    sAlarmType = sAlarmType + new String("：硬盘未格式化");
                    break;
                case 5:
                    sAlarmType = sAlarmType + new String("：读写硬盘出错");
                    break;
                case 6:
                    sAlarmType = sAlarmType + new String("：遮挡报警");
                    break;
                case 7:
                    sAlarmType = sAlarmType + new String("：制式不匹配");
                    break;
                case 8:
                    sAlarmType = sAlarmType + new String("：非法访问");
                    break;
                }
                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));
                break;
            case HCNetSDK.COMM_ALARM_RULE:
                HCNetSDK.NET_VCA_RULE_ALARM strVcaAlarm = new HCNetSDK.NET_VCA_RULE_ALARM();
                strVcaAlarm.write();
                Pointer pVcaInfo = strVcaAlarm.getPointer();
                pVcaInfo.write(0, pAlarmInfo.getByteArray(0, strVcaAlarm.size()), 0, strVcaAlarm.size());
                strVcaAlarm.read();

                switch (strVcaAlarm.struRuleInfo.wEventTypeEx) {
                case 1:
                    sAlarmType = sAlarmType + new String("：穿越警戒面") + "，" + "_wPort:" + strVcaAlarm.struDevInfo.wPort
                            + "_byChannel:" + strVcaAlarm.struDevInfo.byChannel + "_byIvmsChannel:"
                            + strVcaAlarm.struDevInfo.byIvmsChannel + "_Dev IP："
                            + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                    break;
                case 2:
                    sAlarmType = sAlarmType + new String("：目标进入区域") + "，" + "_wPort:" + strVcaAlarm.struDevInfo.wPort
                            + "_byChannel:" + strVcaAlarm.struDevInfo.byChannel + "_byIvmsChannel:"
                            + strVcaAlarm.struDevInfo.byIvmsChannel + "_Dev IP："
                            + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                    break;
                case 3:
                    sAlarmType = sAlarmType + new String("：目标离开区域") + "，" + "_wPort:" + strVcaAlarm.struDevInfo.wPort
                            + "_byChannel:" + strVcaAlarm.struDevInfo.byChannel + "_byIvmsChannel:"
                            + strVcaAlarm.struDevInfo.byIvmsChannel + "_Dev IP："
                            + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                    break;
                default:
                    sAlarmType = sAlarmType + new String("：其他行为分析报警，事件类型：") + strVcaAlarm.struRuleInfo.wEventTypeEx
                            + "_wPort:" + strVcaAlarm.struDevInfo.wPort + "_byChannel:"
                            + strVcaAlarm.struDevInfo.byChannel + "_byIvmsChannel:"
                            + strVcaAlarm.struDevInfo.byIvmsChannel + "_Dev IP："
                            + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                    break;
                }
                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));

                if (strVcaAlarm.dwPicDataLen > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        fout = new FileOutputStream(".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "wEventTypeEx["
                                + strVcaAlarm.struRuleInfo.wEventTypeEx + "]_" + newName + "_vca.jpg");
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strVcaAlarm.pImage.getByteBuffer(offset, strVcaAlarm.dwPicDataLen);
                        byte[] bytes = new byte[strVcaAlarm.dwPicDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_UPLOAD_PLATE_RESULT:
                HCNetSDK.NET_DVR_PLATE_RESULT strPlateResult = new HCNetSDK.NET_DVR_PLATE_RESULT();
                strPlateResult.write();
                Pointer pPlateInfo = strPlateResult.getPointer();
                pPlateInfo.write(0, pAlarmInfo.getByteArray(0, strPlateResult.size()), 0, strPlateResult.size());
                strPlateResult.read();
                try {
                    String srt3 = new String(strPlateResult.struPlateInfo.sLicense, "GBK");
                    sAlarmType = sAlarmType + "：交通抓拍上传，车牌：" + srt3;
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));

                if (strPlateResult.dwPicLen > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        fout = new FileOutputStream(".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_" + newName
                                + "_plateResult.jpg");
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strPlateResult.pBuffer1.getByteBuffer(offset, strPlateResult.dwPicLen);
                        byte[] bytes = new byte[strPlateResult.dwPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_ITS_PLATE_RESULT:
                HCNetSDK.NET_ITS_PLATE_RESULT strItsPlateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
                strItsPlateResult.write();
                Pointer pItsPlateInfo = strItsPlateResult.getPointer();
                pItsPlateInfo.write(0, pAlarmInfo.getByteArray(0, strItsPlateResult.size()), 0,
                        strItsPlateResult.size());
                strItsPlateResult.read();
                try {
                    String srt3 = new String(strItsPlateResult.struPlateInfo.sLicense, "GBK");
                    sAlarmType = sAlarmType + ",车辆类型：" + strItsPlateResult.byVehicleType + ",交通抓拍上传，车牌：" + srt3;
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));

                for (int i = 0; i < strItsPlateResult.dwPicNum; i++) {
                    if (strItsPlateResult.struPicInfo[i].dwDataLen > 0) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            String filename = ".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_" + newName
                                    + "_type[" + strItsPlateResult.struPicInfo[i].byType + "]_ItsPlate.jpg";
                            fout = new FileOutputStream(filename);
                            // 将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strItsPlateResult.struPicInfo[i].pBuffer.getByteBuffer(offset,
                                    strItsPlateResult.struPicInfo[i].dwDataLen);
                            byte[] bytes = new byte[strItsPlateResult.struPicInfo[i].dwDataLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case HCNetSDK.COMM_ALARM_PDC:
                HCNetSDK.NET_DVR_PDC_ALRAM_INFO strPDCResult = new HCNetSDK.NET_DVR_PDC_ALRAM_INFO();
                strPDCResult.write();
                Pointer pPDCInfo = strPDCResult.getPointer();
                pPDCInfo.write(0, pAlarmInfo.getByteArray(0, strPDCResult.size()), 0, strPDCResult.size());
                strPDCResult.read();

                if (strPDCResult.byMode == 0) {
                    strPDCResult.uStatModeParam.setType(HCNetSDK.NET_DVR_STATFRAME.class);
                    sAlarmType = sAlarmType + "：客流量统计，进入人数：" + strPDCResult.dwEnterNum + "，离开人数："
                            + strPDCResult.dwLeaveNum + ", byMode:" + strPDCResult.byMode + ", dwRelativeTime:"
                            + strPDCResult.uStatModeParam.struStatFrame.dwRelativeTime + ", dwAbsTime:"
                            + strPDCResult.uStatModeParam.struStatFrame.dwAbsTime;
                }
                if (strPDCResult.byMode == 1) {
                    strPDCResult.uStatModeParam.setType(HCNetSDK.NET_DVR_STATTIME.class);
                    String strtmStart = ""
                            + String.format("%04d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwYear)
                            + String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwMonth)
                            + String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwDay)
                            + String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwHour)
                            + String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwMinute)
                            + String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwSecond);
                    String strtmEnd = "" + String.format("%04d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwYear)
                            + String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwMonth)
                            + String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwDay)
                            + String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwHour)
                            + String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwMinute)
                            + String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwSecond);
                    sAlarmType = sAlarmType + "：客流量统计，进入人数：" + strPDCResult.dwEnterNum + "，离开人数："
                            + strPDCResult.dwLeaveNum + ", byMode:" + strPDCResult.byMode + ", tmStart:" + strtmStart
                            + ",tmEnd :" + strtmEnd;
                }

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(strPDCResult.struDevInfo.struDevIP.sIpV4).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));
                break;

            case HCNetSDK.COMM_ITS_PARK_VEHICLE:
                HCNetSDK.NET_ITS_PARK_VEHICLE strItsParkVehicle = new HCNetSDK.NET_ITS_PARK_VEHICLE();
                strItsParkVehicle.write();
                Pointer pItsParkVehicle = strItsParkVehicle.getPointer();
                pItsParkVehicle.write(0, pAlarmInfo.getByteArray(0, strItsParkVehicle.size()), 0,
                        strItsParkVehicle.size());
                strItsParkVehicle.read();
                try {
                    String srtParkingNo = new String(strItsParkVehicle.byParkingNo).trim(); // 车位编号
                    String srtPlate = new String(strItsParkVehicle.struPlateInfo.sLicense, "GBK").trim(); // 车牌号码
                    sAlarmType = sAlarmType + ",停产场数据,车位编号：" + srtParkingNo + ",车位状态："
                            + strItsParkVehicle.byLocationStatus + ",车牌：" + srtPlate;
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));

                for (int i = 0; i < strItsParkVehicle.dwPicNum; i++) {
                    if (strItsParkVehicle.struPicInfo[i].dwDataLen > 0) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            String filename = ".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_" + newName
                                    + "_type[" + strItsParkVehicle.struPicInfo[i].byType + "]_ParkVehicle.jpg";
                            fout = new FileOutputStream(filename);
                            // 将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strItsParkVehicle.struPicInfo[i].pBuffer.getByteBuffer(offset,
                                    strItsParkVehicle.struPicInfo[i].dwDataLen);
                            byte[] bytes = new byte[strItsParkVehicle.struPicInfo[i].dwDataLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case HCNetSDK.COMM_ALARM_TFS:
                HCNetSDK.NET_DVR_TFS_ALARM strTFSAlarmInfo = new HCNetSDK.NET_DVR_TFS_ALARM();
                strTFSAlarmInfo.write();
                Pointer pTFSInfo = strTFSAlarmInfo.getPointer();
                pTFSInfo.write(0, pAlarmInfo.getByteArray(0, strTFSAlarmInfo.size()), 0, strTFSAlarmInfo.size());
                strTFSAlarmInfo.read();

                try {
                    String srtPlate = new String(strTFSAlarmInfo.struPlateInfo.sLicense, "GBK").trim(); // 车牌号码
                    sAlarmType = sAlarmType + "：交通取证报警信息，违章类型：" + strTFSAlarmInfo.dwIllegalType + "，车牌号码：" + srtPlate
                            + "，车辆出入状态：" + strTFSAlarmInfo.struAIDInfo.byVehicleEnterState;
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(strTFSAlarmInfo.struDevInfo.struDevIP.sIpV4).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));
                break;
            case HCNetSDK.COMM_ALARM_AID_V41:
                HCNetSDK.NET_DVR_AID_ALARM_V41 struAIDAlarmInfo = new HCNetSDK.NET_DVR_AID_ALARM_V41();
                struAIDAlarmInfo.write();
                Pointer pAIDInfo = struAIDAlarmInfo.getPointer();
                pAIDInfo.write(0, pAlarmInfo.getByteArray(0, struAIDAlarmInfo.size()), 0, struAIDAlarmInfo.size());
                struAIDAlarmInfo.read();
                sAlarmType = sAlarmType + "：交通事件报警信息，交通事件类型：" + struAIDAlarmInfo.struAIDInfo.dwAIDType + "，规则ID："
                        + struAIDAlarmInfo.struAIDInfo.byRuleID + "，车辆出入状态："
                        + struAIDAlarmInfo.struAIDInfo.byVehicleEnterState;

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(struAIDAlarmInfo.struDevInfo.struDevIP.sIpV4).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));
                break;
            case HCNetSDK.COMM_ALARM_TPS_V41:
                HCNetSDK.NET_DVR_TPS_ALARM_V41 struTPSAlarmInfo = new HCNetSDK.NET_DVR_TPS_ALARM_V41();
                struTPSAlarmInfo.write();
                Pointer pTPSInfo = struTPSAlarmInfo.getPointer();
                pTPSInfo.write(0, pAlarmInfo.getByteArray(0, struTPSAlarmInfo.size()), 0, struTPSAlarmInfo.size());
                struTPSAlarmInfo.read();

                sAlarmType = sAlarmType + "：交通统计报警信息，绝对时标：" + struTPSAlarmInfo.dwAbsTime + "，能见度:"
                        + struTPSAlarmInfo.struDevInfo.byIvmsChannel + "，车道1交通状态:"
                        + struTPSAlarmInfo.struTPSInfo.struLaneParam[0].byTrafficState + "，监测点编号："
                        + new String(struTPSAlarmInfo.byMonitoringSiteID).trim() + "，设备编号："
                        + new String(struTPSAlarmInfo.byDeviceID).trim() + "，开始统计时间：" + struTPSAlarmInfo.dwStartTime
                        + "，结束统计时间：" + struTPSAlarmInfo.dwStopTime;

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(struTPSAlarmInfo.struDevInfo.struDevIP.sIpV4).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));
                break;
            case HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT:
                // 实时人脸抓拍上传
                HCNetSDK.NET_VCA_FACESNAP_RESULT strFaceSnapInfo = new HCNetSDK.NET_VCA_FACESNAP_RESULT();
                strFaceSnapInfo.write();
                Pointer pFaceSnapInfo = strFaceSnapInfo.getPointer();
                pFaceSnapInfo.write(0, pAlarmInfo.getByteArray(0, strFaceSnapInfo.size()), 0, strFaceSnapInfo.size());
                strFaceSnapInfo.read();
                sAlarmType = sAlarmType + "：人脸抓拍上传，人脸评分：" + strFaceSnapInfo.dwFaceScore + "，年龄段："
                        + strFaceSnapInfo.struFeature.byAgeGroup + "，性别：" + strFaceSnapInfo.struFeature.bySex;
                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(strFaceSnapInfo.struDevInfo.struDevIP.sIpV4).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); // 设置日期格式
                String time = df.format(new Date()); // new Date()为获取当前系统时间
                // 人脸图片写文件
                try {
                    FileOutputStream small = new FileOutputStream(
                            System.getProperty("user.dir") + "\\pic\\" + time + "small.jpg");
                    FileOutputStream big = new FileOutputStream(
                            System.getProperty("user.dir") + "\\pic\\" + time + "big.jpg");

                    if (strFaceSnapInfo.dwFacePicLen > 0) {
                        try {
                            small.write(strFaceSnapInfo.pBuffer1.getByteArray(0, strFaceSnapInfo.dwFacePicLen), 0,
                                    strFaceSnapInfo.dwFacePicLen);
                            small.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (strFaceSnapInfo.dwFacePicLen > 0) {
                        try {
                            big.write(strFaceSnapInfo.pBuffer2.getByteArray(0, strFaceSnapInfo.dwBackgroundPicLen), 0,
                                    strFaceSnapInfo.dwBackgroundPicLen);
                            big.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                break;
            case HCNetSDK.COMM_SNAP_MATCH_ALARM:
                // 人脸黑名单比对报警
                HCNetSDK.NET_VCA_FACESNAP_MATCH_ALARM strFaceSnapMatch = new HCNetSDK.NET_VCA_FACESNAP_MATCH_ALARM();
                strFaceSnapMatch.write();
                Pointer pFaceSnapMatch = strFaceSnapMatch.getPointer();
                pFaceSnapMatch.write(0, pAlarmInfo.getByteArray(0, strFaceSnapMatch.size()), 0,
                        strFaceSnapMatch.size());
                strFaceSnapMatch.read();

                if ((strFaceSnapMatch.dwSnapPicLen > 0) && (strFaceSnapMatch.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = System.getProperty("user.dir") + "\\pic\\" + newName + "_pSnapPicBuffer"
                                + ".jpg";
                        fout = new FileOutputStream(filename);
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strFaceSnapMatch.pSnapPicBuffer.getByteBuffer(offset,
                                strFaceSnapMatch.dwSnapPicLen);
                        byte[] bytes = new byte[strFaceSnapMatch.dwSnapPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if ((strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen > 0) && (strFaceSnapMatch.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = System.getProperty("user.dir") + "\\pic\\" + newName
                                + "_struSnapInfo_pBuffer1" + ".jpg";
                        fout = new FileOutputStream(filename);
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strFaceSnapMatch.struSnapInfo.pBuffer1.getByteBuffer(offset,
                                strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen);
                        byte[] bytes = new byte[strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if ((strFaceSnapMatch.struBlackListInfo.dwBlackListPicLen > 0)
                        && (strFaceSnapMatch.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = System.getProperty("user.dir") + "\\pic\\" + newName + "_fSimilarity_"
                                + strFaceSnapMatch.fSimilarity + "_struBlackListInfo_pBuffer1" + ".jpg";
                        fout = new FileOutputStream(filename);
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strFaceSnapMatch.struBlackListInfo.pBuffer1.getByteBuffer(offset,
                                strFaceSnapMatch.struBlackListInfo.dwBlackListPicLen);
                        byte[] bytes = new byte[strFaceSnapMatch.struBlackListInfo.dwBlackListPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                sAlarmType = sAlarmType + "：人脸黑名单比对报警，相识度：" + strFaceSnapMatch.fSimilarity + "，黑名单姓名："
                        + new String(strFaceSnapMatch.struBlackListInfo.struBlackListInfo.struAttribute.byName, "GBK")
                                .trim()
                        + "，\n黑名单证件信息："
                        + new String(
                                strFaceSnapMatch.struBlackListInfo.struBlackListInfo.struAttribute.byCertificateNumber)
                                        .trim();

                // 获取人脸库ID
                byte[] FDIDbytes;
                if ((strFaceSnapMatch.struBlackListInfo.dwFDIDLen > 0)
                        && (strFaceSnapMatch.struBlackListInfo.pFDID != null)) {
                    ByteBuffer FDIDbuffers = strFaceSnapMatch.struBlackListInfo.pFDID.getByteBuffer(0,
                            strFaceSnapMatch.struBlackListInfo.dwFDIDLen);
                    FDIDbytes = new byte[strFaceSnapMatch.struBlackListInfo.dwFDIDLen];
                    FDIDbuffers.rewind();
                    FDIDbuffers.get(FDIDbytes);
                    sAlarmType = sAlarmType + "，人脸库ID:" + new String(FDIDbytes).trim();
                }
                // 获取人脸图片ID
                byte[] PIDbytes;
                if ((strFaceSnapMatch.struBlackListInfo.dwPIDLen > 0)
                        && (strFaceSnapMatch.struBlackListInfo.pPID != null)) {
                    ByteBuffer PIDbuffers = strFaceSnapMatch.struBlackListInfo.pPID.getByteBuffer(0,
                            strFaceSnapMatch.struBlackListInfo.dwPIDLen);
                    PIDbytes = new byte[strFaceSnapMatch.struBlackListInfo.dwPIDLen];
                    PIDbuffers.rewind();
                    PIDbuffers.get(PIDbytes);
                    sAlarmType = sAlarmType + "，人脸图片ID:" + new String(PIDbytes).trim();
                }
                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));
                break;
            case HCNetSDK.COMM_ALARM_ACS: // 门禁主机报警信息
                HCNetSDK.NET_DVR_ACS_ALARM_INFO strACSInfo = new HCNetSDK.NET_DVR_ACS_ALARM_INFO();
                strACSInfo.write();
                Pointer pACSInfo = strACSInfo.getPointer();
                pACSInfo.write(0, pAlarmInfo.getByteArray(0, strACSInfo.size()), 0, strACSInfo.size());
                strACSInfo.read();

                sAlarmType = sAlarmType + "：门禁主机报警信息，卡号：" + new String(strACSInfo.struAcsEventInfo.byCardNo).trim()
                        + "，卡类型：" + strACSInfo.struAcsEventInfo.byCardType + "，报警主类型：" + strACSInfo.dwMajor + "，报警次类型："
                        + strACSInfo.dwMinor;

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));

                if (strACSInfo.dwPicDataLen > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = ".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_byCardNo["
                                + new String(strACSInfo.struAcsEventInfo.byCardNo).trim() + "_" + newName + "_Acs.jpg";
                        fout = new FileOutputStream(filename);
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strACSInfo.pPicData.getByteBuffer(offset, strACSInfo.dwPicDataLen);
                        byte[] bytes = new byte[strACSInfo.dwPicDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_ID_INFO_ALARM: // 身份证信息
                HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM strIDCardInfo = new HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM();
                strIDCardInfo.write();
                Pointer pIDCardInfo = strIDCardInfo.getPointer();
                pIDCardInfo.write(0, pAlarmInfo.getByteArray(0, strIDCardInfo.size()), 0, strIDCardInfo.size());
                strIDCardInfo.read();

                sAlarmType = sAlarmType + "：门禁身份证刷卡信息，身份证号码：" + new String(strIDCardInfo.struIDCardCfg.byIDNum).trim()
                        + "，姓名：" + new String(strIDCardInfo.struIDCardCfg.byName).trim() + "，报警主类型："
                        + strIDCardInfo.dwMajor + "，报警次类型：" + strIDCardInfo.dwMinor;

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));

                // 身份证图片
                if (strIDCardInfo.dwPicDataLen > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = ".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_byCardNo["
                                + new String(strIDCardInfo.struIDCardCfg.byIDNum).trim() + "_" + newName
                                + "_IDInfoPic.jpg";
                        fout = new FileOutputStream(filename);
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strIDCardInfo.pPicData.getByteBuffer(offset, strIDCardInfo.dwPicDataLen);
                        byte[] bytes = new byte[strIDCardInfo.dwPicDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                // 抓拍图片
                if (strIDCardInfo.dwCapturePicDataLen > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = ".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_byCardNo["
                                + new String(strIDCardInfo.struIDCardCfg.byIDNum).trim() + "_" + newName
                                + "_IDInfoCapturePic.jpg";
                        fout = new FileOutputStream(filename);
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strIDCardInfo.pCapturePicData.getByteBuffer(offset,
                                strIDCardInfo.dwCapturePicDataLen);
                        byte[] bytes = new byte[strIDCardInfo.dwCapturePicDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_UPLOAD_AIOP_VIDEO: // 设备支持AI开放平台接入，上传视频检测数据
                HCNetSDK.NET_AIOP_VIDEO_HEAD struAIOPVideo = new HCNetSDK.NET_AIOP_VIDEO_HEAD();
                struAIOPVideo.write();
                Pointer pAIOPVideo = struAIOPVideo.getPointer();
                pAIOPVideo.write(0, pAlarmInfo.getByteArray(0, struAIOPVideo.size()), 0, struAIOPVideo.size());
                struAIOPVideo.read();

                String strTime = "" + String.format("%04d", struAIOPVideo.struTime.wYear)
                        + String.format("%02d", struAIOPVideo.struTime.wMonth)
                        + String.format("%02d", struAIOPVideo.struTime.wDay)
                        + String.format("%02d", struAIOPVideo.struTime.wHour)
                        + String.format("%02d", struAIOPVideo.struTime.wMinute)
                        + String.format("%02d", struAIOPVideo.struTime.wSecond)
                        + String.format("%03d", struAIOPVideo.struTime.wMilliSec);

                sAlarmType = sAlarmType + "：AI开放平台接入，上传视频检测数据，通道号:" + struAIOPVideo.dwChannel + ", 时间:" + strTime;

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));

                if (struAIOPVideo.dwAIOPDataSize > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = ".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_" + newName
                                + "_AIO_VideoData.json";
                        fout = new FileOutputStream(filename);
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struAIOPVideo.pBufferAIOPData.getByteBuffer(offset,
                                struAIOPVideo.dwAIOPDataSize);
                        byte[] bytes = new byte[struAIOPVideo.dwAIOPDataSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (struAIOPVideo.dwPictureSize > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = ".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_" + newName
                                + "_AIO_VideoPic.jpg";
                        fout = new FileOutputStream(filename);
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struAIOPVideo.pBufferPicture.getByteBuffer(offset,
                                struAIOPVideo.dwPictureSize);
                        byte[] bytes = new byte[struAIOPVideo.dwPictureSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_UPLOAD_AIOP_PICTURE: // 设备支持AI开放平台接入，上传视频检测数据
                HCNetSDK.NET_AIOP_PICTURE_HEAD struAIOPPic = new HCNetSDK.NET_AIOP_PICTURE_HEAD();
                struAIOPPic.write();
                Pointer pAIOPPic = struAIOPPic.getPointer();
                pAIOPPic.write(0, pAlarmInfo.getByteArray(0, struAIOPPic.size()), 0, struAIOPPic.size());
                struAIOPPic.read();

                String strPicTime = "" + String.format("%04d", struAIOPPic.struTime.wYear)
                        + String.format("%02d", struAIOPPic.struTime.wMonth)
                        + String.format("%02d", struAIOPPic.struTime.wDay)
                        + String.format("%02d", struAIOPPic.struTime.wHour)
                        + String.format("%02d", struAIOPPic.struTime.wMinute)
                        + String.format("%02d", struAIOPPic.struTime.wSecond)
                        + String.format("%03d", struAIOPPic.struTime.wMilliSec);

                sAlarmType = sAlarmType + "：AI开放平台接入，上传图片检测数据，通道号:" + new String(struAIOPPic.szPID) + ", 时间:"
                        + strPicTime;

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));

                if (struAIOPPic.dwAIOPDataSize > 0) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = ".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_" + newName
                                + "_AIO_PicData.json";
                        fout = new FileOutputStream(filename);
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struAIOPPic.pBufferAIOPData.getByteBuffer(offset,
                                struAIOPPic.dwAIOPDataSize);
                        byte[] bytes = new byte[struAIOPPic.dwAIOPDataSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_ISAPI_ALARM: // ISAPI协议报警信息
                HCNetSDK.NET_DVR_ALARM_ISAPI_INFO struEventISAPI = new HCNetSDK.NET_DVR_ALARM_ISAPI_INFO();
                struEventISAPI.write();
                Pointer pEventISAPI = struEventISAPI.getPointer();
                pEventISAPI.write(0, pAlarmInfo.getByteArray(0, struEventISAPI.size()), 0, struEventISAPI.size());
                struEventISAPI.read();

                sAlarmType = sAlarmType + "：ISAPI协议报警信息, 数据格式:" + struEventISAPI.byDataType + ", 图片个数:"
                        + struEventISAPI.byPicturesNumber;

                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));

                SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
                String curTime = sf1.format(new Date());
                FileOutputStream foutdata;
                try {
                    String jsonfilename = ".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + curTime + "_ISAPI_Alarm_"
                            + ".json";
                    foutdata = new FileOutputStream(jsonfilename);
                    // 将字节写入文件
                    ByteBuffer jsonbuffers = struEventISAPI.pAlarmData.getByteBuffer(0, struEventISAPI.dwAlarmDataLen);
                    byte[] jsonbytes = new byte[struEventISAPI.dwAlarmDataLen];
                    jsonbuffers.rewind();
                    jsonbuffers.get(jsonbytes);
                    foutdata.write(jsonbytes);
                    foutdata.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                for (int i = 0; i < struEventISAPI.byPicturesNumber; i++) {
                    HCNetSDK.NET_DVR_ALARM_ISAPI_PICDATA struPicData = new HCNetSDK.NET_DVR_ALARM_ISAPI_PICDATA();
                    struPicData.write();
                    Pointer pPicData = struPicData.getPointer();
                    pPicData.write(0,
                            struEventISAPI.pPicPackData.getByteArray(i * struPicData.size(), struPicData.size()), 0,
                            struPicData.size());
                    struPicData.read();

                    FileOutputStream fout;
                    try {
                        String filename = ".\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + curTime + "_ISAPIPic_"
                                + i + "_" + new String(struPicData.szFilename).trim() + ".jpg";
                        fout = new FileOutputStream(filename);
                        // 将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struPicData.pPicData.getByteBuffer(offset, struPicData.dwPicLen);
                        byte[] bytes = new byte[struPicData.dwPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            default:
                newRow[0] = dateFormat.format(today);
                // 报警类型
                newRow[1] = sAlarmType;
                // 报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                System.out.println(JSONObject.toJsonString(newRow));
                break;
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public class FMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31 {
        // 报警信息回调函数

        public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen,
                Pointer pUser) {
            AlarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
            return true;
        }
    }

    public class FMSGCallBack implements HCNetSDK.FMSGCallBack {
        // 报警信息回调函数

        public void invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen,
                Pointer pUser) {
            AlarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
        }
    }
}
