package com.caipiao.live.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 排列组合
 * 
 * @author bjkj
 *
 */
public class PermutationsUtils {

	/**
	 * 排列组合 C mn 列如:从这个数组4个数中选择三个
	 */
	public static List<String> getCmnList(List<String> shu, int i) {
		Stack<String> stack = new Stack<String>();
		List<String> aList = new ArrayList<String>();
		f(shu, i, 0, 0, stack, aList);
		return aList;
	}

	/**
	 * 排列组合 C mn
	 * 
	 * @param shu  元素
	 * @param targ 要选多少个元素
	 * @param has  当前有多少个元素
	 * @param cur  当前选到的下标
	 */
	private static void f(List<String> shu, int targ, int has, int cur, Stack<String> stack, List<String> aList) {
		if (has == targ) {
			if (null != stack) {
				String str = stack.toString();
				aList.add(str);
			}
			return;
		}

		for (int i = cur; i < shu.size(); i++) {
			if (!stack.contains(shu.get(i))) {
				stack.add(shu.get(i));
				f(shu, targ, has + 1, i, stack, aList);
				stack.pop();
			}
		}

	}
	
	
	/**
	 * 排列组合 二同号单选
	 */
	public static List<String> getDanxuanList(List<String> l1, List<String> l2) {
		List<String> liangPai = new ArrayList<String>();
		for (String s1 : l1) {
			for (String s2 : l2) {
				//Integer he = Integer.parseInt(String.valueOf(s1))  +  Integer.parseInt(String.valueOf(s2));
				if (!s1.equals(s2)) {
					liangPai.add("["+s1+","+s2+"]");
				}
			}
		}
		return liangPai;
	}
	

	/*public static void main(String[] args) {
	
		List<String> shuList = new ArrayList<String>();
		shuList.add("1");
		shuList.add("2");
		shuList.add("3");
		shuList.add("4");
	
		// Permutations
		List<String> paiList = getCmnList(shuList, 2);
		System.out.println(paiList);
		System.out.println(paiList.size());
	
		List<String> shuList1 = new ArrayList<String>();
		shuList1.add("1");
		shuList1.add("2");
		shuList1.add("3");
		shuList1.add("4");
		shuList1.add("5");
	
		List<String> paiList1 = getCmnList(shuList1, 3);
		// String aaString = paiList1.toString();
		// System.out.println(aaString.replaceAll("\\[", "").replaceAll("\\]", ""));
		System.out.println(paiList1.toString());
		System.out.println(paiList1.size());
		
		
		
		//
		List<String> liangPai = new ArrayList<String>();
		for (String s1 : shuList) {
			for (String s2 : shuList1) {
				if (!s1.equals(s2) ) {
					liangPai.add("["+s1+","+s2+"]");
				}
			}
		}
		System.out.println(liangPai);
		System.out.println(liangPai.size());
	
	}*/

}
