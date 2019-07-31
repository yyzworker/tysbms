package com.tys.upush;

import com.tys.upush.android.AndroidUnicast;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-3-21 9:57
 */
public class UPush {
    private AndroidUnicast unicast = new AndroidUnicast();

    public void sendMessage(String upushid,String message){
        try {
            unicast.setAppMasterSecret("esp62cb5eon9xraz7jcuv0dglejhpsjn");
            unicast.setPredefinedKeyValue("appkey", "5c88657e3fc195023d00062a");
            unicast.setPredefinedKeyValue("timestamp", Integer.toString((int)(System.currentTimeMillis() / 1000)));
            unicast.setPredefinedKeyValue("device_tokens", upushid);
            unicast.setPredefinedKeyValue("display_type", "message");
            unicast.setPredefinedKeyValue("custom",message);
            unicast.setPredefinedKeyValue("production_mode", "true");
            unicast.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
