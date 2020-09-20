package com.feather.hikvnetsdk.net;

public class Test {
    public static void main(String[] args) {
        new Thxx().start();
        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                break;
            }
        }
    }

    public static class Thxx extends Thread {
        public void run() {
            HCNetDeviceUtil hcNetDeviceUtil = new HCNetDeviceUtil();

            int lUserID = hcNetDeviceUtil.loginDevice("192.168.1.51", "8000", "admin", "bjx123456");
            // HCNetSDK.INSTANCE.NET_DVR_Logout(lUserID);
            System.out.println(lUserID);
        }
    }
}
