package com.abive.framework.security;

import com.abive.framework.config.AppConfig;
import com.abive.util.security.DES;

/**
 * Created by ranjiangchuan on 15/3/31.
 */
public class DESComponent {

    private static DES _des;

    private static void initDes(){
        if (null == _des){
            _des = new DES(AppConfig.getAbive().getDesKey());
        }
    }

    /**
     * 加密
     * @param strIn
     * @return
     * @throws Exception
     */
    public static String encrypt(String strIn) throws Exception {
        initDes();
        return _des.encrypt(strIn);
    }

    /**
     * 解密
     * @param strIn
     * @return
     * @throws Exception
     */
    public static String decrypt(String strIn) throws Exception{
        initDes();
        return _des.decrypt(strIn);
    }

}
