package com.feather.hikvnetsdk.net.c;

import com.feather.hikvnetsdk.net.HCNetSDK;
import com.sun.jna.Pointer;

public class Test1 {
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    static int lUserID = -1;// 用户句柄
    static int lAlarmHandle = -1;// 报警布防句柄
    FMSGCallBack_V31 fMSFCallBack_V31 = null;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Test1 test = new Test1();
        hCNetSDK.NET_DVR_Init();

        test.Login();
        test.SetAlarm();
        while (true)
            ;
    }

    public void Login() {
        HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        lUserID = hCNetSDK.NET_DVR_Login_V30("192.168.1.51", (short) 8000, "admin", "bjx123456", m_strDeviceInfo);
        if (lUserID == -1) {
            System.out.println("登录失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("登录成功！");
        }
    }

    public void SetAlarm() {
        if (lAlarmHandle < 0)// 尚未布防,需要布防
        {
            if (fMSFCallBack_V31 == null) {
                fMSFCallBack_V31 = new FMSGCallBack_V31();
                Pointer pUser = null;
                if (!hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(fMSFCallBack_V31, pUser)) {
                    System.out.println("设置回调函数失败!");
                } else {
                    System.out.println("设置回调函数成功!");
                }
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
            m_strAlarmInfo.dwSize = m_strAlarmInfo.size();
            m_strAlarmInfo.byLevel = 1;
            m_strAlarmInfo.byAlarmInfoType = 1;
            m_strAlarmInfo.byDeployType = 1;
            m_strAlarmInfo.write();
            lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V41(lUserID, m_strAlarmInfo);
            if (lAlarmHandle == -1) {
                System.out.println("布防失败，错误号:" + hCNetSDK.NET_DVR_GetLastError());
            } else {
                System.out.println("布防成功");
            }
        }

    }
}
