package com.feather.hikvnetsdk.net;

import com.feather.common.json.JSONObject;
import com.sun.jna.Pointer;

public class FMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31 {
    private HCNetDeviceUtil hcNetDevice;

    public HCNetDeviceUtil getHCNetDeviceUtil() {
        return hcNetDevice;
    }

    public void setHCNetDeviceUtil(HCNetDeviceUtil obj) {
        hcNetDevice = obj;
    }

    public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen,
            Pointer pUser) {
        System.out.println("=================== FMSGCallBack_V31 =====================");
        System.out.println("lCommand: " + lCommand);
        System.out.println("pAlarmer: " + JSONObject.toJsonString(pAlarmer));
        System.out.println("dwBufLen: " + dwBufLen);
        if (lCommand == 20482) {
            HCNetSDK.NET_DVR_ACS_ALARM_INFO strACSInfo = new HCNetSDK.NET_DVR_ACS_ALARM_INFO();
            strACSInfo.write();
            Pointer pACSInfo = strACSInfo.getPointer();
            pACSInfo.write(0L, pAlarmInfo.getByteArray(0L, strACSInfo.size()), 0, strACSInfo.size());
            strACSInfo.read();

            System.out.println("strACSInfo: " + JSONObject.toJsonString(strACSInfo));
            // 5代表主事件类型 次事件类型75对应的16进制0x4b代表刷脸通过
            if ((strACSInfo.dwMajor == 5) && (strACSInfo.dwMinor == 75)) {
                String ip = new String(pAlarmer.sDeviceIP).trim();
                String punchTime = strACSInfo.struTime.toStringTime();
                // 工号如果以0开头的设备会将0给去掉，这是个坑
                String empCode = Integer.toString(strACSInfo.struAcsEventInfo.dwEmployeeNo);
                String cardNo = new String(strACSInfo.struAcsEventInfo.byCardNo).trim();
                System.out.println("======设备ip：====" + ip);
                System.out.println("======编号：====" + empCode);
                System.out.println("======卡号：====" + cardNo);
                System.out.println("======时间：====" + punchTime);

                HCNetDeviceUtil hcNetDevice = new HCNetDeviceUtil();

                hcNetDevice.getFaceInfo(hcNetDevice.getUserId());
            }
        }

        switch (lCommand) {
        case 20482:
            HCNetSDK.NET_DVR_ACS_ALARM_INFO strACSInfo = new HCNetSDK.NET_DVR_ACS_ALARM_INFO();
            strACSInfo.write();
            Pointer pACSInfo = strACSInfo.getPointer();
            pACSInfo.write(0L, pAlarmInfo.getByteArray(0L, strACSInfo.size()), 0, strACSInfo.size());
            strACSInfo.read();

            String ip = new String(pAlarmer.sDeviceIP).trim();
            String lUserID = Integer.toString(pAlarmer.lUserID);
            String time = strACSInfo.struTime.toStringTime();
            String cardNo = new String(strACSInfo.struAcsEventInfo.byCardNo);
            String empCode = Integer.toString(strACSInfo.struAcsEventInfo.dwEmployeeNo);
            System.out.println("======设备ip：====" + ip);
            System.out.println("======编号：====" + empCode);
            System.out.println("======卡号：====" + cardNo);
            System.out.println("======用户id：====" + lUserID);
            System.out.println("======时间：====" + time);
            break;
        }
        return true;
    }
}
