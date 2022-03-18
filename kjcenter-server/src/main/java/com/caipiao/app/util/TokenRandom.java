package com.caipiao.app.util;

import java.util.Random;

/**
 * 生成Token工具类
 * 
 * @author Hans
 * @create 2019-03-27 20:07
 * 
 * */
public class TokenRandom {
	private final char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	private  void generateString(int length) {
		StringBuilder result = new StringBuilder();
		int size = chars.length;
		Random rand = new Random();
		for(int i=0,n=length;i<n;i++){
			result.append(chars[rand.nextInt(size)]);
		}
		System.out.println(result);

	}

//	public static void main(String[] args){
//		TokenRandom randomDemo = new TokenRandom();
//		for(int i = 0;i<100;i++){
//			randomDemo.generateString(16);
//		}
//
//	}
}
