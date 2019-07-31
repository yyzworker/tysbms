package com.tys.core;

import com.tys.entity.wx.WXPhoneInfo;
import com.tys.entity.wx.WXUserInfo;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;


/**
 * @author ：我是金角大王
 * @date ：Created in 2019-3-11 9:43
 */
@Service
public class WXBizDataCrypt {

    public WXPhoneInfo getWXphoneInfo(String sessionKey, String encryptData, String iv,String appId){
        String decrypt = decrypt(sessionKey, iv, encryptData);
        WXPhoneInfo wxui = JSONObject.parseObject(decrypt, WXPhoneInfo.class);
        if(!appId.equals(wxui.getWatermark().getAppid())){
            return null;
        }
        return wxui;
    }

    public WXUserInfo getWXuserInfo(String sessionKey, String encryptData, String iv,String appId){
        String decrypt = decrypt(sessionKey, iv, encryptData);
        WXUserInfo wxui = JSONObject.parseObject(decrypt, WXUserInfo.class);
        if(!appId.equals(wxui.getWatermark().getAppid())){
            return null;
        }
        return wxui;
    }

    private static boolean hasInited = false;

    public static void init() {
        if (hasInited) {
            return;
        }
        Security.addProvider(new BouncyCastleProvider());
        hasInited = true;
    }

    public static String decrypt(String session_key, String iv, String encryptData) {
        String decryptString = "";
        init();
        byte[] sessionKeyByte = Base64.decodeBase64(session_key);
        byte[] ivByte = Base64.decodeBase64(iv);
        byte[] encryptDataByte = Base64.decodeBase64(encryptData);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key key = new SecretKeySpec(sessionKeyByte, "AES");
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("AES");
            algorithmParameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameters);
            byte[] bytes = cipher.doFinal(encryptDataByte);
            decryptString = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptString;
    }


}
