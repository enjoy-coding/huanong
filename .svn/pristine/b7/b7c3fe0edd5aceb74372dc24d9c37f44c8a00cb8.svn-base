package com.feather.hikvnetsdk.net.c;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.feather.hikvnetsdk.net.HCNetSDK;
import com.feather.hikvnetsdk.net.HCNetSDK.NET_DVR_ALARMER;
import com.sun.jna.Pointer;

/**
 * 
 */
class FMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31 {
    // 报警信息回调函数

    @Override
    public boolean invoke(int lCommand, NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        AlarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
        return true;
    }

    public void AlarmDataHandle(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen,
            Pointer pUser) {
        switch (lCommand) {

        case HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT: // 实时人脸抓拍上传
            System.out.println("UPLOAD_FACESNAP_Alarm");
            HCNetSDK.NET_VCA_FACESNAP_RESULT strFaceSnapInfo = new HCNetSDK.NET_VCA_FACESNAP_RESULT();
            strFaceSnapInfo.write();
            Pointer pFaceSnapInfo = strFaceSnapInfo.getPointer();
            pFaceSnapInfo.write(0, pAlarmInfo.getByteArray(0, strFaceSnapInfo.size()), 0, strFaceSnapInfo.size());
            strFaceSnapInfo.read();
            System.out.println("人脸评分：" + strFaceSnapInfo.dwFaceScore);
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
                String time = df.format(new Date());// new Date()为获取当前系统时间

                // 人脸图片写文件
                FileOutputStream small = new FileOutputStream("D:\\Picture\\" + time + "small.jpg");
                FileOutputStream big = new FileOutputStream("D:\\Picture\\" + time + "big.jpg");
                try {
                    small.write(strFaceSnapInfo.pBuffer1.getByteArray(0, strFaceSnapInfo.dwFacePicLen), 0,
                            strFaceSnapInfo.dwFacePicLen);
                    small.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    big.write(strFaceSnapInfo.pBuffer2.getByteArray(0, strFaceSnapInfo.dwBackgroundPicLen), 0,
                            strFaceSnapInfo.dwBackgroundPicLen);
                    big.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            break;

        case HCNetSDK.COMM_SNAP_MATCH_ALARM:
            // 人脸黑名单比对报警
            System.out.println("SNAP_MATCH_ALARM");
            HCNetSDK.NET_VCA_FACESNAP_MATCH_ALARM strFaceSnapMatch = new HCNetSDK.NET_VCA_FACESNAP_MATCH_ALARM();
            strFaceSnapMatch.write();
            Pointer pFaceSnapMatch = strFaceSnapMatch.getPointer();
            pFaceSnapMatch.write(0, pAlarmInfo.getByteArray(0, strFaceSnapMatch.size()), 0, strFaceSnapMatch.size());
            strFaceSnapMatch.read();

            if ((strFaceSnapMatch.dwSnapPicLen > 0) && (strFaceSnapMatch.byPicTransType == 0)) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                String newName = sf.format(new Date());
                FileOutputStream fout;
                try {
                    String filename = "D:\\Picture\\" + newName + "_pSnapPicBuffer" + ".jpg";
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
                    String filename = "D:\\Picture\\" + newName + "_struSnapInfo_pBuffer1" + ".jpg";
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
            if ((strFaceSnapMatch.struBlackListInfo.dwBlackListPicLen > 0) && (strFaceSnapMatch.byPicTransType == 0)) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                String newName = sf.format(new Date());
                FileOutputStream fout;
                try {
                    String filename = "D:\\Picture\\" + newName + "_fSimilarity_" + strFaceSnapMatch.fSimilarity
                            + "_struBlackListInfo_pBuffer1" + ".jpg";
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
            break;
        }

    }
}
