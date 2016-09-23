package com.abive.framework.security;

import com.abive.framework.config.AppConfig;
import com.abive.util.security.RSA;

import java.io.*;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;


/**
 * key
 * @copyright www.abive.com
 * @author ranjc
 * 2012-12-24  上午10:56:33
 * 
 * Function :
 */
public class RSAComponent {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RSAComponent.class);

    
    /**
     * RSA Key
     */
    private static KeyPair rsaKeyPair;

    
    /**
     * 初始化方法
     */
    private static void init(){
        logger.info("init Key start...");
        initRsaKeyPairFromFile();
        if(rsaKeyPair==null){
            createRsaKeyPair();
        }
        logger.info("init Key end...");
    }
    
    //创建文件夹
    private static void makeDir(File dir) {
        if(! dir.getParentFile().exists()) {  
            makeDir(dir.getParentFile());  
        }  
        dir.mkdir();  
    } 
    
    /**
     * 初始化RSA KeyPair
     */
    private static void createRsaKeyPair(){
        logger.info("create rsaKey and save to file ...,rsaKeyPairPath:{}",AppConfig.getDiffer().getRsaKeyPath());
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            File f = new File(AppConfig.getDiffer().getRsaKeyPath());
            //首先检测文件夹是否存在
            if(!f.getParentFile().exists()){
            	makeDir(f.getParentFile());
            }
            //如果文件不存在则创建文件
            if(!f.exists()){
                f.createNewFile();
            }
            rsaKeyPair = RSA.generateKeyPair();
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            //生成密钥
            oos.writeObject(rsaKeyPair);
        }
        catch (Exception e) {
            logger.error("createRsaKeyPair Error",e);
        }finally{
            try {
            	if(null != oos){
            		oos.close();
            	}
            }
            catch (IOException e) {
                logger.error("close File Error",e);
            }
            try {
            	if(null != oos){
            		fos.close();
            	}
            }
            catch (IOException e) {
                logger.error("close File Error",e);
            }
        }
    }
    
    /**
     * 从文件中读取KeyPair
     */
    private static void initRsaKeyPairFromFile(){
        logger.info("init rsaKey from file ...");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            File f = new File(AppConfig.getDiffer().getRsaKeyPath());
            if(f.exists()){
                fis = new FileInputStream(f);
                ois = new ObjectInputStream(fis);
                rsaKeyPair= (KeyPair) ois.readObject();
            }
        }
        catch (Exception e) {
            logger.error("initRsaKeyPairFromFile Error",e);
        }finally{
            try {
                if(ois!=null){
                    ois.close();
                }
            }
            catch (IOException e) {
                logger.error("close File Error",e);
            } 
            try {
                if(fis!=null){
                    fis.close();
                }
            }
            catch (IOException e) {
                logger.error("close File Error",e);
            }
        }
    }

    /**
     * 获取公钥
     * @return
     */
    public static RSAPublicKey getPublicKey(){
        if (null == rsaKeyPair){
            init();
        }

        return (RSAPublicKey)rsaKeyPair.getPublic();
    }

    /**
     * 加密文件
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data) throws Exception{

        if (null == rsaKeyPair){
            init();
        }

        return RSA.encrypt(rsaKeyPair.getPublic(),data);
    }

    /**
     * 解密文件
     * @param raw
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] raw) throws Exception{

        if (null == rsaKeyPair){
            init();
        }

        return RSA.decrypt(rsaKeyPair.getPrivate(),raw);
    }

    /**
     * 解密字符串
     * @param str
     * @return
     * @throws Exception
     */
    public static String decryptString( String str) throws Exception{
        if (null == rsaKeyPair){
            init();
        }

        return RSA.decryptString(rsaKeyPair.getPrivate(),str);
    }

    /**
     * main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        RSAComponent pmpKey = new RSAComponent();
//        pmpKey.init();
//
//        RSAPublicKey publicKey = (RSAPublicKey)pmpKey.getRsaKeyPair().getPublic();
//        String modulus  = publicKey.getModulus().toString(16);
//        String publicExponent = publicKey.getPublicExponent().toString(16);
//
//        System.out.println("modulus:" + modulus + "\npublicExponent:" + publicExponent);
//
//        PrivateKey privateKey = pmpKey.getRsaKeyPair().getPrivate();
//
//        String pwd = "123";
//
//        RSAPublicKey publicKey2 = RSA.generateRSAPublicKey(modulus.getBytes(), publicExponent.getBytes());
//
//
//        System.out.println(publicKey.toString() + publicKey2.toString());
//
//
//        byte[] pwdTemp = RSA.encrypt(publicKey2, pwd.getBytes());
//
//        byte[] pwd2 = RSA.decrypt(privateKey, pwdTemp);
//
//        System.out.println(new String(pwdTemp));
//        System.out.println(new String(pwd2));

    }

}
