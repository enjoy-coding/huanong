package com.feather.hikvnetsdk.net;

import com.sun.jna.Pointer;

//下发卡号，回调函数实现类
public class FRemoteCfgCallBackCardSet implements HCNetSDK.FRemoteConfigCallback {
    public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData) {
        System.out.println("长连接回调获取数据,NET_SDK_CALLBACK_TYPE_STATUS:" + dwType);
        if (dwType == 0) {
            HCNetSDK.REMOTECONFIGSTATUS_CARD struCardStatus = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
            struCardStatus.write();
            Pointer pInfoV30 = struCardStatus.getPointer();
            pInfoV30.write(0, lpBuffer.getByteArray(0, struCardStatus.size()), 0, struCardStatus.size());
            struCardStatus.read();
            int iStatus = 0;
            for (int i = 0; i < 4; i++) {
                int ioffset = i * 8;
                int iByte = struCardStatus.byStatus[i] & 0xff;
                iStatus = iStatus + (iByte << ioffset);
            }

            if (iStatus == 1000) {
                System.out.println("下发卡参数成功,dwStatus:" + iStatus);
            } else if (iStatus == 1002) {
                int iErrorCode = 0;
                for (int i = 0; i < 4; i++) {
                    int ioffset = i * 8;
                    int iByte = struCardStatus.byErrorCode[i] & 0xff;
                    iErrorCode = iErrorCode + (iByte << ioffset);
                }
                System.out.println("下发卡参数失败, dwStatus:" + iStatus + "错误号:" + iErrorCode);
            }
        }
    }
}
