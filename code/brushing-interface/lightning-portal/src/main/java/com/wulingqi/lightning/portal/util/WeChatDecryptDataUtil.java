package com.wulingqi.lightning.portal.util;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class WeChatDecryptDataUtil {
	
	private static boolean hasInited = false;

	public static String decrypt(String sessionKey, String iv, String encryptData) {
		String decryptString = "";
		init();
		byte[] sessionKeyByte = Base64.decodeBase64(sessionKey);
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

	public static void init() {
		if (hasInited) {
			return;
		}
		Security.addProvider(new BouncyCastleProvider());
		hasInited = true;
	}

}
