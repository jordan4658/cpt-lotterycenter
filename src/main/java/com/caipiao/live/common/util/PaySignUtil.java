package com.caipiao.live.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.Base64;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PaySignUtil {
	private final static Logger logger = LoggerFactory.getLogger(PaySignUtil.class);

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	public static String MD5Encoder(String s) {
		try {
			byte[] btInput = s.getBytes("utf-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * HMACSHA256加密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String HMACSHA256(String data, String key) {
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte item : array) {
				sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/***
	 * 利用Apache的工具类实现SHA-256加密
	 * @param str 加密后的报文
	 * @return
	 */
	public static String getSHA256Str(String str) {
		MessageDigest messageDigest;
		String encdeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
			encdeStr = Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encdeStr;
	}



	/**
	 * SHA1 安全加密算法
	 */
	public static String signSHA1(String content,String inputCharset)  {
		//获取信息摘要 - 参数字典排序后字符串
		try {
			//指定sha1算法
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(content.getBytes(inputCharset));
			//获取字节数组
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = java.util.Base64.getDecoder().decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] encodedKey = (new BASE64Decoder()).decodeBuffer(key); // 正确方式
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 基本的RSA加签方法<br>
	 * 对data数据进行加签
	 *
	 * @param data 待加签的字符串
	 * @param privateKeyStr 私钥字符串
	 * @return 签名值
	 * @throws Exception
	 */
	public static byte[] sign(String data, String privateKeyStr) throws Exception {
		// 参数检查
		if (data == null || data.length() == 0) {
			throw new Exception("待加签参数为空");
		}
		if (privateKeyStr == null || privateKeyStr.length() == 0) {
			throw new Exception("私钥字符串为空");
		}
		try {
			// 获取根据私钥字符串获取私钥
			PrivateKey privateKey = getPrivateKey(privateKeyStr);
			// 返回签名
			return sign(data.getBytes(StandardCharsets.UTF_8), privateKey);
		} catch (Exception e) {
			throw new Exception("加签异常", e);
		}
	}

	/**
	 *
	 * @param contentBytes
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] contentBytes, PrivateKey privateKey) throws Exception {
		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
		signature.initSign(privateKey);
		signature.update(contentBytes);
		return signature.sign();
	}

	/**
	 *
	 * 功能描述: <br>
	 * 对数据进行验签
	 * 1.对Base64公钥进行解码
	 * 2.数据验签
	 *
	 * @param signData
	 * @param signature
	 * @param publicKey
	 * @return
	 * @throws Exception
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static boolean verify(String signData, String signature, String publicKey) throws Exception {
		// 参数检查
		if (signData == null || signData.length() == 0) {
			throw new Exception("原数据字符串为空");
		}
		if (signature == null || signature.length() == 0) {
			throw new Exception("签名字符串为空");
		}
		if (publicKey == null || publicKey.length() == 0) {
			throw new Exception("公钥字符串为空");
		}
		try {
			// 获取根据公钥字符串获取私钥
			PublicKey pubKey = getPublicKey(publicKey);
			// 返回验证结果
			return verify(signData.getBytes(StandardCharsets.UTF_8), Base64.getDecoder().decode(signature), pubKey);
		} catch (Exception e) {
			throw new Exception("验证签名异常", e);
		}
	}


	/**
	 *
	 * @param contentBytes
	 * @param publicKey
	 * @param signBytes
	 * @return
	 */
	public static boolean verify(byte[] contentBytes, byte[] signBytes, PublicKey publicKey) throws Exception {
		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
		signature.initVerify(publicKey);
		signature.update(contentBytes);
		boolean verify = signature.verify(signBytes);
		return verify;
	}


	/**
	 * sign转为大写
	 *
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String getSign(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// System.out.println(key.toString());
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		// 再拼接秘钥
		String src = res.append("key=" + api_key).toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = MD5Encoder(src);
		// 将字符串全部转为大写
		String rel = md5.toUpperCase();
		logger.info("sign rel={}", rel);
		return rel;
	}

	public static String getSignAppkey(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// System.out.println(key.toString());
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		// 再拼接秘钥
		String src = res.append("appkey=" + api_key).toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = MD5Encoder(src);
		// 将字符串全部转为大写
		String rel = md5.toUpperCase();
		logger.info("sign rel={}", rel);
		return rel;
	}

	/**
	 * sign转为小写
	 *
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String getSignLower(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// System.out.println(key.toString());
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		// 再拼接秘钥
		String src = res.append("key=" + api_key).toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = MD5Encoder(src);
		// 将字符串全部转为小写
		String rel = md5.toLowerCase();
		logger.info("sign rel={}", rel);
		return rel;
	}

	public static String getSignTonghui(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// System.out.println(key.toString());
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		// 再拼接秘钥
		String src = res.append("key=" + api_key).toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = MD5Encoder(src);
		// 将字符串全部转为小写
		String rel = md5.toLowerCase();
		logger.info("sign rel={}", rel);
		return rel;
	}
	/**
	 * 【排序不在后面接key，不做大小写转换】
	 * @Description ifpay支付
	 * @Date 2020/6/22-9:51
	 * @Param [map, api_key]
	 * @return String
	 **/
	public static String getSignOrder(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// System.out.println(key.toString());
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(map.get(key[i]));
		}
		String src = res.toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = MD5Encoder(src);
		logger.info("sign rel={}", md5);
		return md5;
	}

	/**
	 * 不排序 sign转为大写
	 *
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String getUnsortedSign(Map<String, String> map, String api_key) {
		// 生成加密原串
		StringBuffer res = new StringBuffer();

		for (String key : map.keySet()) {
			res.append(key + "=" + map.get(key) + "&");
		}
		// 再拼接秘钥
		String src = res.append("key=" + api_key).toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = MD5Encoder(src);
		// 将字符串全部转为大写
		String rel = md5.toUpperCase();
		logger.info("sign rel={}", rel);
		return rel;
	}


	/**
	 * 【SHA1加密】
	 *
	 * @Author 芥黄
	 * @Description PaySignUtil
	 * @Date 2020/7/13-10:24
	 * @Param [map, api_key]
	 * @return java.lang.String
	 **/
	public static String getSHA1Sign(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			if (map.get(key[i]) != null && !"".equals(map.get(key[i]))) {
				res.append(key[i] + "=" + map.get(key[i]) + "&");
			}
		}
		res.deleteCharAt(res.length() - 1);
		// 再拼接秘钥
		String src = res.append(api_key).toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = signSHA1(src,"utf-8");
		// 将字符串全部转为小写
		String rel = md5.toLowerCase();
		logger.info("sign rel={}", rel);
		return rel;
	}
	/**
	 * 不排序 sign转为小写
	 *
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String getUnsortedSignLower(Map<String, String> map, String api_key) {
		// 生成加密原串
		StringBuffer res = new StringBuffer();

		for (String key : map.keySet()) {
			res.append(key + "=" + map.get(key) + "&");
		}
		// 再拼接秘钥
		String src = res.append("key=" + api_key).toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = MD5Encoder(src);
		// 将字符串全部转为大写
		String rel = md5.toLowerCase();
		logger.info("sign rel={}", rel);
		return rel;
	}

	/**
	 * 利宝支付 不排序 sign转为小写
	 *
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String getUnsortedSignLibao(Map<String, String> map, String api_key) {
		// 生成加密原串
		StringBuffer res = new StringBuffer();

		for (String key : map.keySet()) {
			res.append(key + "=" + map.get(key) + "&");
		}
		res.deleteCharAt(res.length() - 1);
		// 再拼接秘钥
		String src = res.append(api_key).toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = MD5Encoder(src);
		// 将字符串全部转为小写
		String rel = md5.toLowerCase();
		logger.info("sign rel={}", rel);
		return rel;
	}

	/**
	 * 转为小写
	 *
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String getNiuNiuSign(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// System.out.println(key.toString());
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		// 再拼接秘钥
		String src = res.append("api_secret=" + api_key).toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = MD5Encoder(src);
		// 将字符串全部转为小写
		String rel = md5.toLowerCase();
		//// 将字符串全部转为大写
		// String rel = md5.toUpperCase();
		logger.info("sign rel={}", rel);
		return rel;
	}

	/**
	 * 君临
	 *
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String getJunlinSign(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// System.out.println(key.toString());
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		// 再拼接秘钥
		//key直接拼接，前面无须加&key=
		StringBuffer resStr = new StringBuffer();
		String resString = res.toString();
		resStr.append(resString.substring(0, resString.length() - 1));
		resStr.append(api_key);
		String src = resStr.toString();
		logger.info("sign src={}", src);
		String md5 = MD5Encoder(src);
		logger.info("sign rel={}", md5);
		return md5;
	}

	/**
	 *
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String getSignNokey(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// System.out.println(key.toString());
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		StringBuffer resStr = new StringBuffer();
		String resString = res.toString();
		resStr.append(resString.substring(0, resString.length() - 1));
		String src = resStr.toString();
		logger.info("sign src={}", src);
		String md5 = MD5Encoder(src);
		logger.info("sign rel={}", md5);
		return md5;
	}


	public static String getSignHuitong(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// System.out.println(key.toString());
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		StringBuffer resStr = new StringBuffer();
		resStr.append(res.toString());
		resStr.append("secretKey=").append(api_key);
		String src = resStr.toString();
		logger.info("sign src={}", src);
		String md5 = MD5Encoder(src);
		logger.info("sign rel={}", md5);
		return md5;
	}
	/**
	 * 顺旺支付签名
	 *
	 * @param map   参数
	 * @param privateKey 私钥
	 * @return
	 */
	public static String getShunwangSign(Map<String, String> map, String privateKey) throws Exception {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		res = res.deleteCharAt(res.length() - 1);
		String str = getSHA256Str(res.toString()).toLowerCase();
		String rel =  Base64.getEncoder().encodeToString(sign(str, privateKey));
		logger.info("sign rel={}", rel);
		return rel;
	}

	/**
	 * 顺旺支付验签
	 *
	 * @param map       参数
	 * @param sign      签名
	 * @param publicKey 公钥
	 * @return
	 */
	public static boolean verifyShunwangSign(Map<String, String> map, String sign, String publicKey) throws Exception {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		res = res.deleteCharAt(res.length() - 1);
		String str = getSHA256Str(res.toString()).toLowerCase();
		boolean verify = verify(str, sign, publicKey);
		return verify;
	}

	/**
	 * HmacSHA 签名
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String getHmacSHASign(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		res = res.deleteCharAt(res.length() - 1);
		String src = res.toString();
		logger.info("sign src={}", src);
		String md5 = HMACSHA256(src, api_key);
		String rel = md5.toUpperCase();
		logger.info("sign rel={}", rel);
		return rel;
	}

	public static String getHmacSHASignKey(Map<String, String> map, String api_key) {
		// 对参数名按照ASCII升序排序
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			res.append(key[i] + "=" + map.get(key[i]) + "&");
		}
		String src = res.append("key=" + api_key).toString();
		logger.info("sign src={}", src);
		String md5 = HMACSHA256(src, api_key);
		String rel = md5.toUpperCase();
		logger.info("sign rel={}", rel);
		return rel;
	}
	/**
	 * 非空参数不参与签名
	 *
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String buildNonemptySgin(Map<String, String> map, String api_key) {
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		// System.out.println(key.toString());
		// 生成加密原串
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			if (map.get(key[i]) != null && !"".equals(map.get(key[i]))) {
				res.append(key[i] + "=" + map.get(key[i]) + "&");
			}
		}
		// 再拼接秘钥
		String src = res.append("key=" + api_key).toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = MD5Encoder(src);
		// 将字符串全部转为大写
		String rel = md5.toUpperCase();
		logger.info("sign rel={}", rel);
		return rel;
	}

	/**
	 * 哈希算法加密
	 *
	 * @param map
	 * @param api_key
	 * @return
	 */
	public static String getShaSign(Map<String, Object> map, String api_key) {
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			if (map.get(key[i]) != null && !"".equals(map.get(key[i]))) {
				res.append(key[i] + "=" + map.get(key[i]) + "&");
			}
		}
		res.append("key=").append(api_key);

		String dataStr = res.toString();
		logger.info("sign src={}", dataStr);
		String rel = getSha1(dataStr);
		logger.info("sign src={}", rel);

		return rel;
	}

	public static String getSha1(String str){
		if (null == str || 0 == str.length()){
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getSignJuhe(StringBuffer res) {
		// 再拼接秘钥
		String src = res.toString();
		logger.info("sign src={}", src);
		// MD5加密
		String md5 = PaySignUtil.MD5Encoder(src);
		// 将字符串全部转为大写
		String rel = md5.toUpperCase();
		logger.info("sign rel={}", rel);
		return rel;
	}
}
