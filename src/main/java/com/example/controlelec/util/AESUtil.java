package com.example.controlelec.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class AESUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);

    private static byte[] keygen = null; //加密秘钥 密钥长度必须为16位


    public static String encryptData(String content) //加密字符串 其中content为需要加密的内容
    {
        if (keygen == null){
            keygen =  getKey();
        }
        if (null == content || content.length() == 0){
            return "";
        }
        try
        {
            //base64 编码原文
            String content_base64 = Base64Utils.encodeToString(content.getBytes("utf-8"));
            content_base64 = content_base64.replaceAll("\n","");
            content_base64 = content_base64.replaceAll("\r","");

            //加密数据长度不足16位的倍数的话则用'\0'补齐
            int _size = content_base64.length() % 16;
            if (_size != 0)
            {
                for (int i = 0; i < 16 - _size;i++)
                {
                    content_base64 += "\0";
                }
            }
            SecretKeySpec key = new SecretKeySpec(keygen, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            byte[] byteContent = content_base64.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key); // 初始化
            byte[] result = cipher.doFinal(byteContent);
            String code = parseByteToHexStr(result);
            return code;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(" AES encryptData ----  ERROR :"+ e.getMessage());
        } catch (NoSuchPaddingException e) {
            LOGGER.error(" AES encryptData ----  ERROR :"+ e.getMessage());
        } catch (InvalidKeyException e) {
            LOGGER.error(" AES encryptData ----  ERROR :"+ e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(" AES encryptData ----  ERROR :"+ e.getMessage());
        } catch (IllegalBlockSizeException e) {
            LOGGER.error(" AES encryptData ----  ERROR :"+ e.getMessage());
        } catch (BadPaddingException e) {
            LOGGER.error(" AES encryptData ----  ERROR :"+ e.getMessage());
        }
        return null;
    }

    public static String decryptData(String content)  // 解密字符串 其中content为需要解密的内容
    {
        if (keygen == null){
            keygen =  getKey();
        }
        if (null == content || content.length() == 0){
            return "";
        }

        byte[] code = parseHexStrToByte(content);
        try {
            SecretKeySpec key = new SecretKeySpec(keygen, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(code);
            try {
                String res = new String(result, "UTF-8");

                //剔除\0 字符
                Integer indexxxx = res.indexOf('\0');
                if (indexxxx != -1){
                    res = res.substring(0,indexxxx);
                }

                //base64解码还远明文
                byte[] bbvvv = Base64Utils.decodeFromString(res);
                res = new String(bbvvv,"utf-8");

                Integer index = res.lastIndexOf('}');
                if (index != null && index != -1){
                    res = res.substring(0,index + 1);
                }

                return res;
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(" AES decryptData ----  ERROR :"+ e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(" AES decryptData ----  ERROR :"+ e.getMessage());
        } catch (NoSuchPaddingException e) {
            LOGGER.error(" AES decryptData ----  ERROR :"+ e.getMessage());
        } catch (InvalidKeyException e) {
            LOGGER.error(" AES decryptData ----  ERROR :"+ e.getMessage());
        } catch (IllegalBlockSizeException e) {
            LOGGER.error(" AES decryptData ----  ERROR :"+ e.getMessage());
        } catch (BadPaddingException e) {
            LOGGER.error(" AES decryptData ----  ERROR :"+ e.getMessage());
        }catch (Exception e) {
            LOGGER.error(" AES decryptData ----  ERROR :"+ e.getMessage());
        }
        return null;
    }

    private static String parseByteToHexStr(byte buf[]) //流转字符串 辅助函数
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++)
        {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] parseHexStrToByte(String hexStr)  //字符串转流 辅助函数
    {
        if (hexStr.length() < 1)
        {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++)
        {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    private static byte[] getKey(){
        byte[] bs=new byte[16];
        for(byte b=49;b<58;b++){
            bs[b-49]=b;
        }
        for(byte b=48;b<55;b++){
            bs[b-39]=b;
        }
        return bs;
    }

}
