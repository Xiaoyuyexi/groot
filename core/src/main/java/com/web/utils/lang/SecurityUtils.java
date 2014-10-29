package com.web.utils.lang;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class SecurityUtils {
	
	public static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish
	
	public static String EncoderByMd5(String str) {
		return SecurityUtils.EncoderByMd5(str, false);
	}
	
	
	
	public static String EncoderByMd5(String str,boolean isLower) {
		String encoder = null;
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			if(isLower){
				encoder = StringUtils.binaryToHexString(md5.digest(str.getBytes("utf-8"))).toLowerCase();
			}else{
				BASE64Encoder base64en = new BASE64Encoder();
				encoder = base64en.encode(md5.digest(str.getBytes("utf-8")));
			}
			md5.digest(str.getBytes("utf-8")).toString().toLowerCase();
		} catch (UnsupportedEncodingException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		return encoder;
	}
	
	public static String encrypt(RSAPublicKey publicKey,String src) throws NoSuchAlgorithmException, NoSuchPaddingException,
		InvalidKeyException, IllegalBlockSizeException, BadPaddingException{  
		if(publicKey!=null && src != null){  
			//Cipher负责完成加密或解密工作，基于RSA  
			Cipher cipher = Cipher.getInstance("RSA");  
			//根据公钥，对Cipher对象进行初始化  
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			
			byte[] resultBytes = cipher.doFinal(src.getBytes());
			return StringUtils.binaryToHexString(resultBytes);  
		}  
		return null;  
	}
	
	public static String decrypt(RSAPrivateKey privateKey,String encoder) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{  
        if(privateKey!=null && encoder != null){  
            //Cipher负责完成加密或解密工作，基于RSA  
            Cipher cipher = Cipher.getInstance("RSA");  
            //根据公钥，对Cipher对象进行初始化  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] srcBytes = StringUtils.hexStringToBinary(encoder);
            byte[] resultBytes = cipher.doFinal(srcBytes);  
            return new String(resultBytes);  
        }  
        return null;  
    }
	
	public static KeyPair keyPairGeneratorByRSA() throws NoSuchAlgorithmException{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        //初始化密钥对生成器，密钥大小为1024位  
        keyPairGen.initialize(512);  
        //生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();
        try {
			FileOutputStream  o = new FileOutputStream("d:\\RsaKeyPair.properties");
			ObjectOutputStream oos = new ObjectOutputStream(o);  
			oos.writeObject(keyPair);
			
			o = new FileOutputStream("d:\\RsaPublicKey.properties"); 
			oos = new ObjectOutputStream(o);  
			oos.writeObject(keyPair.getPublic());
			
	        oos.close();  
	        o.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return keyPair;
        //得到私钥  
//        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();               
//        //得到公钥  
//        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();  
	}
    
    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    public static byte[] encrypt3DES(byte[] keybyte, byte[] src,boolean isPadding) {
       try {
    	   String alg = "DESede";
    		if(!isPadding){
    			alg = "DESede/ECB/NoPadding";
    		}
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //加密
            Cipher c1 = Cipher.getInstance(alg);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
  //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    public static byte[] encrypt3DES(byte[] keybyte, byte[] src) {
    	return encrypt3DES(keybyte,src,true);
    }
    
  //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    public static byte[] encrypt3DESNoPadding(byte[] keybyte, byte[] src) {
    	return encrypt3DES(keybyte,src,false);
    }

    //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    public static byte[] decrypt3DES(byte[] keybyte, byte[] src) {      
        return decrypt3DES(keybyte,src,true);
    }
    
  //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    public static byte[] decrypt3DES(byte[] keybyte, byte[] src,boolean isPadding) {
    try {
    		String alg = "DESede";
    		if(!isPadding){
    			alg = "DESede/ECB/NoPadding";
    		}
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //解密
            Cipher c1 = Cipher.getInstance(alg);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    
  //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    public static byte[] decrypt3DESNoPadding(byte[] keybyte, byte[] src) {  
        return decrypt3DES(keybyte,src,false);
    }
    
    /**
	 * 解密dataKey
	 * @param zmk
	 * @param value
	 * @return
	 */
	public static byte[] decipheringDataKey(String zmk,String workKey) {
		byte[] key_byte = StringUtils.hexStringToBinary(workKey);
		byte[] key = countKey(zmk);
		byte[] result = SecurityUtils.decrypt3DES(key, key_byte,false);
		byte[] byte_1 = new byte[24];
		for (int i = 0; i < 24; i++) {
			if (i < 16) {
				byte_1[i] = result[i];
			} else {
				byte_1[i] = byte_1[i-16];
			}
		}
		return byte_1;
	}
	private static byte[] countKey(String mackey) {
		mackey = mackey + mackey.substring(0, 16);
		byte[] result = StringUtils.hexStringToBinary(mackey);
		return result;
	}
	/**
	 * 解密pinKey
	 * @param zmk
	 * @param value
	 * @return
	 */
	public static byte[] decipheringPinKey(String zmk,String workKey) {
		byte[] key_byte = StringUtils.hexStringToBinary(workKey);
		byte[] key = countKey(zmk);
		byte[] result = SecurityUtils.decrypt3DES(key, key_byte,false);
		byte[] byte_1 = new byte[24];
		for (int i = 0; i < 24; i++) {
			if (i < 16) {
				byte_1[i] = result[i+16];
			} else {
				byte_1[i] = byte_1[i-16];
			}
		}
		return byte_1;
	}
	/**
	 * 解密MACKEY
	 * @return
	 */
	public static byte[] decipheringMacKey(String zmk,String workKey){
		byte[] key_byte = StringUtils.hexStringToBinary(workKey);
		byte[] key = countKey(zmk);
		byte[] result = SecurityUtils.decrypt3DES(key, key_byte,false);
		byte[] byte_1=new byte[24];
		for (int i = 0; i < 24; i++) {
			if(i<16){
				byte_1[i]=result[i+32];
			}else{
				byte_1[i]=byte_1[i-16];
			}
		}
		return byte_1;
	}
    
	public static void main(String[] args) throws Exception {
		
		
		//System.out.println(SecurityUtils.EncoderByMd5("11", true));
//		SecurityUtils.keyPairGeneratorByRSA();
		
//		System.out.println("加密:1");
//		byte[] encoder = SecurityUtils.EncoderByAES("1");
//		System.out.println("密文:"+encoder);
////		2C059073A6F225B9B90BCCD04CF9D65C
//		System.out.println("解密:"+SecurityUtils.DecryptorByAES(encoder));
		//final MessageDigest md = MessageDigest.getInstance("md5");
		//final byte[] digestOfPassword = md.digest("628B0E4A89E65AED1D4CFF27996A3F85".getBytes("utf-8"));
		final byte[] digestOfPassword = StringUtils.hexStringToBinary("628B0E4A89E65AED1D4CFF27996A3F85");
    	final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
    	for (int j = 0, k = 16; j < 8;) {
    		keyBytes[k++] = keyBytes[j++];
    	}
    	byte[] encryStr = encrypt3DES(keyBytes, StringUtils.hexStringToBinary("0808080808080808"),false);
    	System.out.println(StringUtils.binaryToHexString(encryStr));
    	//byte[] encryStr = "DD3AD8766C23E703".getBytes();
		System.out.println(StringUtils.binaryToHexString(decrypt3DES(keyBytes, encryStr,false)));
	}  
}
