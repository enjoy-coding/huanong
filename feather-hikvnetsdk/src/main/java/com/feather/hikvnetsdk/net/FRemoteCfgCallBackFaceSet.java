package com.feather.hikvnetsdk.net;

import com.sun.jna.Pointer;

//下发人脸，回调函数实现类
public class FRemoteCfgCallBackFaceSet implements HCNetSDK.FRemoteConfigCallback {
    public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData) {
        switch (dwType) {
        case 0:// NET_SDK_CALLBACK_TYPE_STATUS
            HCNetSDK.REMOTECONFIGSTATUS_CARD struCfgStatus = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
            struCfgStatus.write();
            Pointer pCfgStatus = struCfgStatus.getPointer();
            pCfgStatus.write(0, lpBuffer.getByteArray(0, struCfgStatus.size()), 0, struCfgStatus.size());
            struCfgStatus.read();

            int iStatus = 0;
            for (int i = 0; i < 4; i++) {
                int ioffset = i * 8;
                int iByte = struCfgStatus.byStatus[i] & 0xff;
                iStatus = iStatus + (iByte << ioffset);
            }

            switch (iStatus) {
            case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                System.out.println("下发人脸参数成功,dwStatus:" + iStatus);
                break;
            case 1002:
                int iErrorCode = 0;
                for (int i = 0; i < 4; i++) {
                    int ioffset = i * 8;
                    int iByte = struCfgStatus.byErrorCode[i] & 0xff;
                    iErrorCode = iErrorCode + (iByte << ioffset);
                }
                System.out.println("下发人脸参数失败, dwStatus:" + iStatus + "错误号:" + iErrorCode);
                break;
            }
            break;
        case 2:// 获取状态数据
            HCNetSDK.NET_DVR_FACE_PARAM_STATUS m_struFaceStatus = new HCNetSDK.NET_DVR_FACE_PARAM_STATUS();
            m_struFaceStatus.write();
            Pointer pStatusInfo = m_struFaceStatus.getPointer();
            pStatusInfo.write(0, lpBuffer.getByteArray(0, m_struFaceStatus.size()), 0, m_struFaceStatus.size());
            m_struFaceStatus.read();
            String str = new String(m_struFaceStatus.byCardNo).trim();
            System.out.println("下发人脸数据关联的卡号:" + str + ",人脸读卡器状态:" + m_struFaceStatus.byCardReaderRecvStatus[0]
                    + ",错误描述:" + new String(m_struFaceStatus.byErrorMsg).trim());
            break;
        default:
            break;
        }
    }
}
